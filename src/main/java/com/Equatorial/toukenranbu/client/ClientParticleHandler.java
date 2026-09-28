package com.Equatorial.toukenranbu.client;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.client.particle.BlueCherryPetalFallParticle;
import com.Equatorial.toukenranbu.client.particle.BreezePetalParticle;
import com.Equatorial.toukenranbu.client.particle.MoonlightParticle;
import com.Equatorial.toukenranbu.particle.ModParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientParticleHandler {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.MOONLIGHT.get(),
                MoonlightParticle.Provider::new);

        event.registerSpriteSet(ModParticleTypes.BLUE_CHERRY.get(),
                BlueCherryPetalFallParticle.Provider::new);

        event.registerSpriteSet(ModParticleTypes.BREEZE_PETAL.get(),
                BreezePetalParticle.Provider::new);
    }
}