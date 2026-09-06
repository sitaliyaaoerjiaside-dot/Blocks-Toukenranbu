package com.Equatorial.toukenranbu.entity.renderer.tachi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.tachi.IchigoHitofuriEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IchigoHitofuriRenderer extends GeoEntityRenderer<IchigoHitofuriEntity> {
    public IchigoHitofuriRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new IchigoHitofuriModel());
        this.shadowRadius = 0.4f;
    }
    @Override
    public ResourceLocation getTextureLocation(IchigoHitofuriEntity instance) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/ichigo_hitofuri.png");
    }
    @Override
    public RenderType getRenderType(IchigoHitofuriEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack,
                                    IchigoHitofuriEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scale = 0.49f;
        poseStack.scale(scale, scale, scale);
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model,
                isReRender, partialTick, packedLight, packedOverlay);
    }
}