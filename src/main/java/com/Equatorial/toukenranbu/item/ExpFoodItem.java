package com.Equatorial.toukenranbu.item;

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

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {

        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide && entity instanceof Player player) {
            player.giveExperiencePoints(expAmount);
        }

        return result;
    }
}