package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class ToukenFarmingGoal extends Goal {
    private final ToukenDanshiEntity entity;
    private BlockPos targetPos;
    private int cooldown;
    private int stuckTick;
    private int noSeedCooldown;

    private int farmlandCheckCooldown = 0;
    private boolean cachedAllPlanted = false;

    private final Map<BlockPos, Integer> blockedTargets = new HashMap<>();
    /** 每个目标的连续寻路失败次数，达到3次才加入 blockedTargets */
    private final Map<BlockPos, Integer> targetFailCounts = new HashMap<>();

    // ===== 逃番机制：收够且种够才逃番，否则永久摸鱼 =====
    // 【改这里】收够多少个作物才开始逃番
    private int harvestCounter = 0;
    private static final int HARVEST_THRESHOLD = 128;

    // 【改这里】种够多少个种子才开始逃番
    private int plantCounter = 0;
    private static final int PLANT_THRESHOLD = 128;

    public ToukenFarmingGoal(ToukenDanshiEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.cooldown = 0;
        this.noSeedCooldown = 0;
    }

    // ===== 外部调用：玩家点击种地按钮时重置逃番，立刻复工 =====
    public void resetEscape() {
        // 【改这里】如果以后想改成"合计次数"，把下面两行改成同一个计数器即可
        this.harvestCounter = 0;
        this.plantCounter = 0;
        this.cooldown = 0;
        this.targetPos = null;
        this.stuckTick = 0;
        this.targetFailCounts.clear();
    }

    /**
     * 判断是否处于逃番状态（收够且种够阈值）
     */
    public boolean isEscaping() {
        return harvestCounter >= HARVEST_THRESHOLD && plantCounter >= PLANT_THRESHOLD;
    }

    @Override
    public boolean canUse() {
        if (!entity.isFarming() || entity.isOrderedToSit()) return false;
        // 修正 cooldown：先判断再减，避免负数混乱
        if (cooldown > 0) {
            cooldown--;
            return false;
        }
        // 逃番中（收够且种够），不启动，等玩家点按 resetEscape 唤醒
        if (harvestCounter >= HARVEST_THRESHOLD && plantCounter >= PLANT_THRESHOLD) {
            return false;
        }
        entity.recalcFarmlandTerritory();
        // 领地为空就不启动
        return !entity.getMyFarmlandTerritory().isEmpty();
    }

    @Override
    public boolean canContinueToUse() {
        if (!entity.isFarming() || entity.isOrderedToSit()) return false;
        // 卡死超过120tick自动退出种地模式，防止反复启停循环
        if (stuckTick > 120) {
            entity.setFarming(false);
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        this.stuckTick = 0;
        this.targetPos = null;
    }

    @Override
    public void stop() {
        entity.releaseFarmTarget();
        this.targetPos = null;
        this.stuckTick = 0;
        this.cooldown = 5;
        this.targetFailCounts.clear();
        if (!entity.level().isClientSide) {
            entity.getNavigation().stop();
        }
    }

    @Override
    public void tick() {
        ServerLevel level = (ServerLevel) entity.level();
        decrementBlockedCooldowns();

        // === 逃番中：收够128且种够128 → 永久摸鱼游荡，直到玩家点按种地按钮 ===
        if (harvestCounter >= HARVEST_THRESHOLD && plantCounter >= PLANT_THRESHOLD) {
            if (entity.getNavigation().isDone()) {
                wanderInTerritory(level);
            }
            return;
        }

        // 目标无效就重新找
        if (targetPos == null || !isValidTarget(level, targetPos) || isBlocked(targetPos)) {
            entity.releaseFarmTarget();
            targetPos = findNextTarget(level);
            if (targetPos != null) {
                entity.claimFarmTarget(targetPos);
            }
        }

        // 找不到目标 → 在领地内轻微游荡
        if (targetPos == null) {
            if (entity.getNavigation().isDone()) {
                wanderInTerritory(level);
            }
            return;
        }

        BlockState aboveState = level.getBlockState(targetPos.above());

        entity.getLookControl().setLookAt(
                targetPos.getX() + 0.5,
                targetPos.getY() + 1.0,
                targetPos.getZ() + 0.5
        );

        double dist = entity.distanceToSqr(
                targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5
        );

        if (dist > 2.5D) {
            this.stuckTick++;
            if (this.stuckTick > 80) {
                // 记录该目标连续失败次数，达到3次才短暂拉黑
                BlockPos immutablePos = targetPos.immutable();
                int fails = targetFailCounts.getOrDefault(immutablePos, 0) + 1;
                targetFailCounts.put(immutablePos, fails);
                if (fails >= 3) {
                    blockedTargets.put(immutablePos, 60); // 60 tick 短暂冷却
                }
                entity.releaseFarmTarget();
                targetPos = null;
                entity.getNavigation().stop();
                this.stuckTick = 0;
                return;
            }
            entity.getNavigation().moveTo(
                    targetPos.getX() + 0.5,
                    targetPos.getY(),
                    targetPos.getZ() + 0.5,
                    0.8D
            );
            return;
        }

        entity.getNavigation().stop();
        this.stuckTick = 0;

        Block aboveBlock = aboveState.getBlock();
        if (aboveBlock instanceof CropBlock crop && crop.isMaxAge(aboveState)) {
            harvestCrop(level, targetPos.above(), aboveState);
            resetFarmlandCache();
            targetFailCounts.remove(targetPos.immutable());
            entity.releaseFarmTarget();
            targetPos = null;
            cooldown = 5;
            harvestCounter++; // 【改这里】收割计数+1
            return;
        }

        if (aboveBlock instanceof SugarCaneBlock) {
            BlockPos topPos = findSugarCaneTop(level, targetPos.above());
            if (topPos.getY() > targetPos.above().getY()) {
                harvestSugarCane(level, targetPos.above(), topPos);
                resetFarmlandCache();
                targetFailCounts.remove(targetPos.immutable());
                entity.releaseFarmTarget();
                targetPos = null;
                cooldown = 0;
                harvestCounter++; // 【改这里】收割计数+1
                return;
            }
            // 甘蔗不够高，不收，不加计数
            resetFarmlandCache();
            targetFailCounts.remove(targetPos.immutable());
            entity.releaseFarmTarget();
            targetPos = null;
            cooldown = 0;
            return;
        }

        if (aboveState.isAir()) {
            if (tryPlant(level, targetPos.above())) {
                resetFarmlandCache();
                targetFailCounts.remove(targetPos.immutable());
                entity.releaseFarmTarget();
                targetPos = null;
                cooldown = 0;
                plantCounter++; // 【改这里】种植计数+1
                return;
            } else {
                entity.setNeedsSeedRefill(true);
                entity.releaseFarmTarget();
                targetPos = null;
                cooldown = 20;
                return;
            }
        }

        entity.releaseFarmTarget();
        targetPos = null;
    }

    // ===== 领地游荡 =====
    private void wanderInTerritory(ServerLevel level) {
        Set<BlockPos> territory = entity.getMyFarmlandTerritory();
        double tx, ty, tz;

        if (territory.isEmpty()) {
            double angle = level.getRandom().nextDouble() * Math.PI * 2;
            double dist = 3.0D + level.getRandom().nextDouble() * 6.0D;
            tx = entity.getX() + Math.cos(angle) * dist;
            ty = entity.getY();
            tz = entity.getZ() + Math.sin(angle) * dist;
        } else {
            List<BlockPos> list = new ArrayList<>(territory);
            BlockPos target = list.get(level.getRandom().nextInt(list.size()));
            tx = target.getX() + 0.5;
            ty = target.getY();
            tz = target.getZ() + 0.5;
        }

        entity.getNavigation().moveTo(tx, ty, tz, 0.5D);
    }

    private void resetFarmlandCache() {
        this.farmlandCheckCooldown = 0;
        this.cachedAllPlanted = false;
    }

    private boolean isBlocked(BlockPos pos) {
        Integer cd = blockedTargets.get(pos);
        return cd != null && cd > 0;
    }

    private void decrementBlockedCooldowns() {
        blockedTargets.entrySet().removeIf(e -> e.setValue(e.getValue() - 1) <= 0);
    }

    private BlockPos findSugarCaneTop(ServerLevel level, BlockPos basePos) {
        BlockPos pos = basePos;
        while (level.getBlockState(pos.above()).getBlock() instanceof SugarCaneBlock) {
            pos = pos.above();
        }
        return pos;
    }

    private void harvestSugarCane(ServerLevel level, BlockPos basePos, BlockPos topPos) {
        for (BlockPos pos = topPos; pos.getY() > basePos.getY(); pos = pos.below()) {
            BlockState state = level.getBlockState(pos);
            var drops = Block.getDrops(state, level, pos, null, entity, ItemStack.EMPTY);

            for (ItemStack drop : drops) {
                ItemStack remainder = ItemHandlerHelper.insertItem(entity.getInventoryHandler(), drop, false);
                if (!remainder.isEmpty()) {
                    level.addFreshEntity(new ItemEntity(level,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            remainder));
                }
            }

            level.removeBlock(pos, false);
            level.levelEvent(2001, pos, Block.getId(state));
        }
    }

    private boolean isValidTarget(ServerLevel level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof FarmBlock || isSugarCaneBase(level, pos);
    }

    private boolean isSugarCaneBase(ServerLevel level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return above.getBlock() instanceof SugarCaneBlock;
    }

    private void harvestCrop(ServerLevel level, BlockPos cropPos, BlockState cropState) {
        var drops = Block.getDrops(cropState, level, cropPos, null, entity, ItemStack.EMPTY);

        for (ItemStack drop : drops) {
            ItemStack remainder = ItemHandlerHelper.insertItem(entity.getInventoryHandler(), drop, false);
            if (!remainder.isEmpty()) {
                level.addFreshEntity(new ItemEntity(level,
                        cropPos.getX() + 0.5, cropPos.getY() + 0.5, cropPos.getZ() + 0.5,
                        remainder));
            }
        }

        level.removeBlock(cropPos, false);
        level.levelEvent(2001, cropPos, Block.getId(cropState));
    }

    private boolean tryPlant(ServerLevel level, BlockPos plantPos) {
        BlockState below = level.getBlockState(plantPos.below());

        if (below.getBlock() == Blocks.SAND || below.getBlock() == Blocks.DIRT || below.getBlock() == Blocks.GRASS_BLOCK) {
            if (hasAdjacentWater(level, plantPos.below())) {
                if (level.isEmptyBlock(plantPos)) {
                    level.setBlock(plantPos, Blocks.SUGAR_CANE.defaultBlockState(), 3);
                    return true;
                }
            }
        }

        ItemStack seedStack = findFirstSeed();
        if (seedStack.isEmpty()) return false;

        if (seedStack.getItem() instanceof BlockItem blockItem) {
            if (!level.isEmptyBlock(plantPos)) return false;
            if (!(level.getBlockState(plantPos.below()).getBlock() instanceof FarmBlock)) return false;
            level.setBlock(plantPos, blockItem.getBlock().defaultBlockState(), 3);
            level.levelEvent(1505, plantPos, 0);
            seedStack.shrink(1);
            return true;
        }

        if (seedStack.is(net.minecraft.world.item.Items.CARROT)) {
            return plantCrop(level, plantPos, Blocks.CARROTS.defaultBlockState());
        }
        if (seedStack.is(net.minecraft.world.item.Items.POTATO)) {
            return plantCrop(level, plantPos, Blocks.POTATOES.defaultBlockState());
        }
        if (seedStack.is(net.minecraft.world.item.Items.BEETROOT)) {
            return plantCrop(level, plantPos, Blocks.BEETROOTS.defaultBlockState());
        }
        if (seedStack.is(net.minecraft.world.item.Items.SWEET_BERRIES)) {
            return plantCrop(level, plantPos, Blocks.SWEET_BERRY_BUSH.defaultBlockState());
        }

        return false;
    }

    private boolean plantCrop(ServerLevel level, BlockPos plantPos, BlockState cropState) {
        if (!level.isEmptyBlock(plantPos)) return false;
        if (!(level.getBlockState(plantPos.below()).getBlock() instanceof FarmBlock)) return false;
        level.setBlock(plantPos, cropState, 3);
        level.levelEvent(1505, plantPos, 0);
        ItemStack seedStack = findFirstSeed();
        if (!seedStack.isEmpty()) {
            seedStack.shrink(1);
        }
        return true;
    }

    private boolean hasAdjacentWater(ServerLevel level, BlockPos pos) {
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            if (level.getFluidState(pos.relative(dir)).is(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }

    private ItemStack findFirstSeed() {
        var handler = entity.getInventoryHandler();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (isSeed(stack)) return stack;
        }
        return ItemStack.EMPTY;
    }

    public boolean hasRipeCrops() {
        var handler = entity.getInventoryHandler();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (ToukenDanshiEntity.isCropProduce(stack)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllFarmlandPlanted(ServerLevel level) {
        if (farmlandCheckCooldown-- > 0) return cachedAllPlanted;

        farmlandCheckCooldown = 20;
        boolean result = true;

        BlockPos center = entity.blockPosition();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        outer:
        for (int dx = -8; dx <= 8; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -8; dz <= 8; dz++) {
                    mutable.set(center.getX() + dx, center.getY() + dy, center.getZ() + dz);
                    BlockState state = level.getBlockState(mutable);
                    if (state.getBlock() instanceof FarmBlock) {
                        BlockState above = level.getBlockState(mutable.above());
                        if (above.isAir()) {
                            result = false;
                            break outer;
                        }
                    }
                }
            }
        }

        cachedAllPlanted = result;
        return result;
    }

    private BlockPos findNextTarget(ServerLevel level) {
        Set<BlockPos> territory = entity.getMyFarmlandTerritory();
        if (territory.isEmpty()) return null;

        BlockPos bestHarvest = null;
        BlockPos bestEmpty = null;
        double bestHarvestDist = Double.MAX_VALUE;
        double bestEmptyDist = Double.MAX_VALUE;

        for (BlockPos groundPos : territory) {
            BlockState groundState = level.getBlockState(groundPos);
            BlockState above = level.getBlockState(groundPos.above());
            Block aboveBlock = above.getBlock();
            double dist = entity.distanceToSqr(
                    groundPos.getX() + 0.5, groundPos.getY(), groundPos.getZ() + 0.5
            );

            // 预检路径可达性，避免选到对岸/栅栏外等过不去的地
            var path = entity.getNavigation().createPath(groundPos, 0);
            if (path == null) continue;

            if (groundState.getBlock() instanceof FarmBlock) {
                if (aboveBlock instanceof CropBlock crop && crop.isMaxAge(above)) {
                    if (dist < bestHarvestDist && !isBlocked(groundPos)
                            && !ToukenDanshiEntity.isFarmTargetLockedByOther(level, groundPos, entity.getUUID())
                        /* && !ToukenDanshiEntity.isPosInOtherTerritory(level, groundPos, entity.getUUID()) */) {
                        bestHarvestDist = dist;
                        bestHarvest = groundPos;
                    }
                } else if (above.isAir()) {
                    if (dist < bestEmptyDist && !isBlocked(groundPos)
                            && !ToukenDanshiEntity.isFarmTargetLockedByOther(level, groundPos, entity.getUUID())
                        /* && !ToukenDanshiEntity.isPosInOtherTerritory(level, groundPos, entity.getUUID()) */) {
                        bestEmptyDist = dist;
                        bestEmpty = groundPos;
                    }
                }
            }

            if (aboveBlock instanceof SugarCaneBlock) {
                BlockPos top = findSugarCaneTop(level, groundPos.above());
                if (top.getY() > groundPos.above().getY() && dist < bestHarvestDist && !isBlocked(groundPos)
                        && !ToukenDanshiEntity.isFarmTargetLockedByOther(level, groundPos, entity.getUUID())
                    /* && !ToukenDanshiEntity.isPosInOtherTerritory(level, groundPos, entity.getUUID()) */) {
                    bestHarvestDist = dist;
                    bestHarvest = groundPos;
                }
            }
        }

        return bestHarvest != null ? bestHarvest : bestEmpty;
    }

    public static boolean isSeed(ItemStack stack) {
        if (stack.isEmpty()) return false;
        if (stack.is(net.minecraft.world.item.Items.WHEAT_SEEDS) ||
                stack.is(net.minecraft.world.item.Items.BEETROOT_SEEDS) ||
                stack.is(net.minecraft.world.item.Items.PUMPKIN_SEEDS) ||
                stack.is(net.minecraft.world.item.Items.MELON_SEEDS)) {
            return true;
        }
        if (stack.is(net.minecraft.world.item.Items.CARROT) ||
                stack.is(net.minecraft.world.item.Items.POTATO) ||
                stack.is(net.minecraft.world.item.Items.BEETROOT) ||
                stack.is(net.minecraft.world.item.Items.SWEET_BERRIES) ||
                stack.is(net.minecraft.world.item.Items.CACTUS) ||
                stack.is(net.minecraft.world.item.Items.SUGAR_CANE)) {
            return true;
        }
        if (stack.getItem() instanceof BlockItem bi && bi.getBlock() instanceof CropBlock) {
            return true;
        }
        return false;
    }
}
