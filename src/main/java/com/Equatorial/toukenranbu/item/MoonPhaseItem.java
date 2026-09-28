package com.Equatorial.toukenranbu.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoonPhaseItem extends Item {

    public MoonPhaseItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.toukenranbu_mod.moon_phase.line1")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.toukenranbu_mod.moon_phase.line2")
                .withStyle(ChatFormatting.DARK_PURPLE));
    }
}