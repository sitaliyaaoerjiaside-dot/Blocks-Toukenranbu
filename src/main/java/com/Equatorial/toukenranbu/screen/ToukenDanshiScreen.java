package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.network.ToukenDanshiActionPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ToukenDanshiScreen extends AbstractContainerScreen<ToukenDanshiMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/gui/touken_danshi.png");

    private Button sitButton;
    private Button followButton;
    private Button farmButton;

    private static final int BUTTON_X = 120;
    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 18;
    private static final int BUTTON_START_Y = 80;    // ← 下移（原来是60）
    private static final int BUTTON_SPACING = 20;

    private static final int ENTITY_RENDER_X = 144;  // 位置
    private static final int ENTITY_RENDER_Y = 72;   // 这个是实体渲染的高度。
    private static final int ENTITY_RENDER_SCALE = 25;

    public ToukenDanshiScreen(ToukenDanshiMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 222;
    }

    @Override
    protected void init() {
        super.init();
        // 显示标题，隐藏物品栏标签（可选）
        this.titleLabelY = 3;
        this.inventoryLabelY = 10000;

        int gx = this.leftPos;
        int gy = this.topPos;

        this.sitButton = Button.builder(Component.translatable("gui.toukenranbu.button.sit"), btn -> sendAction(0))
                .pos(gx + BUTTON_X, gy + BUTTON_START_Y)
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .build();

        this.followButton = Button.builder(Component.translatable("gui.toukenranbu.button.follow"), btn -> sendAction(1))
                .pos(gx + BUTTON_X, gy + BUTTON_START_Y + BUTTON_SPACING)
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .build();

        this.farmButton = Button.builder(Component.translatable("gui.toukenranbu.button.farm"), btn -> sendAction(2))
                .pos(gx + BUTTON_X, gy + BUTTON_START_Y + BUTTON_SPACING * 2)
                .size(BUTTON_WIDTH, BUTTON_HEIGHT)
                .build();

        this.addRenderableWidget(this.sitButton);
        this.addRenderableWidget(this.followButton);
        this.addRenderableWidget(this.farmButton);
    }

    private void sendAction(int action) {
        if (menu.getEntity() != null) {
            ToukenDanshiActionPacket.send(menu.getEntity().getId(), action);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.menu.getEntity() != null) {
            var entity = this.menu.getEntity();

            this.sitButton.setMessage(Component.translatable(
                    entity.isOrderedToSit() ? "gui.toukenranbu.button.stand" : "gui.toukenranbu.button.sit"
            ).withStyle(entity.isOrderedToSit() ? ChatFormatting.GREEN : ChatFormatting.GRAY));

            this.followButton.setMessage(Component.translatable(
                    entity.isFollowing() ? "gui.toukenranbu.button.unfollow" : "gui.toukenranbu.button.follow"
            ).withStyle(entity.isFollowing() ? ChatFormatting.RED : ChatFormatting.GREEN));

            boolean hasSeeds = entity.hasSeeds();
            String farmKey;
            ChatFormatting farmColor;

            if (entity.isFarming()) {
                if (entity.isFarmingEscaping()) {
                    farmKey = "gui.toukenranbu.button.farm_escaping";
                    farmColor = ChatFormatting.GOLD;
                } else {
                    farmKey = "gui.toukenranbu.button.stop_farm";
                    farmColor = ChatFormatting.RED;
                }
            } else {
                farmKey = hasSeeds ? "gui.toukenranbu.button.farm" : "gui.toukenranbu.button.farm_no_seeds";
                farmColor = hasSeeds ? ChatFormatting.DARK_GREEN : ChatFormatting.GRAY;
            }

            this.farmButton.setMessage(Component.translatable(farmKey).withStyle(farmColor));
            this.farmButton.active = hasSeeds || entity.isFarming();
        }

        renderBackground(guiGraphics);
        renderEntity(guiGraphics, this.leftPos + ENTITY_RENDER_X, this.topPos + ENTITY_RENDER_Y, ENTITY_RENDER_SCALE, mouseX, mouseY);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderEntity(GuiGraphics guiGraphics, int x, int y, int scale, int mouseX, int mouseY) {
        if (this.menu.getEntity() != null) {
            float offsetX = (float) x - mouseX;
            float offsetY = (float) (y - 50) - mouseY;
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    guiGraphics, x, y, scale, offsetX, offsetY, this.menu.getEntity()
            );
        }
    }
}
