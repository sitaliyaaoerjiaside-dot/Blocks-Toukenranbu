package com.Equatorial.toukenranbu.client.gui;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.network.ClientSpiritPowerData;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.lang.reflect.Field;

public class SpiritPowerHud {

    private static final ResourceLocation BAR_EMPTY =
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/gui/spirit_power_bar_empty.png");

    private static final ResourceLocation BAR_FULL =
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/gui/spirit_power_bar_full.png");

    private static final Field OVERLAY_MESSAGE_TIME_FIELD;
    private static final boolean REFLECTION_SUCCESS;

    static {
        Field field = null;
        boolean success = false;
        try {
            field = net.minecraft.client.gui.Gui.class.getDeclaredField("overlayMessageTime");
            field.setAccessible(true);
            success = true;
        } catch (NoSuchFieldException e) {
            System.err.println("[SpiritPowerHud] Failed to find overlayMessageTime field");
        }
        OVERLAY_MESSAGE_TIME_FIELD = field;
        REFLECTION_SUCCESS = success;
    }

    public static final IGuiOverlay OVERLAY = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        if (mc.gameMode.getPlayerMode() == GameType.CREATIVE
                || mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
            return;
        }

        int spiritPower = ClientSpiritPowerData.getSpiritPower();
        int maxSpiritPower = ClientSpiritPowerData.getMaxSpiritPower();

        int x = screenWidth / 2 + 10;
        int y = screenHeight - 52;

        int barWidth = 81;
        int barHeight = 9;

        int filledWidth = (int) ((spiritPower / (float) maxSpiritPower) * barWidth);

        float alpha = getHudAlpha(mc);

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0, 0, 5000);

        if (alpha < 1.0f) {
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
        }

        guiGraphics.blit(BAR_EMPTY, x, y, 0, 0, barWidth, barHeight, barWidth, barHeight);

        if (filledWidth > 0) {
            guiGraphics.blit(BAR_FULL, x, y, 0, 0, filledWidth, barHeight, barWidth, barHeight);
        }

        if (alpha < 1.0f) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
        }

        // 文字保持在条右侧，也应用透明度
        String text = spiritPower + "/" + maxSpiritPower;
        int textAlpha = (int) (alpha * 255) << 24;
        int textColor = (0xFFFFFF & 0x00FFFFFF) | textAlpha;
        guiGraphics.drawString(mc.font, text, x + barWidth + 2, y + 1, textColor);

        guiGraphics.pose().popPose();
    };

    private static float getHudAlpha(Minecraft mc) {
        if (!REFLECTION_SUCCESS) {
            return 1.0f;
        }

        try {
            int overlayMessageTime = (int) OVERLAY_MESSAGE_TIME_FIELD.get(mc.gui);

            if (overlayMessageTime > 0) {
                return 0.3f;
            }
        } catch (IllegalAccessException e) {

        }

        return 1.0f;
    }
}
