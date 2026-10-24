package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.player.Inventory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ToukenScreen extends AbstractContainerScreen<ToukenContainerMenu> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "textures/gui/touken_gui.png");
    private boolean showAIPanel = false;

    public ToukenScreen(ToukenContainerMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 256;
        this.imageHeight = 240;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);

        ToukenDanshiEntity entity = this.menu.getEntity();

        if (entity != null && entity.isAlive()) {

            // ===== 1. 3D 模型预览渲染 =====
            int modelX = leftPos + 50;
            int modelY = topPos + 75;
            int scale = 25;

            float xAngle = (float) (modelX - mouseX);
            float yAngle = (float) (modelY - 50 - mouseY);

            net.minecraft.client.gui.screens.inventory.InventoryScreen.renderEntityInInventoryFollowsMouse(
                    graphics, modelX, modelY, scale, xAngle, yAngle, entity);

            // ===== 2. 药水效果渲染 =====
            Collection<MobEffectInstance> effects = entity.getActiveEffects();
            if (!effects.isEmpty()) {
                int startX = leftPos + 85;
                int startY = topPos + 112;
                int index = 0;

                for (MobEffectInstance effect : effects) {
                    TextureAtlasSprite sprite = Minecraft.getInstance()
                            .getMobEffectTextures().get(effect.getEffect());

                    if (sprite != null) {
                        int iconX = startX + (index * 20);
                        int iconY = startY;

                        graphics.blit(iconX, iconY, 0, 18, 18, sprite);

                        int amplifier = effect.getAmplifier();
                        if (amplifier > 0) {
                            String levelText = String.valueOf(amplifier + 1);
                            graphics.drawString(Minecraft.getInstance().font, levelText,
                                    iconX + 10, iconY + 10, 0xFFFFFF, true);
                        }

                        if (!showAIPanel && mouseX >= iconX && mouseX <= iconX + 18 && mouseY >= iconY && mouseY <= iconY + 18) {
                            graphics.renderTooltip(Minecraft.getInstance().font,
                                    List.of(
                                            effect.getEffect().getDisplayName(),
                                            MobEffectUtil.formatDuration(effect, 1.0F)
                                    ),
                                    Optional.empty(), mouseX, mouseY);
                        }

                        index++;
                        if (index >= 10) break;
                    }
                }
            }

            // ===== 3. 绘制等级与经验 =====
            int expX = leftPos + 85;
            int levelY = topPos + 70;
            int expBarY = topPos + 93;
            int expBarWidth = 70;
            int expBarHeight = 6;

            String levelStr = Component.translatable("gui.toukenranbu.label.level_value", entity.toukenData.level).getString();
            graphics.drawString(this.font, levelStr, expX, levelY, 0x404040, false);

            long curExpInLevel = entity.toukenData.experience - entity.toukenData.getCurrentLevelExp();
            long needExpForNext = entity.toukenData.getNextLevelExp() - entity.toukenData.getCurrentLevelExp();
            if (entity.toukenData.level >= 99) {
                curExpInLevel = 1;
                needExpForNext = 1;
            }
            String curExpStr = formatExp(curExpInLevel);
            String needExpStr = formatExp(needExpForNext);
            String expStr = Component.translatable("gui.toukenranbu.label.experience").getString() + " " + curExpStr + " / " + needExpStr;
            graphics.drawString(this.font, expStr, expX, expBarY - 10, 0x404040, false);

            graphics.fill(expX, expBarY, expX + expBarWidth, expBarY + expBarHeight, 0xFF555555);

            float levelProgress = entity.toukenData.getLevelProgress();
            if (levelProgress > 1.0f) levelProgress = 1.0f;
            int expFillWidth = (int) (expBarWidth * levelProgress);
            graphics.fill(expX, expBarY, expX + expFillWidth, expBarY + expBarHeight, 0xFF55FF55);

            // ===== 3.5 绘制槽位底部的文字标签 =====

            // 1. 马匹文字
            String mountText = Component.translatable("gui.toukenranbu.label.mount").getString();
            // 手动计算居中位置
            int mountX = (leftPos + 114 + 8) - (this.font.width(mountText) / 2);
            // topPos + 30 就是它的高度，想往上挪就把 29 改小
            graphics.drawString(this.font, mountText, mountX, topPos + 29, 0x404040, false);

            // 2. 本体刀文字
            String bladeText = Component.translatable("gui.toukenranbu.label.blade").getString();
            // 手动计算居中位置
            int bladeX = (leftPos + 114 + 8) - (this.font.width(bladeText) / 2);
            // topPos + 66 就是它的高度，想往上挪就把 39 改小
            graphics.drawString(this.font, bladeText, bladeX, topPos + 39, 0x404040, false);

            // 以后加宝物的话，直接复制格式改坐标就行
            // String treasureText = Component.translatable("gui.toukenranbu.label.treasure").getString();
            // int treasureX = (leftPos + 114 + 8) - (this.font.width(treasureText) / 2);
            // graphics.drawString(this.font, treasureText, treasureX, topPos + 102, 0x404040, false);

            // ===== 4. 绘制生命值和疲劳度 =====
            int barX = leftPos + 5;
            int barWidth = 70;
            int barHeight = 8;

            int hpY = topPos + 100;
            int fatigueY = topPos + 115;

            float hpRatio = entity.getHealth() / entity.getMaxHealth();
            if (hpRatio > 1.0f) hpRatio = 1.0f;
            int hpFillWidth = (int) (barWidth * hpRatio);

            graphics.fill(barX, hpY, barX + barWidth, hpY + barHeight, 0xFF555555);
            graphics.fill(barX, hpY, barX + hpFillWidth, hpY + barHeight, 0xFFFF5555);

            String hpText = Component.translatable("gui.toukenranbu.hp",
                    (int) entity.getHealth(), (int) entity.getMaxHealth()).getString();
            graphics.drawString(this.font, hpText, barX + 2, hpY, 0x000000, false);

            float fatigueRatio = entity.toukenData.getFatiguePercent();
            if (fatigueRatio > 1.0f) fatigueRatio = 1.0f;
            if (fatigueRatio < 0.0f) fatigueRatio = 0.0f;
            int fatigueFillWidth = (int) (barWidth * fatigueRatio);

            graphics.fill(barX, fatigueY, barX + barWidth, fatigueY + barHeight, 0xFF555555);
            graphics.fill(barX, fatigueY, barX + fatigueFillWidth, fatigueY + barHeight,
                    entity.toukenData.getFatigueStatusColor());

            String fatigueText = Component.translatable(entity.toukenData.getFatigueStatusKey()).getString();
            graphics.drawString(this.font, fatigueText, barX + 2, fatigueY, 0x000000, false);

            // ===== 5. 绘制六维属性 =====
            int col1X = leftPos + 5;
            int col2X = leftPos + 42;
            int startY = topPos + 128;
            int lineSpacing = 12;

            graphics.drawString(this.font, Component.translatable("gui.toukenranbu.stat.impact", entity.toukenData.getEffectiveImpact()).getString(), col1X, startY, 0x404040, false);
            graphics.drawString(this.font, Component.translatable("gui.toukenranbu.stat.mobility", entity.toukenData.getEffectiveMobility()).getString(), col2X, startY, 0x404040, false);
            graphics.drawString(this.font, Component.translatable("gui.toukenranbu.stat.killing", entity.toukenData.getEffectiveKilling()).getString(), col1X, startY + lineSpacing, 0x404040, false);
            graphics.drawString(this.font, Component.translatable("gui.toukenranbu.stat.scouting", entity.toukenData.getEffectiveScouting()).getString(), col2X, startY + lineSpacing, 0x404040, false);
            graphics.drawString(this.font, Component.translatable("gui.toukenranbu.stat.concealment", entity.toukenData.getEffectiveConcealment()).getString(), col1X, startY + lineSpacing * 2, 0x404040, false);
            graphics.drawString(this.font, Component.translatable("gui.toukenranbu.stat.troops", entity.toukenData.getEffectiveTroops()).getString(), col2X, startY + lineSpacing * 2, 0x404040, false);

            // ===== 6. 绘制阵型按钮 =====
            int formBtnX = leftPos + 5;
            int formBtnY = topPos + 165;
            int formBtnWidth = 70;
            int formBtnHeight = 16;

            boolean isFollowingCaptain = entity.getCaptain() != null && entity.getCaptain() != entity;
            String formText = isFollowingCaptain ?
                    Component.translatable("gui.toukenranbu.formation.follow_captain").getString() :
                    Component.translatable("gui.toukenranbu.formation." + entity.getFormationType().name().toLowerCase()).getString();

            boolean isFormHovered = isMouseOver(mouseX, mouseY, formBtnX, formBtnY, formBtnWidth, formBtnHeight);
            int formBgColor = isFollowingCaptain ? 0xFF555555 : (isFormHovered ? 0xFFAAAAAA : 0xFF888888);
            graphics.fill(formBtnX, formBtnY, formBtnX + formBtnWidth, formBtnY + formBtnHeight, formBgColor);
            graphics.renderOutline(formBtnX, formBtnY, formBtnWidth, formBtnHeight, 0xFF000000);
            graphics.drawCenteredString(this.font, formText, formBtnX + formBtnWidth / 2, formBtnY + 4, isFollowingCaptain ? 0xFFAAAAAA : 0xFFFFFF);

            // ===== 7. 绘制 AI 按钮 =====
            int aiBtnX = leftPos + 5;
            int aiBtnY = topPos + 185;
            int aiBtnWidth = 70;
            int aiBtnHeight = 16;

            boolean isAiHovered = isMouseOver(mouseX, mouseY, aiBtnX, aiBtnY, aiBtnWidth, aiBtnHeight);
            graphics.fill(aiBtnX, aiBtnY, aiBtnX + aiBtnWidth, aiBtnY + aiBtnHeight, isAiHovered ? 0xFFAAAAAA : 0xFF888888);
            graphics.renderOutline(aiBtnX, aiBtnY, aiBtnWidth, aiBtnHeight, 0xFF000000);
            // 使用翻译键
            graphics.drawCenteredString(this.font, Component.translatable("gui.toukenranbu.ai.title").getString(), aiBtnX + aiBtnWidth / 2, aiBtnY + 4, 0xFFFFFF);

            // ===== 8. 绘制跟随捡物按钮 =====
            int pickupBtnX = leftPos + 5;
            int pickupBtnY = topPos + 205;
            int pickupBtnWidth = 70;
            int pickupBtnHeight = 16;

            boolean isPickupHovered = isMouseOver(mouseX, mouseY, pickupBtnX, pickupBtnY, pickupBtnWidth, pickupBtnHeight);
            String pickupText = entity.isPickupWhenFollowing() ?
                    Component.translatable("gui.toukenranbu.label.auto_pickup.on").getString() :
                    Component.translatable("gui.toukenranbu.label.auto_pickup.off").getString();

            int pickupBgColor = entity.isPickupWhenFollowing() ? 0xFF55AA55 : 0xFF888888;
            if (isPickupHovered) pickupBgColor = 0xFFAAAAAA;

            graphics.fill(pickupBtnX, pickupBtnY, pickupBtnX + pickupBtnWidth, pickupBtnY + pickupBtnHeight, pickupBgColor);
            graphics.renderOutline(pickupBtnX, pickupBtnY, pickupBtnWidth, pickupBtnHeight, 0xFF000000);
            graphics.drawCenteredString(this.font, pickupText, pickupBtnX + pickupBtnWidth / 2, pickupBtnY + 4, 0xFFFFFF);
        }

        // ===== 9. 绘制 AI 面板弹窗 =====
        if (showAIPanel) {
            graphics.pose().pushPose();
            graphics.pose().translate(0, 0, 400);

            graphics.fill(0, 0, this.width, this.height, 0x80000000);

            int pWidth = 160;
            int pHeight = 145;
            int pX = (this.width - pWidth) / 2;
            int pY = (this.height - pHeight) / 2;

            graphics.fill(pX, pY, pX + pWidth, pY + pHeight, 0xFFC6C6C6);
            graphics.renderOutline(pX, pY, pWidth, pHeight, 0xFF000000);

            if (entity != null) {
                int X1 = pX + 10, X2 = pX + 85;
                int Y1 = pY + 15, Y2 = Y1 + 22, Y3 = Y2 + 22, Y4 = Y3 + 22;
                int btnW = 65, btnH = 16;

                boolean hasPickaxe = false;
                for (int i = 0; i < entity.getInventoryHandler().getSlots(); i++) {
                    if (entity.getInventoryHandler().getStackInSlot(i).getItem() instanceof net.minecraft.world.item.PickaxeItem) {
                        hasPickaxe = true; break;
                    }
                }
                boolean hasTorch = entity.hasTorches();

                boolean isIdle = !entity.isOrderedToSit() && !entity.isFollowing() && !entity.isFarming()
                        && !entity.isMining() && !entity.isPatrolling() && !entity.isCaveClearing();

                boolean hasPartner = entity.findSparringPartner() != null;
                boolean canMine = entity.isMining() || hasPickaxe;
                boolean canCave = entity.isCaveClearing() || hasTorch;
                boolean canSpar = entity.isSparring() || (isIdle && hasPartner);

                String txt1 = entity.isOrderedToSit() ? Component.translatable("gui.toukenranbu.button.stand").getString() : Component.translatable("gui.toukenranbu.button.sit").getString();
                String txt2 = entity.isFollowing() ? Component.translatable("gui.toukenranbu.button.unfollow").getString() : Component.translatable("gui.toukenranbu.button.follow").getString();
                String txt3 = entity.isFarming() ? Component.translatable("gui.toukenranbu.button.stop_farm").getString() : Component.translatable("gui.toukenranbu.button.farm").getString();
                String txt4 = entity.isMining() ? Component.translatable("gui.toukenranbu.button.stop_mine").getString() : (hasPickaxe ? Component.translatable("gui.toukenranbu.button.mine").getString() : Component.translatable("gui.toukenranbu.button.mine_no_pickaxe").getString());
                String txt5 = entity.isPatrolling() ? Component.translatable("gui.toukenranbu.button.patrolling").getString() : Component.translatable("gui.toukenranbu.button.patrol").getString();

                String txt6;
                if (entity.isSparring()) {
                    txt6 = Component.translatable("gui.toukenranbu.button.sparring").getString();
                } else if (!isIdle) {
                    txt6 = Component.translatable("gui.toukenranbu.message.sparrow_need_idle").getString();
                } else if (hasPartner) {
                    txt6 = Component.translatable("gui.toukenranbu.button.sparrow").getString();
                } else {
                    txt6 = Component.translatable("gui.toukenranbu.message.sparrow_no_partner").getString();
                }

                String txt7 = entity.isCaveClearing() ? Component.translatable("gui.toukenranbu.button.cave_clearing").getString() : (hasTorch ? Component.translatable("gui.toukenranbu.button.cave_clear").getString() : Component.translatable("gui.toukenranbu.button.cave_clear_no_torches").getString());

                drawPanelButton(graphics, mouseX, mouseY, X1, Y1, btnW, btnH, txt1, true);
                drawPanelButton(graphics, mouseX, mouseY, X1, Y2, btnW, btnH, txt3, true);
                drawPanelButton(graphics, mouseX, mouseY, X1, Y3, btnW, btnH, txt5, true);
                drawPanelButton(graphics, mouseX, mouseY, X1, Y4, btnW, btnH, txt7, canCave);

                drawPanelButton(graphics, mouseX, mouseY, X2, Y1, btnW, btnH, txt2, true);
                drawPanelButton(graphics, mouseX, mouseY, X2, Y2, btnW, btnH, txt4, canMine);
                drawPanelButton(graphics, mouseX, mouseY, X2, Y3, btnW, btnH, txt6, canSpar);

                // 关闭按钮使用原版翻译键，或者自定义
                drawPanelButton(graphics, mouseX, mouseY, X2, Y4, btnW, btnH, Component.translatable("gui.done").getString(), true);
            }
            graphics.pose().popPose();
        }

        if (!showAIPanel) {
            this.renderTooltip(graphics, mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        int customX = 5;
        int customY = 89;
        graphics.drawString(this.font, this.title, customX, customY, 0x404040, false);
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        if (showAIPanel) return;
        super.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        ToukenDanshiEntity entity = this.menu.getEntity();
        if (entity != null && entity.isAlive() && button == 0 && this.minecraft != null && this.minecraft.gameMode != null) {

            if (showAIPanel) {
                int pWidth = 160;
                int pHeight = 145;
                int pX = (this.width - pWidth) / 2;
                int pY = (this.height - pHeight) / 2;

                if (mouseX < pX || mouseX > pX + pWidth || mouseY < pY || mouseY > pY + pHeight) {
                    showAIPanel = false;
                    return true;
                }

                int X1 = pX + 10, X2 = pX + 85;
                int Y1 = pY + 15, Y2 = Y1 + 22, Y3 = Y2 + 22, Y4 = Y3 + 22;
                int btnW = 65, btnH = 16;

                boolean hasPickaxe = false;
                for (int i = 0; i < entity.getInventoryHandler().getSlots(); i++) {
                    if (entity.getInventoryHandler().getStackInSlot(i).getItem() instanceof net.minecraft.world.item.PickaxeItem) {
                        hasPickaxe = true; break;
                    }
                }
                boolean hasTorch = entity.hasTorches();
                boolean isIdle = !entity.isOrderedToSit() && !entity.isFollowing() && !entity.isFarming()
                        && !entity.isMining() && !entity.isPatrolling() && !entity.isCaveClearing();
                boolean hasPartner = entity.findSparringPartner() != null;

                int clickedId = -1;
                if (isMouseOver(mouseX, mouseY, X1, Y1, btnW, btnH)) clickedId = 1;
                else if (isMouseOver(mouseX, mouseY, X1, Y2, btnW, btnH)) clickedId = 3;
                else if (isMouseOver(mouseX, mouseY, X1, Y3, btnW, btnH)) clickedId = 5;
                else if (isMouseOver(mouseX, mouseY, X1, Y4, btnW, btnH)) {
                    if (entity.isCaveClearing() || hasTorch) clickedId = 7;
                    else { playClickSound(); return true; }
                }
                else if (isMouseOver(mouseX, mouseY, X2, Y1, btnW, btnH)) clickedId = 2;
                else if (isMouseOver(mouseX, mouseY, X2, Y2, btnW, btnH)) {
                    if (entity.isMining() || hasPickaxe) clickedId = 4;
                    else { playClickSound(); return true; }
                }
                else if (isMouseOver(mouseX, mouseY, X2, Y3, btnW, btnH)) {
                    if (entity.isSparring() || (isIdle && hasPartner)) clickedId = 6;
                    else { playClickSound(); return true; }
                }
                else if (isMouseOver(mouseX, mouseY, X2, Y4, btnW, btnH)) {
                    showAIPanel = false;
                    return true;
                }

                if (clickedId != -1) {
                    playClickSound();
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, clickedId);
                    return true;
                }
                return true;
            }

            int pickupBtnX = leftPos + 5;
            int pickupBtnY = topPos + 205;
            if (isMouseOver(mouseX, mouseY, pickupBtnX, pickupBtnY, 70, 16)) {
                playClickSound();
                this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 8);
                return true;
            }

            int aiBtnX = leftPos + 5;
            int aiBtnY = topPos + 185;
            if (isMouseOver(mouseX, mouseY, aiBtnX, aiBtnY, 70, 16)) {
                showAIPanel = true;
                playClickSound();
                return true;
            }

            int formBtnX = leftPos + 5;
            int formBtnY = topPos + 165;
            if (isMouseOver(mouseX, mouseY, formBtnX, formBtnY, 70, 16)) {
                boolean isFollowingCaptain = entity.getCaptain() != null && entity.getCaptain() != entity;
                if (!isFollowingCaptain) {
                    playClickSound();
                    this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
                }
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (showAIPanel) return true;
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (showAIPanel) return true;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private boolean isMouseOver(double mouseX, double mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    private void drawPanelButton(GuiGraphics graphics, int mouseX, int mouseY, int x, int y, int w, int h, String text, boolean enabled) {
        boolean hovered = enabled && isMouseOver(mouseX, mouseY, x, y, w, h);
        int bgColor;
        int textColor;

        if (!enabled) {
            bgColor = 0xFF555555;
            textColor = 0xFFAAAAAA;
        } else if (hovered) {
            bgColor = 0xFFAAAAAA;
            textColor = 0xFFFFFF;
        } else {
            bgColor = 0xFF888888;
            textColor = 0xFFFFFF;
        }

        graphics.fill(x, y, x + w, y + h, bgColor);
        graphics.renderOutline(x, y, w, h, 0xFF000000);
        graphics.drawCenteredString(this.font, text, x + w / 2, y + 4, textColor);
    }

    private void playClickSound() {
        Minecraft.getInstance().getSoundManager().play(
                net.minecraft.client.resources.sounds.SimpleSoundInstance.forUI(
                        net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    private String formatExp(long exp) {
        if (exp < 0) return "0";
        if (exp < 1000) return String.valueOf(exp);
        else if (exp < 10000) {
            double val = exp / 1000.0;
            String s = String.format("%.1f", val);
            if (s.endsWith(".0")) s = s.substring(0, s.length() - 2);
            return s + "k";
        } else {
            double val = exp / 10000.0;
            String s = String.format("%.1f", val);
            if (s.endsWith(".0")) s = s.substring(0, s.length() - 2);
            return s + "w";
        }
    }
}