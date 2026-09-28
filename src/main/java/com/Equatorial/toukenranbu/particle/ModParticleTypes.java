package com.Equatorial.toukenranbu.particle;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ToukenRanbuMod.MOD_ID);

    // 光点
    public static final RegistryObject<SimpleParticleType> MOONLIGHT =
            PARTICLE_TYPES.register("moonlight",
                    () -> new SimpleParticleType(false));

    // 花瓣
    public static final RegistryObject<SimpleParticleType> BLUE_CHERRY =
            PARTICLE_TYPES.register("blue_cherry",
                    () -> new SimpleParticleType(false));

    // 阵风花瓣
    public static final RegistryObject<SimpleParticleType> BREEZE_PETAL =
            PARTICLE_TYPES.register("breeze_petal",
                    () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}