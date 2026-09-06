package com.Equatorial.toukenranbu.entity.renderer.naginata;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.naginata.TomoegataNaginataEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TomoegataNaginataRenderer extends GeoEntityRenderer<TomoegataNaginataEntity> {
    public TomoegataNaginataRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TomoegataNaginataModel());
        this.shadowRadius = 0.55f;
    }
    @Override
    public ResourceLocation getTextureLocation(TomoegataNaginataEntity instance) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tomoegata_naginata.png");
    }
    @Override
    public RenderType getRenderType(TomoegataNaginataEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack,
                                    TomoegataNaginataEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scale = 0.59f;
        poseStack.scale(scale, scale, scale);
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model,
                isReRender, partialTick, packedLight, packedOverlay);
    }
}