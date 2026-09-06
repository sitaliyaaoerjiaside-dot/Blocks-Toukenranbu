package com.Equatorial.toukenranbu.entity.renderer.tachi;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.tachi.TsurumaruKuninagaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TsurumaruKuninagaRenderer extends GeoEntityRenderer<TsurumaruKuninagaEntity> {
    public TsurumaruKuninagaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TsurumaruKuninagaModel());
        this.shadowRadius = 0.4f;
    }
    @Override
    public ResourceLocation getTextureLocation(TsurumaruKuninagaEntity instance) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/entity/tsurumaru_kuninaga.png");
    }
    @Override
    public RenderType getRenderType(TsurumaruKuninagaEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack,
                                    TsurumaruKuninagaEntity animatable, BakedGeoModel model, boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scale = 0.49f;
        poseStack.scale(scale, scale, scale);
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model,
                isReRender, partialTick, packedLight, packedOverlay);
    }
}