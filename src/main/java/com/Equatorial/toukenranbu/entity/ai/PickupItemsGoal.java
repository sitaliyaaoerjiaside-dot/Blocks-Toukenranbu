package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.EnumSet;
import java.util.List;

/**
 * 刀剑男士拾取掉落物AI
 * 只要不是坐下状态，且背包未满，就会主动拾取周围8格内的所有掉落物。
 */
public class PickupItemsGoal extends Goal {
    private final ToukenDanshiEntity entity;
    private ItemEntity targetItem;
    private int cooldown;

    public PickupItemsGoal(ToukenDanshiEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // 坐下时不捡东西；背包满了也不捡
        if (entity.isOrderedToSit()) return false;
        if (entity.isFollowing() && !entity.isPickupWhenFollowing()) return false;
        if (isInventoryFull()) return false;
        if (--cooldown > 0) return false;

        AABB box = entity.getBoundingBox().inflate(8.0);
        List<ItemEntity> items = entity.level().getEntitiesOfClass(ItemEntity.class, box,
                e -> !e.isRemoved() && isWantedItem(e.getItem()) && e.getAge() > 10);

        if (items.isEmpty()) return false;

        // 找最近的掉落物
        targetItem = items.get(0);
        double bestDist = entity.distanceToSqr(targetItem);
        for (int i = 1; i < items.size(); i++) {
            double dist = entity.distanceToSqr(items.get(i));
            if (dist < bestDist) {
                bestDist = dist;
                targetItem = items.get(i);
            }
        }

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return targetItem != null
                && !targetItem.isRemoved()
                && !entity.isOrderedToSit()
                && !isInventoryFull();
    }

    @Override
    public void stop() {
        targetItem = null;
        cooldown = 10;
    }

    @Override
    public void tick() {
        if (targetItem == null || targetItem.isRemoved()) {
            return;
        }

        double dist = entity.distanceToSqr(targetItem);
        if (dist > 1.5D) {
            entity.getNavigation().moveTo(targetItem, 1.2D);
            return;
        }

        entity.getNavigation().stop();

        ItemStack stack = targetItem.getItem();
        ItemStack remainder = ItemHandlerHelper.insertItem(entity.getInventoryHandler(), stack, false);
        if (remainder.isEmpty()) {
            targetItem.discard();
        } else {
            targetItem.setItem(remainder);
        }

        targetItem = null;
    }

    /**
     * 判断是否是想要的物品
     * 现在改为：只要不是空物品，全都要。
     * 如果你以后想过滤垃圾（如泥土、圆石、木棍），把下面的 return 改成过滤逻辑即可。
     */
    private boolean isWantedItem(ItemStack stack) {
        return !stack.isEmpty();

        /* === 如果以后背包被垃圾塞满，换成下面这段过滤版 ===
        if (stack.isEmpty()) return false;

        // 始终保留：种地相关
        if (ToukenFarmingGoal.isSeed(stack)) return true;
        if (ToukenDanshiEntity.isCropProduce(stack)) return true;

        // 始终保留：回血相关
        if (entity.evaluateHealItem(stack).heal > 0) return true;

        // 过滤常见垃圾
        if (stack.is(Items.DIRT)) return false;
        if (stack.is(Items.COBBLESTONE)) return false;
        if (stack.is(Items.GRANITE)) return false;
        if (stack.is(Items.DIORITE)) return false;
        if (stack.is(Items.ANDESITE)) return false;
        if (stack.is(Items.COBBLED_DEEPSLATE)) return false;
        if (stack.is(Items.STICK)) return false;
        if (stack.is(Items.SAND)) return false;
        if (stack.is(Items.GRAVEL)) return false;
        if (stack.is(Items.NETHERRACK)) return false;
        if (stack.is(Items.TORCH)) return false;  // 火把通常很多

        return true;
        */
    }

    /**
     * 检查25格通用背包是否已满（没有空格子且现有堆叠都到上限）
     */
    private boolean isInventoryFull() {
        var handler = entity.getInventoryHandler();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.isEmpty()) return false;
            if (stack.getCount() < stack.getMaxStackSize()) return false;
        }
        return true;
    }
}
