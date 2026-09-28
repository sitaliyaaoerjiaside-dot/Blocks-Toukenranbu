package com.Equatorial.toukenranbu.client;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, value = Dist.CLIENT)
public class WhiteMikazukiBossBarRenderer {

    private static final ResourceLocation BAR_BG = ResourceLocation.fromNamespaceAndPath(
            ToukenRanbuMod.MOD_ID, "textures/gui/white_mikazuki_bar_bg.png");
    private static final ResourceLocation BAR_FILL = ResourceLocation.fromNamespaceAndPath(
            ToukenRanbuMod.MOD_ID, "textures/gui/white_mikazuki_bar_fill.png");
    private static final ResourceLocation BAR_FRAME = ResourceLocation.fromNamespaceAndPath(
            ToukenRanbuMod.MOD_ID, "textures/gui/white_mikazuki_bar_frame.png");
    private static final ResourceLocation BAR_DECO = ResourceLocation.fromNamespaceAndPath(
            ToukenRanbuMod.MOD_ID, "textures/gui/white_mikazuki_bar_deco.png");

    private static final int TEX_WIDTH = 512;
    private static final int TEX_HEIGHT = 64;

    // === 屏幕显示尺寸：改这两个数调大小 ===
    private static final int RENDER_WIDTH = 240;
    private static final int RENDER_HEIGHT = 30;

    private static final int SCREEN_MARGIN_TOP = 2;

    private static final String BOSS_BAR_MARKER = "\u200B";

    @SubscribeEvent
    public static void onBossBarRender(CustomizeGuiOverlayEvent.BossEventProgress event) {
        Component bossName = event.getBossEvent().getName();
        String rawName = bossName.getString();
        if (!rawName.startsWith(BOSS_BAR_MARKER)) return;

        event.setCanceled(true);

        GuiGraphics gui = event.getGuiGraphics();
        float progress = event.getBossEvent().getProgress();

        Minecraft mc = Minecraft.getInstance();
        int screenW = mc.getWindow().getGuiScaledWidth();
        int x = (screenW - RENDER_WIDTH) / 2;
        int y = SCREEN_MARGIN_TOP;

        // 1. 底图
        gui.blit(BAR_BG, x, y, RENDER_WIDTH, RENDER_HEIGHT,
                0, 0, TEX_WIDTH, TEX_HEIGHT, TEX_WIDTH, TEX_HEIGHT);

        // 2. 填充
        int fillWidth = (int) (RENDER_WIDTH * progress);
        if (fillWidth > 0) {
            gui.blit(BAR_FILL, x, y, fillWidth, RENDER_HEIGHT,
                    0, 0, (int) (TEX_WIDTH * progress), TEX_HEIGHT, TEX_WIDTH, TEX_HEIGHT);
        }

        // 3. 边框
        gui.blit(BAR_FRAME, x, y, RENDER_WIDTH, RENDER_HEIGHT,
                0, 0, TEX_WIDTH, TEX_HEIGHT, TEX_WIDTH, TEX_HEIGHT);

        // 4. 装饰
        gui.blit(BAR_DECO, x, y, RENDER_WIDTH, RENDER_HEIGHT,
                0, 0, TEX_WIDTH, TEX_HEIGHT, TEX_WIDTH, TEX_HEIGHT);

        // 5. 名字（自己画）
        Font font = mc.font;
        // 去掉零宽空格前缀，不要画出来
        Component displayName = Component.literal(rawName.substring(BOSS_BAR_MARKER.length()));
        int nameWidth = font.width(displayName);

        // 名字放血条下方居中
        int nameX = x + (RENDER_WIDTH - nameWidth) / 2;
        int nameY = y + RENDER_HEIGHT + 2;

        // 先画黑边（阴影），再画白字，更清晰
        gui.drawString(font, displayName, nameX, nameY, 0xFFFFFF, true);

        // 如果想名字放血条上面，改这一行：
        // int nameY = y - font.lineHeight - 2;
    }
}