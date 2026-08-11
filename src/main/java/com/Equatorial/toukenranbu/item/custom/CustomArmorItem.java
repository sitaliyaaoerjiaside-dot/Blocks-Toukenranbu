package com.Equatorial.toukenranbu.item.custom;

import com.Equatorial.toukenranbu.effect.ModEffects;
import com.Equatorial.toukenranbu.item.ModArmorMaterials;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.shaders.Effect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import oshi.annotation.concurrent.Immutable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CustomArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, List<MobEffectInstance>> MAP =
            (new ImmutableMap.Builder<ArmorMaterial,List<MobEffectInstance>>())
                    .put(ModArmorMaterials.WOOTZ_STEEL,
                            Arrays.asList(
                                    new MobEffectInstance(ModEffects.SPIRIT_REGEN.get(), 1, 1, false, false, true)
                            ))
                    .put(ModArmorMaterials.WHETSTONE,
                            Arrays.asList(
                                    new MobEffectInstance(ModEffects.SPIRIT_REGEN.get(), 1, 1, false, false, true)
                            ))
                    .build();

    public CustomArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (!pLevel.isClientSide()) {
            if (pEntity instanceof Player Player && hasFullSuitableArmor(Player)) {
                evaluatArmorEffects(Player);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    private void evaluatArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, List<MobEffectInstance>> entry : MAP.entrySet()) {
            ArmorMaterial material = entry.getKey();
            List<MobEffectInstance> effects = entry.getValue();

            if (hasCorrectMaterialArmorOn( material, player))  {
                for (MobEffectInstance effect : effects) {
                    MobEffect effect1 = effect.getEffect();
                    if (!player.hasEffect(effect1)) {
                        player.addEffect(effect);
                    }
                }
            }
        }
    }

    private boolean hasCorrectMaterialArmorOn(ArmorMaterial material, Player player) {
        for ( ItemStack stack : player.getInventory().armor) {
            if (!(stack.getItem() instanceof  ArmorItem)) {
                return false;
            }
        }

        ArmorItem helmet = (ArmorItem) player.getInventory().getArmor(3).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmor(2).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmor(1).getItem();
        ArmorItem boots = (ArmorItem) player.getInventory().getArmor(0).getItem();

        return helmet.getMaterial() == material
                && chestplate.getMaterial() == material
                && leggings.getMaterial() == material
                && boots.getMaterial() == material;
    }

    private boolean hasFullSuitableArmor(Player player) {
        ItemStack helmet = player.getInventory().getArmor(3);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack boots = player.getInventory().getArmor(0);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }
}
