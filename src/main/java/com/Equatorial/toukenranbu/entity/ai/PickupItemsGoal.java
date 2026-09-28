package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

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
        // 无主刀男不捡东西
        if (!entity.isTame()) return false;
        // 坐下时不捡东西
        if (entity.isOrderedToSit()) return false;
        // 跟随时没开捡物开关，不捡
        if (entity.isFollowing() && !entity.isPickupWhenFollowing()) return false;
        if (isInventoryFull()) return false;
        if (--cooldown > 0) return false;

        AABB box = entity.getBoundingBox().inflate(8.0);
        List<ItemEntity> items = entity.level().getEntitiesOfClass(ItemEntity.class, box,
                e -> !e.isRemoved() && isWantedItem(e.getItem(), e) && e.getAge() > 10);

        if (items.isEmpty()) return false;

        // 找最近的
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
        if (!entity.isTame()) return false;
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
        if (targetItem == null || targetItem.isRemoved()) return;

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
     * 是否是想要的物品：
     * - 队长徽章：必须是自己主人掉的才捡（NBT 里 touken_captain_owner 要和自己的 owner 相同）
     * - 其他物品：全捡
     */
    private boolean isWantedItem(ItemStack stack, ItemEntity itemEntity) {
        if (stack.isEmpty()) return false;

        if (stack.is(com.Equatorial.toukenranbu.item.ModItems.CAPTAIN_BADGE.get())) {
            UUID selfOwner = entity.getOwnerUUID();
            if (selfOwner == null) return false;  // 没主人，不捡队长徽章

            var tag = itemEntity.getPersistentData();
            if (!tag.hasUUID("touken_captain_owner")) return false;  // 没标记来源，不捡

            UUID itemOwner = tag.getUUID("touken_captain_owner");
            return selfOwner.equals(itemOwner);  // 只捡自己主人的
        }

        return true;
    }

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