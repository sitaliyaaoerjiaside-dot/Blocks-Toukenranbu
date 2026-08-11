package com.Equatorial.toukenranbu.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties A_BITE_OF_DANGO = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).build();
    public static final FoodProperties IMMORTAL_DUMPLINGS = new FoodProperties.Builder().nutrition(7).saturationMod(0.5f).build();
    public static final FoodProperties A_STRING_OF_DANGO = new FoodProperties.Builder().nutrition(5).saturationMod(0.5f).build();
    public static final FoodProperties TURBID_SPIRITUAL_ENERGY = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER,600), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION,600), 1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.UNLUCK, 600), 1.0f).build();
    public static final FoodProperties PURE_SPIRITUAL_ENERGY = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.LUCK,600), 1.0f).build();

    public static final FoodProperties SOLDIER_CANDY = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).build();
    public static final FoodProperties TROOP_CANDY = new FoodProperties.Builder().nutrition(7).saturationMod(0.5f).build();
    public static final FoodProperties OIL_TOFU = new FoodProperties.Builder().nutrition(2).saturationMod(0.5f).build();
}
