package com.Equatorial.toukenranbu.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WhiteMikazukiMunechikaItem extends Item {

    private static final ResourceKey<DamageType> TRUE_DAMAGE = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "true_damage")
    );

    public WhiteMikazukiMunechikaItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // 1. 构造 DamageSource —— 沿用 YariEntity 的三参数写法
        Registry<DamageType> registry = attacker.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE);
        ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE,
                ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "white_mikazuki"));
        DamageSource source = new DamageSource(registry.getHolderOrThrow(key), attacker, attacker);

        // 2. 造成 2147483646 点真实伤害
        target.hurt(source, Integer.MAX_VALUE - 1);

        // 3. 消耗耐久到 0 → 物品立即销毁
        stack.hurtAndBreak(stack.getMaxDamage(), attacker, (entity) -> {});

        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.toukenranbu_mod.white_mikazuki.line1")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.toukenranbu_mod.white_mikazuki.line2")
                .withStyle(ChatFormatting.DARK_RED));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;   // 禁止附魔台
    }

    @Override
    public int getEnchantmentValue() {
        return 0;       // 附魔能力为 0
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;   // 禁止用附魔书在铁砧上加附魔
    }
}