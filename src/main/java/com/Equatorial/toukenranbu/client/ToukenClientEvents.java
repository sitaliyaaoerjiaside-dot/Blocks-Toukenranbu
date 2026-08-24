package com.Equatorial.toukenranbu.client;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = "toukenranbu_mod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ToukenClientEvents {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        PoseStack pose = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        Vec3 camPos = event.getCamera().getPosition();

        for (Entity entity : mc.level.entitiesForRendering()) {
            if (!(entity instanceof ToukenDanshiEntity danshi)) continue;

            Vec3 pos = entity.getPosition(event.getPartialTick());
            double x = pos.x;
            double y = pos.y;
            double z = pos.z;

            int fatigue = danshi.toukenData.fatigue;
            ResourceLocation emote;
            if (fatigue >= 50) {
                emote = ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "textures/emote/sakura.png");
            } else if (fatigue >= 40) {
                emote = ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "textures/emote/normal.png");
            } else if (fatigue >= 20) {
                emote = ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "textures/emote/tired.png");
            } else {
                emote = ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "textures/emote/exhausted.png");
            }

            pose.pushPose();
            pose.translate(x - camPos.x, y - camPos.y + entity.getBbHeight() + 0.8, z - camPos.z);
            pose.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
            pose.mulPose(Axis.YP.rotationDegrees(180));
            pose.scale(-0.025f, 0.025f, 0.025f);

            VertexConsumer builder = buffer.getBuffer(RenderType.entityCutoutNoCull(emote));
            Matrix4f m = pose.last().pose();
            float s = 8f;
            builder.vertex(m, -s, -s, 0).color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(0,1,0).endVertex();
            builder.vertex(m,  s, -s, 0).color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(0,1,0).endVertex();
            builder.vertex(m,  s,  s, 0).color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(0,1,0).endVertex();
            builder.vertex(m, -s,  s, 0).color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(0,1,0).endVertex();

            pose.popPose();
        }
    }
}