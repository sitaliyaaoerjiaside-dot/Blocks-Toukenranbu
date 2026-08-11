package com.Equatorial.toukenranbu.entity.renderer;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.custom.YariMAXEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HistoryRetrogradeArmyYariMAXRenderer extends GeoEntityRenderer<YariMAXEntity> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            ToukenRanbuMod.MOD_ID, "textures/entity/yarimax_texture.png");
    private static final ResourceLocation GLOW_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            ToukenRanbuMod.MOD_ID, "textures/entity/yarimax_texture_glow.png");

    private boolean isRenderingGlow = false;

    public HistoryRetrogradeArmyYariMAXRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HistoryRetrogradeArmyYariMAXModel());
        this.shadowRadius = 0.51f;
    }
    @Override
    public void scaleModelForRender(float widthScale, float heightScale, PoseStack poseStack,
                                    YariMAXEntity animatable, BakedGeoModel model,
                                    boolean isReRender, float partialTick, int packedLight, int packedOverlay) {
        float scale = 0.51f;
        poseStack.scale(scale, scale, scale);
        super.scaleModelForRender(widthScale, heightScale, poseStack, animatable, model,
                isReRender, partialTick, packedLight, packedOverlay);
    }

    @Override
    public ResourceLocation getTextureLocation(YariMAXEntity instance) {
        return isRenderingGlow ? GLOW_TEXTURE : TEXTURE;
    }

    @Override
    public RenderType getRenderType(YariMAXEntity animatable, ResourceLocation texture,
                                    @Nullable MultiBufferSource bufferSource, float partialTick) {
        if (isRenderingGlow) {
            return RenderType.eyes(texture); // 自发光，无视场景光照
        }
        return super.getRenderType(animatable, texture, bufferSource, partialTick); // 正常受光
    }

    @Override
    public void render(YariMAXEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        // 第一遍：正常贴图
        isRenderingGlow = false;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        // 第二遍：发光贴图叠加（纯黑区域不显示，有颜色的区域全亮度发光）
        isRenderingGlow = true;
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        isRenderingGlow = false;
    }
}