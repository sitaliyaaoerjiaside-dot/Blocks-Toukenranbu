package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.datagen.ModBlockTagsProvider;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.tag.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class MiningGoal extends Goal {
    private final ToukenDanshiEntity entity;
    private BlockPos targetOre;
    private int mineProgress;
    private int scanCooldown;
    private int stuckTicks;

    private static final int SCAN_RADIUS = 16;      // 横向扫描半径
    private static final int MINE_TICKS = 40;       // 挖一个矿 2 秒（40 tick）
    private static final int SCAN_INTERVAL = 20;    // 每 1 秒扫描一次
    private static final double REACH_DIST_SQ = 6.0; // 走到距离矿石 2.5 格以内

    public MiningGoal(ToukenDanshiEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return entity.isMining()
                && entity.toukenData.fatigue > 0
                && hasInventorySpace()
                && !findBestPickaxe().isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        scanCooldown = 0;
        mineProgress = 0;
        stuckTicks = 0;
        targetOre = null;
        findOre();
    }

    @Override
    public void stop() {
        if (targetOre != null) {
            entity.level().destroyBlockProgress(entity.getId(), targetOre, -1);
        }
        entity.setMining(false);
        targetOre = null;
        mineProgress = 0;
    }

    @Override
    public void tick() {
        if (targetOre == null) {
            if (--scanCooldown <= 0) {
                scanCooldown = SCAN_INTERVAL;
                findOre();
            }
            if (targetOre == null) {
                entity.setMining(false); // 附近没矿了，自动停止
            }
            return;
        }

        double tx = targetOre.getX() + 0.5;
        double ty = targetOre.getY();
        double tz = targetOre.getZ() + 0.5;
        double distSq = entity.distanceToSqr(tx, ty, tz);

        // 还没走到，继续导航
        if (distSq > REACH_DIST_SQ) {
            entity.getNavigation().moveTo(tx, ty, tz, 1.0);
            stuckTicks++;
            // 5 秒还没走到，放弃这个矿（可能卡住了）
            if (stuckTicks > 100) {
                entity.level().destroyBlockProgress(entity.getId(), targetOre, -1);
                targetOre = null;
                mineProgress = 0;
                stuckTicks = 0;
            }
            return;
        }

        // 走到了，面向矿石开始挖
        stuckTicks = 0;
        entity.getNavigation().stop();

        // 备用：如果当前目标挖不了（比如被其他实体抢先了），立刻重找
        if (!isOre(entity.level().getBlockState(targetOre))
                || !canMine(entity.level().getBlockState(targetOre), findBestPickaxe())) {
            targetOre = null;
            mineProgress = 0;
            return;
        }
        entity.getLookControl().setLookAt(tx, targetOre.getY() + 0.5, tz);

        mineProgress++;

        // 显示破坏裂纹（客户端能看到）
        if (mineProgress % 4 == 0) {
            int stage = mineProgress * 10 / MINE_TICKS;
            entity.level().destroyBlockProgress(entity.getId(), targetOre, stage);
        }

        // 挖完了
        if (mineProgress >= MINE_TICKS) {
            breakAndCollect(targetOre);
            entity.toukenData.fatigue = Math.max(0, entity.toukenData.fatigue - 2); // 挖一个矿掉2疲劳
            entity.syncExtraData();// 同步到客户端，UI才能刷新
            scanCooldown = 0; // 挖完立刻找下一个矿，不等 1 秒冷却
            entity.level().destroyBlockProgress(entity.getId(), targetOre, -1);
            targetOre = null;
            mineProgress = 0;
        }
    }

    private void findOre() {
        BlockPos center = entity.blockPosition();
        ItemStack pick = findBestPickaxe();
        BlockPos closest = null;
        double bestDist = Double.MAX_VALUE;
        int foundCount = 0;
        int canMineCount = 0;

        // Y 范围从 -3~3 扩大到 -8~3，能挖脚下更深的矿
        for (int x = -SCAN_RADIUS; x <= SCAN_RADIUS; x++) {
            for (int y = -8; y <= 3; y++) {
                for (int z = -SCAN_RADIUS; z <= SCAN_RADIUS; z++) {
                    BlockPos p = center.offset(x, y, z);
                    BlockState state = entity.level().getBlockState(p);
                    if (isOre(state)) {
                        foundCount++;
                        if (canMine(state, pick)) {
                            canMineCount++;
                            double d = center.distSqr(p);
                            if (d < bestDist) {
                                bestDist = d;
                                closest = p;
                            }
                        }
                    }
                }
            }
        }

        // 排查日志：进游戏看控制台，确认后删掉这行
        System.out.println("[findOre] center=" + center + " found=" + foundCount
                + " canMine=" + canMineCount + " pick=" + pick
                + " closest=" + closest);

        targetOre = closest;
    }

    private boolean isOre(BlockState state) {
        return state.is(BlockTags.COAL_ORES)
                || state.is(BlockTags.IRON_ORES)
                || state.is(BlockTags.COPPER_ORES)
                || state.is(BlockTags.GOLD_ORES)
                || state.is(BlockTags.REDSTONE_ORES)
                || state.is(BlockTags.LAPIS_ORES)
                || state.is(BlockTags.DIAMOND_ORES)
                || state.is(BlockTags.EMERALD_ORES)
                || state.is(ModBlockTags.ORE_TAGS);
    }

    // ========== 镐子等级判断 ==========
    private boolean canMine(BlockState state, ItemStack pickaxe) {
        if (pickaxe.isEmpty() || !(pickaxe.getItem() instanceof PickaxeItem pick)) {
            return false;
        }
        int level = pick.getTier().getLevel();
        if (state.is(BlockTags.NEEDS_DIAMOND_TOOL)) return level >= 3;
        if (state.is(BlockTags.NEEDS_IRON_TOOL))     return level >= 2;
        if (state.is(BlockTags.NEEDS_STONE_TOOL))    return level >= 1;
        return true;
    }

    private ItemStack findBestPickaxe() {
        ItemStack best = ItemStack.EMPTY;
        int bestLevel = -1;
        for (int i = 0; i < entity.getInventoryHandler().getSlots(); i++) {
            ItemStack stack = entity.getInventoryHandler().getStackInSlot(i);
            if (stack.getItem() instanceof PickaxeItem pick) {
                int level = pick.getTier().getLevel();
                if (level > bestLevel) {
                    bestLevel = level;
                    best = stack;
                }
            }
        }
        return best;
    }

    // ========== 破坏 + 吸掉落物 ==========
    private void breakAndCollect(BlockPos pos) {
        Level level = entity.level();
        level.destroyBlock(pos, true, entity);
        damagePickaxe();

        // 把周围 2 格内掉落物吸进 25 格背包
        List<ItemEntity> drops = level.getEntitiesOfClass(ItemEntity.class,
                new AABB(pos).inflate(2.0));

        for (ItemEntity drop : drops) {
            ItemStack stack = drop.getItem();
            for (int i = 0; i < entity.getInventoryHandler().getSlots(); i++) {
                stack = entity.getInventoryHandler().insertItem(i, stack, false);
                if (stack.isEmpty()) break;
            }
            if (stack.isEmpty()) {
                drop.discard();
            } else {
                drop.setItem(stack);
            }
        }
    }

    private void damagePickaxe() {
        for (int i = 0; i < entity.getInventoryHandler().getSlots(); i++) {
            ItemStack stack = entity.getInventoryHandler().getStackInSlot(i);
            if (stack.getItem() instanceof PickaxeItem) {
                // stack 是 ItemStackHandler 内部引用，hurtAndBreak 直接修改槽位
                stack.hurtAndBreak(1, entity, e -> {});
                // 如果耐久耗尽，stack 自动变成 EMPTY，不需要 setStackInSlot
                return;
            }
        }
    }

    private boolean hasInventorySpace() {
        for (int i = 0; i < entity.getInventoryHandler().getSlots(); i++) {
            if (entity.getInventoryHandler().getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
