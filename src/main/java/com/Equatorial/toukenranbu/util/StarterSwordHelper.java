package com.Equatorial.toukenranbu.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class StarterSwordHelper {
    public static final String STARTER_TAG = "StarterPack";

    public static boolean isStarterSword(ItemStack stack) {
        return !stack.isEmpty() && stack.hasTag() && stack.getTag().getBoolean(STARTER_TAG);
    }

    public static void markAsStarter(ItemStack stack) {
        stack.getOrCreateTag().putBoolean(STARTER_TAG, true);
    }

    /** 清掉背包里其他带标记的刀（跳过当前手持，因为手上这把由 shrink(1) 正常消耗） */
    public static void clearOtherStarterSwords(Player player, InteractionHand currentHand) {
        int skipSlot = currentHand == InteractionHand.MAIN_HAND
                ? player.getInventory().selected
                : 40;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (i == skipSlot) continue;
            ItemStack stack = player.getInventory().getItem(i);
            if (isStarterSword(stack)) {
                player.getInventory().setItem(i, ItemStack.EMPTY);
            }
        }
    }
}