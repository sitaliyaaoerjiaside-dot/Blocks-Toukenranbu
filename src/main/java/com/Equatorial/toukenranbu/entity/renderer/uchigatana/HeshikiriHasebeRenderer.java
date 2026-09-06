package com.Equatorial.toukenranbu.entity.renderer.uchigatana;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.uchigatana.HeshikiriHasebeEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HeshikiriHasebeRenderer extends GeoEntityRenderer<HeshikiriHasebeEntity> {
    public HeshikiriHasebeRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HeshikiriHasebeModel());
        this.shadowRadius = 0.4f;
    }
    @Override
    public ResourceLocation getTextureLocation(HeshikiriHasebeEntity instance) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/heshikiri_hasebe.png");
    }
    @Override
    public RenderType getRenderType(HeshikiriHasebeEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack,
                                    HeshikiriHasebeEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scale = 0.49f;
        poseStack.scale(scale, scale, scale);
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model,
                isReRender, partialTick, packedLight, packedOverlay);
    }
}