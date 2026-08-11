package com.Equatorial.toukenranbu.effect;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ToukenRanbuMod.MOD_ID);

    public static final RegistryObject<MobEffect> SPIRIT_REGEN =
            EFFECTS.register("spirit_regen", SpiritRegenEffect::new);
}