package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import java.util.EnumSet;

public class ToukenDepositGoal extends Goal {
    private final ToukenDanshiEntity entity;
    private BlockPos targetChest;
    private int cooldown;
    private int giveUpTick;
    private Mode mode = Mode.DEPOSIT;

    private int farmlandCheckCooldown = 0;
    private boolean cachedAllPlanted = false;

    private static final int MAX_WITHDRAW_SLOTS = 3;
    private static final int MAX_PER_SLOT = 16;

    private BlockPos cachedChestPos = null;
    private int chestCacheCooldown = 0;

    private enum Mode {
        DEPOSIT,
        WITHDRAW
    }

    public ToukenDepositGoal(ToukenDanshiEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.cooldown = 0;
    }

    @Override
    public boolean canUse() {
        if (--cooldown > 0) return false;
        if (entity.isOrderedToSit()) return false;
        if (!entity.isFarming()) return false;  // ← 只有种地模式触发

        boolean full = isInventoryFull();
        boolean allPlanted = isAllFarmlandPlanted();

        // 背包满了 → 放箱子（优先作物，然后其他）
        if (full) {
            mode = Mode.DEPOSIT;
            targetChest = findNearbyChest();
            return targetChest != null;
        }

        // 需要种子 → 取种子
        if (entity.needsSeedRefill() && !allPlanted) {
            mode = Mode.WITHDRAW;
            targetChest = findNearbyChest();
            return targetChest != null;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return targetChest != null && !entity.isOrderedToSit() && entity.isFarming();
    }

    @Override
    public void start() {
        this.giveUpTick = 0;
    }

    @Override
    public void stop() {
        this.targetChest = null;
        this.cooldown = 60;
        entity.setNeedsSeedRefill(false);
    }

    @Override
    public void tick() {
        if (targetChest == null) return;

        double dist = entity.distanceToSqr(
                targetChest.getX() + 0.5,
                targetChest.getY(),
                targetChest.getZ() + 0.5
        );

        if (dist > 2.5D) {
            this.giveUpTick++;
            if (this.giveUpTick > 100) {
                targetChest = null;
                cachedChestPos = null;
                return;
            }
            entity.getNavigation().moveTo(
                    targetChest.getX() + 0.5,
                    targetChest.getY(),
                    targetChest.getZ() + 0.5,
                    1.0D
            );
            return;
        }

        this.giveUpTick = 0;
        ServerLevel level = (ServerLevel) entity.level();
        BlockEntity be = level.getBlockEntity(targetChest);
        if (be == null) {
            targetChest = null;
            return;
        }

        var cap = be.getCapability(ForgeCapabilities.ITEM_HANDLER);
        if (!cap.isPresent()) {
            targetChest = null;
            return;
        }

        IItemHandler chestHandler = cap.orElse(null);
        ItemStackHandler entityHandler = (ItemStackHandler) entity.getInventoryHandler();

        if (mode == Mode.DEPOSIT) {
            doDeposit(chestHandler, entityHandler);
        } else {
            doWithdraw(chestHandler, entityHandler);
        }
    }

    // ===== 放东西：优先作物，然后其他非种子物品 =====

    private void doDeposit(IItemHandler chestHandler, ItemStackHandler entityHandler) {
        boolean depositedSomething = false;

        // 第一轮：优先放熟成作物
        for (int i = 0; i < entityHandler.getSlots(); i++) {
            ItemStack stack = entityHandler.getStackInSlot(i);
            if (ToukenDanshiEntity.isCropProduce(stack)) {
                ItemStack remainder = ItemHandlerHelper.insertItem(chestHandler, stack.copy(), false);
                if (remainder.getCount() != stack.getCount()) {
                    entityHandler.setStackInSlot(i, remainder);
                    depositedSomething = true;
                }
            }
        }

        // 第二轮：作物放完后，放其他非种子物品（保留种子继续种地）
        for (int i = 0; i < entityHandler.getSlots(); i++) {
            ItemStack stack = entityHandler.getStackInSlot(i);
            if (!stack.isEmpty() && !ToukenFarmingGoal.isSeed(stack)) {
                ItemStack remainder = ItemHandlerHelper.insertItem(chestHandler, stack.copy(), false);
                if (remainder.getCount() != stack.getCount()) {
                    entityHandler.setStackInSlot(i, remainder);
                    depositedSomething = true;
                }
            }
        }

        // 箱子塞满了放不进去，或者背包腾出空间了，结束
        if (!depositedSomething || !isInventoryFull()) {
            targetChest = null;
        }
    }

    // ===== 取种子 =====

    private void doWithdraw(IItemHandler chestHandler, ItemStackHandler entityHandler) {
        boolean withdrewSomething = false;
        int slotsTaken = 0;

        for (int i = 0; i < chestHandler.getSlots() && slotsTaken < MAX_WITHDRAW_SLOTS; i++) {
            ItemStack chestStack = chestHandler.getStackInSlot(i);
            if (chestStack.isEmpty()) continue;

            if (ToukenFarmingGoal.isSeed(chestStack) || isPlantableCrop(chestStack)) {
                int taken = tryWithdrawLimited(chestHandler, entityHandler, i, chestStack);
                if (taken > 0) {
                    withdrewSomething = true;
                    slotsTaken++;
                }
            }
        }

        if (withdrewSomething || !hasPlantableInChest(chestHandler)) {
            targetChest = null;
            entity.setNeedsSeedRefill(false);
        }
    }

    private int tryWithdrawLimited(IItemHandler chestHandler, ItemStackHandler entityHandler, int slot, ItemStack chestStack) {
        ItemStack toTake = chestStack.copy();
        toTake.setCount(Math.min(chestStack.getCount(), MAX_PER_SLOT));

        ItemStack remainder = ItemHandlerHelper.insertItem(entityHandler, toTake, false);
        int taken = toTake.getCount() - remainder.getCount();

        if (taken > 0) {
            chestHandler.extractItem(slot, taken, false);
        }
        return taken;
    }

    // ===== 辅助方法 =====

    private boolean isInventoryFull() {
        var handler = entity.getInventoryHandler();
        for (int i = 0; i < handler.getSlots(); i++) {
            if (handler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isPlantableCrop(ItemStack stack) {
        return stack.is(net.minecraft.world.item.Items.CARROT) ||
                stack.is(net.minecraft.world.item.Items.POTATO) ||
                stack.is(net.minecraft.world.item.Items.BEETROOT) ||
                stack.is(net.minecraft.world.item.Items.SWEET_BERRIES) ||
                stack.is(net.minecraft.world.item.Items.CACTUS) ||
                stack.is(net.minecraft.world.item.Items.SUGAR_CANE);
    }

    private boolean hasRipeCrops() {
        var handler = entity.getInventoryHandler();
        for (int i = 0; i < handler.getSlots(); i++) {
            if (ToukenDanshiEntity.isCropProduce(handler.getStackInSlot(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isAllFarmlandPlanted() {
        if (farmlandCheckCooldown-- > 0) return cachedAllPlanted;

        farmlandCheckCooldown = 20;
        boolean result = true;

        ServerLevel level = (ServerLevel) entity.level();
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

    private boolean hasPlantableInChest(IItemHandler chestHandler) {
        for (int i = 0; i < chestHandler.getSlots(); i++) {
            ItemStack stack = chestHandler.getStackInSlot(i);
            if (ToukenFarmingGoal.isSeed(stack) || isPlantableCrop(stack)) {
                return true;
            }
        }
        return false;
    }

    private BlockPos findNearbyChest() {
        if (--chestCacheCooldown > 0 && cachedChestPos != null) {
            ServerLevel level = (ServerLevel) entity.level();
            BlockEntity be = level.getBlockEntity(cachedChestPos);
            if (be != null && be.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent()) {
                return cachedChestPos;
            }
        }

        ServerLevel level = (ServerLevel) entity.level();
        BlockPos center = entity.blockPosition();

        for (int dx = -6; dx <= 6; dx++) {
            for (int dy = -5; dy <= 5; dy++) {
                for (int dz = -6; dz <= 6; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockEntity be = level.getBlockEntity(pos);
                    if (be != null) {
                        var cap = be.getCapability(ForgeCapabilities.ITEM_HANDLER);
                        if (cap.isPresent()) {
                            cachedChestPos = pos;
                            chestCacheCooldown = 100;
                            return pos;
                        }
                    }
                }
            }
        }

        cachedChestPos = null;
        return null;
    }
}