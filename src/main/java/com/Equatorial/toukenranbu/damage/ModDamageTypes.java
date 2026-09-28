package com.Equatorial.toukenranbu.damage;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> BOSS_INSTANT_DEATH =
            ResourceKey.create(Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "boss_instant_death"));
}
