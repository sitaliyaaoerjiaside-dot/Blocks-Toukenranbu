package com.Equatorial.toukenranbu.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class WhiteNightGardenEffects extends DimensionSpecialEffects {

    public WhiteNightGardenEffects() {
        super(192.0F, true, SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
        return biomeFogColor;
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack,
                             Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return false;
    }

    @Override
    public void adjustLightmapColors(ClientLevel level, float partialTicks, float skyDarken,
                                     float blockLightRedFlicker, float skyLight, int pixelX, int pixelY,
                                     Vector3f colors) {
        colors.x *= 0.92f;
        colors.y *= 0.96f;
        colors.z *= 1.05f;
    }
}