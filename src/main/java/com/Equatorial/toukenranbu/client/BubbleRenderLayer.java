package com.Equatorial.toukenranbu.client;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class BubbleRenderLayer<T extends ToukenDanshiEntity> extends GeoRenderLayer<T> {

    private static final ResourceLocation TEX_LEFT = tex("bubble_left");
    private static final ResourceLocation TEX_MID = tex("bubble_mid");
    private static final ResourceLocation TEX_RIGHT = tex("bubble_right");
    private static final ResourceLocation TEX_TAIL = tex("bubble_tail");

    private static final int LEFT_W = 12;
    private static final int RIGHT_W = 12;
    private static final int MID_W = 16;
    private static final int BUBBLE_H = 32;
    private static final int TAIL_W = 12;
    private static final int TAIL_H = 6;
    private static final int PADDING = 6;
    private static final float Y_OFFSET = 6.0f;

    public BubbleRenderLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }

    private static ResourceLocation tex(String name) {
        return ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID,
                "textures/gui/bubble/" + name + ".png");
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel,
                       RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                       float partialTick, int packedLight, int packedOverlay) {

        String key = animatable.getBubbleKey();

        if (key == null || key.isEmpty()) return;
        if (animatable.level().getGameTime() >= animatable.getBubbleUntil()) return;

        String text = resolveText(key, animatable.getBubbleVariant());
        if (text.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();

        poseStack.pushPose();
        poseStack.translate(0, animatable.getBbHeight() + Y_OFFSET, 0);
        poseStack.mulPose(mc.getEntityRenderDispatcher().cameraOrientation());
        poseStack.scale(-0.025f, -0.025f, 0.025f);

        drawBubble(poseStack, bufferSource, mc.font, text);

        poseStack.popPose();
    }

    private void drawBubble(PoseStack pose, MultiBufferSource buffer, Font font, String text) {
        int packedLight = 0xF000F0;

        // 设置文字放大倍数（1.3f 是放大 30%）
        float textScale = 1.5f;

        // 气泡的宽度根据放大后的文字宽度来算
        int textW = (int) (font.width(text) * textScale);
        int bodyTargetW = textW + PADDING * 2;
        int midRepeat = Math.max(1, (int) Math.ceil((bodyTargetW - LEFT_W - RIGHT_W) / (float) MID_W));
        int totalW = LEFT_W + midRepeat * MID_W + RIGHT_W;
        int totalH = BUBBLE_H + TAIL_H;

        float baseX = -totalW / 2f;
        float baseY = -totalH / 2f; // 整个气泡（包含尾巴）的顶部

        // --- 1. 画背景框 ---
        drawQuad(pose, buffer, TEX_LEFT, baseX, baseY, baseX + LEFT_W, baseY + BUBBLE_H, packedLight);
        for (int i = 0; i < midRepeat; i++) {
            float x1 = baseX + LEFT_W + i * MID_W;
            drawQuad(pose, buffer, TEX_MID, x1, baseY, x1 + MID_W, baseY + BUBBLE_H, packedLight);
        }
        drawQuad(pose, buffer, TEX_RIGHT, baseX + LEFT_W + midRepeat * MID_W, baseY,
                baseX + totalW, baseY + BUBBLE_H, packedLight);
        float tailX = baseX + (totalW - TAIL_W) / 2f;
        drawQuad(pose, buffer, TEX_TAIL, tailX, baseY + BUBBLE_H,
                tailX + TAIL_W, baseY + totalH, packedLight);

        // --- 2. 画文字（绝对居中版） ---
        pose.pushPose();
        pose.scale(textScale, textScale, 1.0f); // 放大文字
        Matrix4f matrix4f = pose.last().pose();

        // X轴居中：负的文字宽度一半
        float textX = -font.width(text) / 2f;

        // Y轴居中：气泡主体（不含尾巴）的中心点 - 文字高度的一半，然后再除以缩放比例
        float bubbleCenterY = baseY + (BUBBLE_H / 2f);
        float textY = (bubbleCenterY - (font.lineHeight / 2f)) / textScale;

        // 绘制文字（黑色，无阴影）
        font.drawInBatch(text, textX, textY, 0xFF000000, false, matrix4f, buffer,
                Font.DisplayMode.NORMAL, 0, packedLight);

        pose.popPose();
    }

    private void drawQuad(PoseStack pose, MultiBufferSource buffer, ResourceLocation texture,
                          float x1, float y1, float x2, float y2, int packedLight) {
        VertexConsumer vc = buffer.getBuffer(RenderType.entityTranslucent(texture));
        Matrix4f m = pose.last().pose();
        vc.vertex(m, x1, y2, 0).color(255, 255, 255, 255).uv(0f, 1f)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vc.vertex(m, x2, y2, 0).color(255, 255, 255, 255).uv(1f, 1f)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vc.vertex(m, x2, y1, 0).color(255, 255, 255, 255).uv(1f, 0f)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
        vc.vertex(m, x1, y1, 0).color(255, 255, 255, 255).uv(0f, 0f)
                .overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(0, 1, 0).endVertex();
    }

    private static String resolveText(String key, int variant) {

        // 新增：如果不是标准的翻译键（比如是颜文字），直接原样返回
        if (!key.startsWith("gui.") && !key.startsWith("message.") && !key.startsWith("entity.")) {
            return key;
        }

        String raw = net.minecraft.client.resources.language.I18n.get(key);
        if (raw == null || raw.isEmpty() || raw.equals(key)) {
            String fallback = tryFallback(key);
            if (fallback == null) return "";
            raw = fallback;
        }
        String[] sentences = raw.split("\\|");
        if (sentences.length == 0) return "";
        return sentences[Math.abs(variant) % sentences.length].trim();
    }

    private static String tryFallback(String key) {
        if (key.startsWith("gui.toukenranbu.bubble.danshi_talk.")) {
            String rest = key.substring("gui.toukenranbu.bubble.danshi_talk.".length());
            int toIdx = rest.indexOf(".to.");
            if (toIdx > 0) {
                String speakerOnly = "gui.toukenranbu.bubble.danshi_talk." + rest.substring(0, toIdx);
                String v = net.minecraft.client.resources.language.I18n.get(speakerOnly);
                if (v != null && !v.isEmpty() && !v.equals(speakerOnly)) return v;
            }
            String generic = net.minecraft.client.resources.language.I18n.get("gui.toukenranbu.bubble.danshi_talk");
            if (generic != null && !generic.isEmpty() && !generic.equals("gui.toukenranbu.bubble.danshi_talk")) return generic;
            return null;
        }
        if (key.startsWith("gui.toukenranbu.bubble.danshi_talk_other.")) {
            String rest = key.substring("gui.toukenranbu.bubble.danshi_talk_other.".length());
            int toIdx = rest.indexOf(".to.");
            if (toIdx > 0) {
                String speakerOnly = "gui.toukenranbu.bubble.danshi_talk_other." + rest.substring(0, toIdx);
                String v = net.minecraft.client.resources.language.I18n.get(speakerOnly);
                if (v != null && !v.isEmpty() && !v.equals(speakerOnly)) return v;
            }
            String generic = net.minecraft.client.resources.language.I18n.get("gui.toukenranbu.bubble.danshi_talk_other");
            if (generic != null && !generic.isEmpty() && !generic.equals("gui.toukenranbu.bubble.danshi_talk_other")) return generic;
            return null;
        }
        return null;
    }
}