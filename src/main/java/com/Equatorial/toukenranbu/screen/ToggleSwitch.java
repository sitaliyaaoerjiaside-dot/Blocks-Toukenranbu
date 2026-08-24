package com.Equatorial.toukenranbu.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class ToggleSwitch extends AbstractWidget {
    private boolean value;
    private final Consumer<Boolean> onChange;
    private float anim = 0f;

    public ToggleSwitch(int x, int y, boolean initial, Consumer<Boolean> onChange) {
        super(x, y, 36, 16, Component.empty());
        this.value = initial;
        this.anim = initial ? 1f : 0f;
        this.onChange = onChange;
    }

    @Override
    protected void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        float target = value ? 1f : 0f;
        anim += (target - anim) * 0.4f;
        if (Math.abs(target - anim) < 0.005f) anim = target;

        int x = getX(), y = getY(), w = width, h = height;

        int col = mix(0xFF9E9E9E, 0xFF4CAF50, anim);
        gui.fill(x + 4, y + 4, x + w - 4, y + h - 4, col);
        gui.fill(x + 3, y + 3, x + w - 3, y + 4, col);
        gui.fill(x + 3, y + h - 4, x + w - 3, y + h - 3, col);
        gui.fill(x + 2, y + 5, x + 3, y + h - 5, col);
        gui.fill(x + w - 3, y + 5, x + w - 2, y + h - 5, col);

        int ts = h - 2;
        int tx = x + 2 + Math.round((w - 4 - ts) * anim);
        int ty = y + 1;
        gui.fill(tx, ty + ts - 1, tx + ts, ty + ts, 0xFFAAAAAA);
        gui.fill(tx, ty, tx + ts, ty + ts - 1, 0xFFFFFFFF);
        gui.fill(tx + 1, ty + 1, tx + ts - 1, ty + 2, 0xFFF5F5F5);
    }

    private int mix(int a, int b, float t) {
        int r1 = (a >> 16) & 0xFF, g1 = (a >> 8) & 0xFF, b1 = a & 0xFF;
        int r2 = (b >> 16) & 0xFF, g2 = (b >> 8) & 0xFF, b2 = b & 0xFF;
        return 0xFF000000
                | ((int)(r1 + (r2 - r1) * t) << 16)
                | ((int)(g1 + (g2 - g1) * t) << 8)
                | (int)(b1 + (b2 - b1) * t);
    }

    @Override
    public void onClick(double mx, double my) {
        this.value = !this.value;
        this.onChange.accept(this.value);
    }

    public boolean getValue() { return value; }
    public void setValue(boolean v) { this.value = v; }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput out) {}
}