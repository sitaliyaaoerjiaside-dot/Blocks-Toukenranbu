package com.Equatorial.toukenranbu.item;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ExpFoodItem extends Item {
    private final int expAmount;

    public ExpFoodItem(int expAmount, Properties properties) {
        super(properties);
        this.expAmount = expAmount;
    }

    // 暴露 get 方法，方便实体类里调用
    public int getExpAmount() {
        return expAmount;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide) {
            if (entity instanceof Player player) {
                player.giveExperiencePoints(expAmount);
            }
            // 如果刀男使用（虽然你的实体里是直接吃，但以防万一）
            else if (entity instanceof ToukenDanshiEntity danshi) {
                if (danshi.isTame()) { // 只有有主的才能吃经验
                    danshi.addExperience(expAmount);
                }
            }
        }

        return result;
    }
}