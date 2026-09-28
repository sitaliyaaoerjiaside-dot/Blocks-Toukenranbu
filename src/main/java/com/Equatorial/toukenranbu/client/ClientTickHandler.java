package com.Equatorial.toukenranbu.client;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.particle.ModParticleTypes;
import com.Equatorial.toukenranbu.world.registry.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientTickHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;
        if (!mc.level.dimension().equals(ModDimensions.WHITE_NIGHT_GARDEN_LEVEL)) return;

        // ===== 光点：保持原样，稀疏一点 =====
        if (mc.level.random.nextInt(4) == 0) {
            double x = mc.player.getX() + (mc.level.random.nextDouble() - 0.5) * 32;
            double y = mc.player.getY() + mc.level.random.nextDouble() * 12;
            double z = mc.player.getZ() + (mc.level.random.nextDouble() - 0.5) * 32;
            mc.level.addParticle(ModParticleTypes.MOONLIGHT.get(), x, y, z, 0, 0.01, 0);
        }

        // ===== 花瓣：持续微风，全图飘 =====
        // 每 tick 有 1/2 概率生成，一次 6-9 片
        if (mc.level.random.nextInt(2) != 0) return;

        int count = 6 + mc.level.random.nextInt(4);
        for (int i = 0; i < count; i++) {
            // 在玩家周围 60 格范围内随机生成
            double x = mc.player.getX() + (mc.level.random.nextDouble() - 0.5) * 60;
            double y = mc.player.getY() + 2 + mc.level.random.nextDouble() * 12;
            double z = mc.player.getZ() + (mc.level.random.nextDouble() - 0.5) * 60;

            // 初始速度给予一点风的初速度
            double xSpeed = 0.04 + mc.level.random.nextDouble() * 0.02;
            double zSpeed = 0.02 + mc.level.random.nextDouble() * 0.01;

            mc.level.addParticle(ModParticleTypes.BREEZE_PETAL.get(),
                    x, y, z,
                    xSpeed, -0.005, zSpeed);
        }
    }
}