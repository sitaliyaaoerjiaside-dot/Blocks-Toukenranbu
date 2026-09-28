package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.entity.touken.ToukenEntityData;
import com.Equatorial.toukenranbu.touken.FormationType;
import com.lowdragmc.lowdraglib2.gui.sync.bindings.impl.SupplierDataSource;
import com.lowdragmc.lowdraglib2.gui.texture.ColorRectTexture;
import com.lowdragmc.lowdraglib2.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib2.gui.texture.SpriteTexture;
import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import com.lowdragmc.lowdraglib2.gui.ui.UI;
import com.lowdragmc.lowdraglib2.gui.ui.UIElement;
import com.lowdragmc.lowdraglib2.gui.ui.data.ScrollerMode;
import com.lowdragmc.lowdraglib2.gui.ui.data.Tooltips;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Button;
import com.lowdragmc.lowdraglib2.gui.ui.elements.ItemSlot;
import com.lowdragmc.lowdraglib2.gui.ui.elements.Label;
import com.lowdragmc.lowdraglib2.gui.ui.elements.ProgressBar;
import com.lowdragmc.lowdraglib2.gui.ui.elements.ScrollerView;
import com.lowdragmc.lowdraglib2.gui.ui.elements.TabView;
import com.lowdragmc.lowdraglib2.gui.ui.rendering.GUIContext;
import com.lowdragmc.lowdraglib2.gui.ui.styletemplate.Sprites;
import com.lowdragmc.lowdraglib2.utils.XmlUtils;
import com.mojang.blaze3d.platform.Lighting;
import dev.vfyjxf.taffy.style.TaffyPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.joml.Quaternionf;

import java.util.List;
import java.util.function.Supplier;

public class ToukenEntityUI2 {

    public static final ToukenEntityUI2 INSTANCE = new ToukenEntityUI2();

    private static final ResourceLocation XML_PATH =
            ResourceLocation.parse("toukenranbu_mod:ldlib2/ui/touken_ui.xml");

    private static final FormationType[] FORMATION_CYCLE = {
            FormationType.NONE,
            FormationType.FISH_SCALE,
            FormationType.CRANE_WING,
            FormationType.GOOSE_LINE,
            FormationType.SQUARE
    };

    public ModularUI createUI(Player player, ToukenDanshiEntity entity) {
        var xml = XmlUtils.loadXml(XML_PATH);
        if (xml == null) {
            return ModularUI.of(UI.empty(), player);
        }
        var ui = UI.of(xml);

        bindData(ui, entity, player);

        return ModularUI.of(ui, player);
    }

    private void bindData(UI ui, ToukenDanshiEntity entity, Player player) {
        // 物品页滚动条只允许纵向
        ui.selectId("item_scroll", ScrollerView.class).findFirst().ifPresent(sv ->
                sv.scrollerStyle(style -> style.mode(ScrollerMode.VERTICAL)));
        // 滚动条皮肤
        ui.selectId("item_scroll", ScrollerView.class).findFirst().ifPresent(this::applyScrollerSkin);
        // 清掉 scroller 的 viewPort 默认背景
        ui.selectId("item_scroll", ScrollerView.class).findFirst().ifPresent(sv ->
                sv.viewPort.style(s -> s.backgroundTexture(IGuiTexture.EMPTY)));

        // ===== 状态页 =====
        bindLiveLabel(ui, "name_label", entity::getName);
        bindLiveLabel(ui, "stat_impact",      () -> Component.translatable("gui.toukenranbu.stat.impact",      entity.toukenData.getEffectiveImpact()));
        bindLiveLabel(ui, "stat_mobility",    () -> Component.translatable("gui.toukenranbu.stat.mobility",    entity.toukenData.getEffectiveMobility()));
        bindLiveLabel(ui, "stat_killing",     () -> Component.translatable("gui.toukenranbu.stat.killing",     entity.toukenData.getEffectiveKilling()));
        bindLiveLabel(ui, "stat_scouting",    () -> Component.translatable("gui.toukenranbu.stat.scouting",    entity.toukenData.getEffectiveScouting()));
        bindLiveLabel(ui, "stat_concealment", () -> Component.translatable("gui.toukenranbu.stat.concealment", entity.toukenData.getEffectiveConcealment()));
        bindLiveLabel(ui, "stat_troops",      () -> Component.translatable("gui.toukenranbu.stat.troops",      entity.toukenData.getEffectiveTroops()));

        ui.selectId("hp_text", Label.class).findFirst().ifPresent(l ->
                l.setText(Component.translatable("gui.toukenranbu.label.health")));
        ui.selectId("fatigue_text", Label.class).findFirst().ifPresent(l ->
                l.setText(Component.translatable("gui.toukenranbu.stat.fatigue")));

        ui.selectId("hp_bar", ProgressBar.class).findFirst().ifPresent(bar ->
                bar.label.setVisible(false));
        ui.selectId("fatigue_bar", ProgressBar.class).findFirst().ifPresent(bar ->
                bar.label.setVisible(false));

        bindLiveLabel(ui, "hp_value", () -> Component.literal(
                (int) entity.getHealth() + "/" + (int) entity.getMaxHealth()));
        bindLiveLabel(ui, "fatigue_value", () -> {
            Component status = Component.translatable(entity.toukenData.getFatigueStatusKey());
            return Component.literal((int) entity.toukenData.fatigue + " ").append(status);
        });

        ui.selectId("pickup_toggle", Button.class).findFirst().ifPresent(btn ->
                btn.setOnServerClick(e ->
                        entity.setPickupWhenFollowing(!entity.isPickupWhenFollowing())));

        ui.selectId("effects_text", Label.class).findFirst().ifPresent(l ->
                l.setText(Component.translatable("gui.toukenranbu.label.effects")));

        UIElement effectsContainer = ui.selectId("effects_container", UIElement.class).findFirst().orElse(null);

        // ===== tick 刷新：挂在 TabView 上（TabView 一直显示，不会失效） =====

        // 设置 Tab 文字（走翻译键）
        ui.selectId("main_tabs", TabView.class).findFirst().ifPresent(tv -> {
            var tabs = tv.getTabContents().keySet().stream().toList();
            if (tabs.size() > 0) tabs.get(0).setText(Component.translatable("gui.toukenranbu.tab.stats"));
            if (tabs.size() > 1) tabs.get(1).setText(Component.translatable("gui.toukenranbu.tab.items"));
            if (tabs.size() > 2) tabs.get(2).setText(Component.translatable("gui.toukenranbu.tab.growth"));
            if (tabs.size() > 3) tabs.get(3).setText(Component.translatable("gui.toukenranbu.tab.formation"));
        });
        // Tab 文字白色
        ui.selectId("main_tabs", TabView.class).findFirst().ifPresent(tv -> {
            for (var tab : tv.getTabContents().keySet()) {
                tab.text.textStyle(s -> s.textColor(0xFFFFFFFF));
            }
        });

        // ===== 皮肤 =====
        ui.selectId("main_tabs", TabView.class).findFirst().ifPresent(tv -> {
            // 清掉系统默认 content 背景
            tv.tabContentContainer.style(s -> s.backgroundTexture(IGuiTexture.EMPTY));
            // 顶部标签栏背景
            tv.tabHeaderContainer.style(s -> s.backgroundTexture(tex("tab_header_bg")));
            // Tab 三态
            for (var tab : tv.getTabContents().keySet()) {
                tab.tabStyle(style -> style
                        .baseTexture(tex("tab"))
                        .hoverTexture(tex("tab_hover"))
                        .selectedTexture(tex("tab_selected")));
            }
            // 4 页背景（必须先撑满尺寸，否则绝对定位的子元素会让 content 尺寸为 0，背景画不出来）
            var tabs = tv.getTabContents().keySet().stream().toList();
            if (tabs.size() > 0) {
                var c = tv.getTabContents().get(tabs.get(0));
                c.layout(l -> l.widthPercent(100).heightPercent(100));
                c.style(s -> s.backgroundTexture(tex("stats_bg")));
            }
            if (tabs.size() > 1) {
                var c = tv.getTabContents().get(tabs.get(1));
                c.layout(l -> l.widthPercent(100).heightPercent(100));
                c.style(s -> s.backgroundTexture(tex("items_bg")));
            }
            if (tabs.size() > 2) {
                var c = tv.getTabContents().get(tabs.get(2));
                c.layout(l -> l.widthPercent(100).heightPercent(100));
                c.style(s -> s.backgroundTexture(tex("growth_bg")));
            }
            if (tabs.size() > 3) {
                var c = tv.getTabContents().get(tabs.get(3));
                c.layout(l -> l.widthPercent(100).heightPercent(100));
                c.style(s -> s.backgroundTexture(tex("formation_bg")));
            }
            // 面板底
            if (tv.getParent() != null) {
                tv.getParent().style(s -> s.backgroundTexture(tex("panel")));
            }
        });

        // 实体 3D 预览：插到状态页里
        ui.selectId("main_tabs", TabView.class).findFirst().ifPresent(tv -> {
            var tabs = tv.getTabContents().keySet().stream().toList();
            if (tabs.isEmpty()) return;
            UIElement statsContent = tv.getTabContents().get(tabs.get(0));
            if (statsContent == null) return;

            UIElement entityDisplay = new UIElement() {
                @Override
                public void drawBackgroundAdditional(GUIContext ctx) {
                    super.drawBackgroundAdditional(ctx);
                    if (!entity.isAlive()) return;

                    int cx = (int)(this.getPositionX() + this.getSizeWidth() / 2);
                    int cy = (int)(this.getPositionY() + this.getSizeHeight() - 4);
                    int scale = 25;

                    InventoryScreen.renderEntityInInventoryFollowsMouse(
                            ctx.graphics, cx, cy, scale, (float)(cx - ctx.localMouseX),
                            (float)(cy - 50 - ctx.localMouseY), entity);
                }
            };
            entityDisplay.setId("entity_scene");
            entityDisplay.layout(l -> l.width(60).height(70)
                    .positionType(TaffyPosition.ABSOLUTE).left(0).top(0));
            statsContent.addChild(entityDisplay);
        });

        // 物品页的实体预览
        ui.selectId("main_tabs", TabView.class).findFirst().ifPresent(tv -> {
            var tabs = tv.getTabContents().keySet().stream().toList();
            if (tabs.size() < 2) return;
            UIElement itemsContent = tv.getTabContents().get(tabs.get(1));
            if (itemsContent == null) return;

            ScrollerView scroller = itemsContent.selectId("item_scroll", ScrollerView.class)
                    .findFirst().orElse(null);
            if (scroller == null) return;

            UIElement entityDisplayItems = new UIElement() {
                @Override
                public void drawBackgroundAdditional(GUIContext ctx) {
                    super.drawBackgroundAdditional(ctx);
                    if (!entity.isAlive()) return;

                    int cx = (int)(this.getPositionX() + this.getSizeWidth() / 2);
                    int cy = (int)(this.getPositionY() + this.getSizeHeight() - 4);

                    float angleX = (float) Math.atan((cx - ctx.localMouseX) / 40.0f);
                    float angleY = (float) Math.atan((cy - 50 - ctx.localMouseY) / 40.0f);

                    Quaternionf qz = new Quaternionf().rotateZ((float) Math.PI);
                    Quaternionf qx = new Quaternionf().rotateX(angleY * 20.0f * ((float) Math.PI / 180f));
                    qz.mul(qx);

                    EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
                    dispatcher.setRenderShadow(false);
                    Lighting.setupForEntityInInventory();

                    com.mojang.blaze3d.systems.RenderSystem.enableDepthTest();
                    com.mojang.blaze3d.systems.RenderSystem.depthMask(true);

                    var pose = ctx.graphics.pose();
                    pose.pushPose();
                    pose.translate(cx, cy, 50);
                    pose.scale(25, 25, -25);
                    pose.mulPose(qz);

                    dispatcher.render(entity, 0, 0, 0, 0, 1,
                            pose, ctx.graphics.bufferSource(), 0xF000F0);

                    ctx.graphics.flush();
                    pose.popPose();

                    dispatcher.setRenderShadow(true);
                    Lighting.setupFor3DItems();
                }
            };
            entityDisplayItems.setId("entity_scene_items");
            entityDisplayItems.layout(l -> l.width(60).height(70)
                    .positionType(TaffyPosition.ABSOLUTE).left(4).top(12));
            itemsContent.addChild(entityDisplayItems);

            // 每 tick 根据 scroller 值手动同步模型位置
            itemsContent.addEventListener("tick", e -> {
                float norm = scroller.verticalScroller.getValue();
                float maxScroll = Math.max(0f,
                        scroller.getContainerHeight() - scroller.viewPort.getContentHeight());
                float offsetY = -norm * maxScroll;
                entityDisplayItems.layout(l -> l.top(12 + offsetY));
            });
        });

        ui.selectId("main_tabs", TabView.class).findFirst().ifPresent(tv ->
                tv.addEventListener("tick", e -> {
                    if (entity.tickCount % 5 != 0) return;

                    ui.selectId("pickup_toggle", Button.class).findFirst().ifPresent(btn ->
                            updatePickupButton(btn, entity.isPickupWhenFollowing()));

                    ui.selectId("hp_bar", ProgressBar.class).findFirst().ifPresent(bar -> {
                        float ratio = entity.getHealth() / entity.getMaxHealth();
                        bar.setProgress(ratio);
                        int hpColor;
                        if (ratio >= 0.75f)      hpColor = 0xFF55FF55;
                        else if (ratio >= 0.5f)  hpColor = 0xFFAAFF55;
                        else if (ratio >= 0.25f) hpColor = 0xFFFFFF55;
                        else                     hpColor = 0xFFFF5555;
                        setBarColor(bar, hpColor);
                    });

                    ui.selectId("fatigue_bar", ProgressBar.class).findFirst().ifPresent(bar -> {
                        bar.setProgress(entity.toukenData.getFatiguePercent());
                        setBarColor(bar, entity.toukenData.getFatigueStatusColor());
                    });
                    ui.selectId("formation_cycle", Button.class).findFirst().ifPresent(btn -> {
                        var cap = entity.getCaptain();
                        if (cap != null && cap != entity) {
                            btn.setText(Component.translatable("gui.toukenranbu.formation.follow_captain"));
                            btn.setActive(false);
                        } else {
                            btn.setText(Component.translatable("gui.toukenranbu.formation."
                                    + entity.getFormationType().name().toLowerCase()));
                            btn.setActive(true);
                        }
                    });
                    ui.selectId("exp_bar", ProgressBar.class).findFirst().ifPresent(bar -> {
                        bar.setProgress(entity.toukenData.getLevelProgress());
                        setBarColor(bar, 0xFF55AAFF);
                    });

                    if (effectsContainer != null) {
                        refreshEffectIcons(effectsContainer, entity);
                    }
                }));

        // ===== 物品页 =====
        ui.selectId("label_armor",      Label.class).findFirst().ifPresent(l -> l.setText(Component.translatable("gui.toukenranbu.label.armor")));
        ui.selectId("label_knife",      Label.class).findFirst().ifPresent(l -> l.setText(Component.translatable("gui.toukenranbu.label.knife")));
        ui.selectId("label_mount",      Label.class).findFirst().ifPresent(l -> l.setText(Component.translatable("gui.toukenranbu.label.mount")));
        ui.selectId("label_blade",      Label.class).findFirst().ifPresent(l -> l.setText(Component.translatable("gui.toukenranbu.label.blade")));
        ui.selectId("label_touken_inv", Label.class).findFirst().ifPresent(l -> l.setText(Component.translatable("gui.toukenranbu.label.inventory")));
        ui.selectId("label_player_inv", Label.class).findFirst().ifPresent(l -> l.setText(Component.translatable("gui.toukenranbu.label.player_inventory")));

        // ===== 阵型页 =====
        ui.selectId("formation_title", Label.class).findFirst().ifPresent(l ->
                l.setText(Component.translatable("gui.toukenranbu.formation.title")));

        bindLiveLabel(ui, "formation_name", () ->
                Component.translatable("gui.toukenranbu.formation."
                        + entity.getEffectiveFormationType().name().toLowerCase()));

        bindLiveLabel(ui, "formation_level", () ->
                Component.translatable("gui.toukenranbu.formation.level",
                        entity.getEffectiveFormationLevel()));

        bindLiveLabel(ui, "formation_count", () ->
                Component.translatable("gui.toukenranbu.formation.count",
                        entity.getEffectiveFormationCount()));

        bindLiveLabel(ui, "formation_atk", () ->
                Component.translatable("gui.toukenranbu.formation.atk",
                        String.format("%.2f", entity.getFormationAttackMult())));

        bindLiveLabel(ui, "formation_def", () ->
                Component.translatable("gui.toukenranbu.formation.def",
                        String.format("%.2f", entity.getFormationDefenseMult())));

        bindLiveLabel(ui, "formation_spd", () ->
                Component.translatable("gui.toukenranbu.formation.spd",
                        String.format("%.2f", entity.getFormationSpeedMult())));

        bindLiveLabel(ui, "formation_rng", () ->
                Component.translatable("gui.toukenranbu.formation.rng",
                        String.format("%.2f", entity.getFormationRangeMult())));

        // 阵型循环切换按钮
        ui.selectId("formation_cycle", Button.class).findFirst().ifPresent(btn ->
                btn.setOnServerClick(e -> {
                    var cap = entity.getCaptain();
                    if (cap != null && cap != entity) return;  // 有队长，自己不能改
                    entity.setFormationType(nextFormation(entity.getFormationType()));
                }));

        bindItemSlots(ui, entity, player);

        // ===== 成长页 =====
        bindLiveLabel(ui, "level_label", () ->
                Component.translatable("gui.toukenranbu.label.level_value", entity.toukenData.level));

        ui.selectId("exp_text", Label.class).findFirst().ifPresent(l ->
                l.setText(Component.translatable("gui.toukenranbu.label.experience")));

        ui.selectId("exp_bar", ProgressBar.class).findFirst().ifPresent(bar -> {
            bar.label.setVisible(false);
            bar.setProgress(entity.toukenData.getLevelProgress());
        });

        bindLiveLabel(ui, "exp_value", () -> {
            long cur = entity.toukenData.getCurrentLevelExp();
            long next = entity.toukenData.getNextLevelExp();
            if (entity.toukenData.level >= ToukenEntityData.MAX_LEVEL) {
                return Component.literal(entity.toukenData.experience + " / 9999999");
            }
            return Component.literal((entity.toukenData.experience - cur) + " / " + (next - cur));
        });

        bindLiveLabel(ui, "level_bonus_title", () -> Component.translatable("gui.toukenranbu.label.level_bonus"));

        bindLiveLabel(ui, "lb_impact",      () -> Component.translatable("gui.toukenranbu.stat.impact",      "+" + entity.toukenData.levelImpact));
        bindLiveLabel(ui, "lb_mobility",    () -> Component.translatable("gui.toukenranbu.stat.mobility",    "+" + entity.toukenData.levelMobility));
        bindLiveLabel(ui, "lb_killing",     () -> Component.translatable("gui.toukenranbu.stat.killing",     "+" + entity.toukenData.levelKilling));
        bindLiveLabel(ui, "lb_scouting",    () -> Component.translatable("gui.toukenranbu.stat.scouting",    "+" + entity.toukenData.levelScouting));
        bindLiveLabel(ui, "lb_concealment", () -> Component.translatable("gui.toukenranbu.stat.concealment", "+" + entity.toukenData.levelConcealment));
        bindLiveLabel(ui, "lb_troops",      () -> Component.translatable("gui.toukenranbu.stat.troops",      "+" + entity.toukenData.levelTroops));

        // ===== AI 弹窗 =====
        ui.selectId("ai_title", Label.class).findFirst().ifPresent(l -> {
            l.setText(Component.translatable("gui.toukenranbu.ai.title"));
            l.textStyle(style -> style
                    .textColor(0xFFFFFFFF)
                    .fontSize(10F)
                    .textAlignHorizontal(com.lowdragmc.lowdraglib2.gui.ui.data.Horizontal.CENTER)
                    .textAlignVertical(com.lowdragmc.lowdraglib2.gui.ui.data.Vertical.CENTER));
        });

        ui.selectId("ai_mask", UIElement.class).findFirst().ifPresent(mask ->
                mask.style(style -> style.backgroundTexture(new ColorRectTexture(0xA0000000))));

        ui.selectId("ai_dialog", UIElement.class).findFirst().ifPresent(dialog ->
                dialog.style(style -> style.backgroundTexture(tex("ai_dialog_bg"))));

        List<UIElement> aiPopup = ui.select(".ai_popup").toList();
        aiPopup.forEach(el -> el.setVisible(false));
        final boolean[] aiPopupVisible = {false};

        Runnable closeAiPopup = () -> {
            aiPopupVisible[0] = false;
            aiPopup.forEach(el -> el.setVisible(false));
        };

        bindAiPage(ui, entity, closeAiPopup);

        // AI 按钮
        ui.selectId("tab_ai", Button.class).findFirst().ifPresent(btn ->
                btn.setOnClick(e -> {
                    aiPopupVisible[0] = !aiPopupVisible[0];
                    aiPopup.forEach(el -> el.setVisible(aiPopupVisible[0]));
                    if (aiPopupVisible[0]) {
                        refreshAiButtons(ui, entity);
                    }
                }));

        // AI 弹窗 mask 点击关闭
        ui.selectId("ai_mask", UIElement.class).findFirst().ifPresent(mask ->
                mask.addEventListener("mouseDown", e -> closeAiPopup.run()));

        // AI 按钮文字刷新
        ui.selectId("tab_ai", Button.class).findFirst().ifPresent(b ->
                b.addEventListener("tick", e -> {
                    if (aiPopupVisible[0] && entity.tickCount % 5 == 0) {
                        refreshAiButtons(ui, entity);
                    }
                }));

        // 切 Tab 时关闭 AI 弹窗
        ui.selectId("main_tabs", TabView.class).findFirst().ifPresent(tv ->
                tv.setOnTabSelected(tab -> closeAiPopup.run()));
        // 所有按钮套皮肤
        ui.selectId("tab_ai", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("pickup_toggle", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("formation_cycle", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("btn_sit", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("btn_follow", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("btn_farm", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("btn_mine", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("btn_patrol", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("btn_spar", Button.class).findFirst().ifPresent(this::applyButtonSkin);
        ui.selectId("btn_cave_clear", Button.class).findFirst().ifPresent(this::applyButtonSkin);
    }

    private void refreshEffectIcons(UIElement container, ToukenDanshiEntity entity) {
        container.clearAllChildren();
        int x = 0;
        for (var instance : entity.getActiveEffects()) {
            final var effect = instance.getEffect();

            List<Component> tips = new java.util.ArrayList<>();
            Component name = effect.getDisplayName().copy();
            if (instance.getAmplifier() >= 1 && instance.getAmplifier() <= 9) {
                name = name.copy().append(" ")
                        .append(Component.translatable("enchantment.level." + (instance.getAmplifier() + 1)));
            }
            tips.add(name);
            if (!instance.isInfiniteDuration()) {
                tips.add(net.minecraft.world.effect.MobEffectUtil.formatDuration(instance, 1.0F));
            }

            final int ix = x;
            UIElement iconEl = new UIElement() {
                @Override
                public void drawBackgroundAdditional(com.lowdragmc.lowdraglib2.gui.ui.rendering.GUIContext ctx) {
                    super.drawBackgroundAdditional(ctx);
                    var sprite = net.minecraft.client.Minecraft.getInstance()
                            .getMobEffectTextures().get(effect);
                    if (sprite == null) return;
                    ctx.pose.pushPose();
                    ctx.pose.translate(this.getPositionX(), this.getPositionY(), 0);
                    ctx.graphics.blit(0, 0, 0, 18, 18, sprite);
                    ctx.pose.popPose();
                }
            };
            iconEl.layout(l -> l.width(18).height(18)
                    .positionType(dev.vfyjxf.taffy.style.TaffyPosition.ABSOLUTE).left(ix).top(0));
            iconEl.style(s -> s.tooltips(Tooltips.of(tips.toArray(new Component[0]))));
            container.addChild(iconEl);

            x += 20;
        }
    }

    // ============ AI 弹窗 ============

    private void bindAiPage(UI ui, ToukenDanshiEntity entity, Runnable closeAiPopup) {
        ui.selectId("btn_sit", Button.class).findFirst().ifPresent(btn -> {
            btn.setOnClick(e -> closeAiPopup.run());
            btn.setOnServerClick(e -> entity.setOrderedToSit(!entity.isOrderedToSit()));
        });

        ui.selectId("btn_follow", Button.class).findFirst().ifPresent(btn -> {
            btn.setOnClick(e -> closeAiPopup.run());
            btn.setOnServerClick(e -> entity.setFollowing(!entity.isFollowing()));
        });

        ui.selectId("btn_farm", Button.class).findFirst().ifPresent(btn -> {
            btn.setOnClick(e -> closeAiPopup.run());
            btn.setOnServerClick(e -> {
                boolean farming = entity.isFarming();
                // 不在畑当番中，且没种子 → 不响应
                if (!farming && !entity.hasSeeds()) return;
                entity.setFarming(!farming);
            });
        });

        ui.selectId("btn_mine", Button.class).findFirst().ifPresent(btn -> {
            btn.setOnClick(e -> closeAiPopup.run());
            btn.setOnServerClick(e -> {
                boolean mining = entity.isMining();
                // 不在挖矿中，且没镐子 → 不响应
                if (!mining && !hasPickaxe(entity)) return;
                entity.setMining(!mining);
            });
        });

        ui.selectId("btn_patrol", Button.class).findFirst().ifPresent(btn -> {
            btn.setOnClick(e -> closeAiPopup.run());
            btn.setOnServerClick(e -> {
                if (entity.isPatrolling()) entity.setPatrolling(false);
                else entity.startPatrol();
            });
        });

        ui.selectId("btn_spar", Button.class).findFirst().ifPresent(btn -> {
            btn.setOnClick(e -> closeAiPopup.run());
            btn.setOnServerClick(e -> {
                if (entity.isSparring()) {
                    entity.setSparring(false);
                } else {
                    var partner = entity.findSparringPartner();
                    if (partner == null) return;   // 没伙伴 → 不响应
                    entity.startSparring(partner);
                }
            });
        });

        ui.selectId("btn_cave_clear", Button.class).findFirst().ifPresent(btn -> {
            btn.setOnClick(e -> closeAiPopup.run());
            btn.setOnServerClick(e -> {
                boolean clearing = entity.isCaveClearing();
                // 不在清缴中，且没火把 → 不响应
                if (!clearing && !entity.hasTorches()) return;
                entity.setCaveClearing(!clearing);
            });
        });
    }
    private void refreshAiButtons(UI ui, ToukenDanshiEntity entity) {
        ui.selectId("btn_sit", Button.class).findFirst().ifPresent(btn ->
                btn.setText(Component.translatable(entity.isOrderedToSit()
                        ? "gui.toukenranbu.button.stand"
                        : "gui.toukenranbu.button.sit")));

        ui.selectId("btn_follow", Button.class).findFirst().ifPresent(btn ->
                btn.setText(Component.translatable(entity.isFollowing()
                        ? "gui.toukenranbu.button.unfollow"
                        : "gui.toukenranbu.button.follow")));

        ui.selectId("btn_farm", Button.class).findFirst().ifPresent(btn -> {
            boolean farming = entity.isFarming();
            boolean hasSeeds = entity.hasSeeds();
            boolean escaping = entity.isFarmingEscaping();
            if (farming) {
                btn.setText(Component.translatable(escaping
                        ? "gui.toukenranbu.button.farm_escaping"
                        : "gui.toukenranbu.button.stop_farm"));
            } else if (!hasSeeds) {
                btn.setText(Component.translatable("gui.toukenranbu.button.farm_no_seeds"));
            } else {
                btn.setText(Component.translatable("gui.toukenranbu.button.farm"));
            }
            btn.setActive(farming || hasSeeds);
            btn.style(style -> style.tooltips(farming || hasSeeds
                    ? Tooltips.empty()
                    : Tooltips.of(Component.translatable("gui.toukenranbu.button.farm_no_seeds"))));
        });

        ui.selectId("btn_mine", Button.class).findFirst().ifPresent(btn -> {
            boolean mining = entity.isMining();
            boolean hasPick = hasPickaxe(entity);
            if (mining) {
                btn.setText(Component.translatable("gui.toukenranbu.button.stop_mine"));
            } else if (!hasPick) {
                btn.setText(Component.translatable("gui.toukenranbu.button.mine_no_pickaxe"));
            } else {
                btn.setText(Component.translatable("gui.toukenranbu.button.mine"));
            }
            btn.setActive(mining || hasPick);
            btn.style(style -> style.tooltips(mining || hasPick
                    ? Tooltips.empty()
                    : Tooltips.of(Component.translatable("gui.toukenranbu.button.mine_no_pickaxe"))));
        });

        ui.selectId("btn_patrol", Button.class).findFirst().ifPresent(btn ->
                btn.setText(Component.translatable(entity.isPatrolling()
                        ? "gui.toukenranbu.button.patrolling"
                        : "gui.toukenranbu.button.patrol")));

        ui.selectId("btn_spar", Button.class).findFirst().ifPresent(btn -> {
            boolean sparring = entity.isSparring();
            boolean hasPartner = entity.findSparringPartner() != null;
            btn.setText(Component.translatable(sparring
                    ? "gui.toukenranbu.button.sparring"
                    : "gui.toukenranbu.button.sparrow"));
            btn.setActive(sparring || hasPartner);
            btn.style(style -> style.tooltips(sparring || hasPartner
                    ? Tooltips.empty()
                    : Tooltips.of(Component.translatable("gui.toukenranbu.message.sparrow_no_partner"))));
        });

        ui.selectId("btn_cave_clear", Button.class).findFirst().ifPresent(btn -> {
            boolean clearing = entity.isCaveClearing();
            boolean hasTorches = entity.hasTorches();
            if (clearing) {
                btn.setText(Component.translatable("gui.toukenranbu.button.cave_clearing"));
            } else if (!hasTorches) {
                btn.setText(Component.translatable("gui.toukenranbu.button.cave_clear_no_torches"));
            } else {
                btn.setText(Component.translatable("gui.toukenranbu.button.cave_clear"));
            }
            btn.setActive(clearing || hasTorches);
            btn.style(style -> style.tooltips(clearing || hasTorches
                    ? Tooltips.empty()
                    : Tooltips.of(Component.translatable("gui.toukenranbu.button.cave_clear_no_torches"))));
        });
    }

    private boolean hasPickaxe(ToukenDanshiEntity entity) {
        var inv = entity.getInventoryHandler();
        for (int i = 0; i < inv.getSlots(); i++) {
            if (inv.getStackInSlot(i).getItem() instanceof PickaxeItem) return true;
        }
        return false;
    }

    private void updatePickupButton(Button btn, boolean enabled) {
        btn.setText(Component.translatable(enabled
                ? "gui.toukenranbu.label.auto_pickup.on"
                : "gui.toukenranbu.label.auto_pickup.off"));
    }

    private void bindItemSlots(UI ui, ToukenDanshiEntity entity, Player player) {
        for (int i = 0; i < 4; i++) {
            final int idx = i;
            ui.selectId("armor_" + i, ItemSlot.class).findFirst().ifPresent(slot -> {
                slot.bind(entity.getArmorHandler(), idx);
                slot.slotStyle(style -> style.acceptQuickMove(true).quickMovePriority(100));
                slot.style(s -> s.backgroundTexture(tex("slot")));
            });
        }
        for (int i = 0; i < 3; i++) {
            final int idx = i;
            ui.selectId("knife_" + i, ItemSlot.class).findFirst().ifPresent(slot -> {
                slot.bind(entity.getKnifeHandler(), idx);
                slot.slotStyle(style -> style.acceptQuickMove(true).quickMovePriority(90));
                slot.style(s -> s.backgroundTexture(tex("slot")));
            });
        }
        ui.selectId("mount", ItemSlot.class).findFirst().ifPresent(slot -> {
            slot.bind(entity.getMountHandler(), 0);
            slot.slotStyle(style -> style.acceptQuickMove(true).quickMovePriority(80));
            slot.style(s -> s.backgroundTexture(tex("slot")));
        });

        ui.selectId("blade", ItemSlot.class).findFirst().ifPresent(slot -> {
            slot.bind(entity.getBladeHandler(), 0);
            slot.slotStyle(style -> style.acceptQuickMove(true).quickMovePriority(80));
            slot.style(s -> s.backgroundTexture(tex("slot")));
        });

        for (int i = 0; i < 25; i++) {
            final int idx = i;
            ui.selectId("inv_" + i, ItemSlot.class).findFirst().ifPresent(slot -> {
                slot.bind(entity.getInventoryHandler(), idx);
                slot.slotStyle(style -> style.acceptQuickMove(true).quickMovePriority(50));
                slot.style(s -> s.backgroundTexture(tex("slot")));
            });
        }

        var playerInv = new InvWrapper(player.getInventory());
        for (int i = 0; i < 27; i++) {
            final int idx = 9 + i;
            ui.selectId("p_main_" + i, ItemSlot.class).findFirst().ifPresent(slot -> {
                slot.bind(playerInv, idx);
                slot.slotStyle(style -> style.acceptQuickMove(true).quickMovePriority(10).isPlayerSlot(true));
                slot.style(s -> s.backgroundTexture(tex("slot")));
            });
        }
        for (int i = 0; i < 9; i++) {
            final int idx = i;
            ui.selectId("p_hotbar_" + i, ItemSlot.class).findFirst().ifPresent(slot -> {
                slot.bind(playerInv, idx);
                slot.slotStyle(style -> style.acceptQuickMove(true).quickMovePriority(10).isPlayerSlot(true));
                slot.style(s -> s.backgroundTexture(tex("slot")));
            });
        }
    }

    private void setBarColor(ProgressBar bar, int color) {
        bar.bar(b -> b.style(s -> s.backgroundTexture(
                Sprites.RECT_RD_T.copy().setColor(color)
        )));
    }

    private void bindLiveLabel(UI ui, String id, Supplier<Component> supplier) {
        ui.selectId(id, Label.class).findFirst().ifPresent(label -> {
            var provider = SupplierDataSource.of(supplier);
            provider.frequency(5);
            label.bindDataSource(provider);
        });
    }

    private FormationType nextFormation(FormationType current) {
        for (int i = 0; i < FORMATION_CYCLE.length; i++) {
            if (FORMATION_CYCLE[i] == current) {
                return FORMATION_CYCLE[(i + 1) % FORMATION_CYCLE.length];
            }
        }
        return FORMATION_CYCLE[0];
    }

    private SpriteTexture tex(String name) {
        return SpriteTexture.of("toukenranbu_mod:textures/gui/" + name + ".png");
    }

    private void applyButtonSkin(Button btn) {
        btn.buttonStyle(style -> style
                .baseTexture(tex("button"))
                .hoverTexture(tex("button_hover"))
                .pressedTexture(tex("button_pressed")));
    }
    private void applyScrollerSkin(ScrollerView sv) {
        var v = sv.verticalScroller;

        // 上下箭头
        v.headButton.buttonStyle(style -> style
                .baseTexture(tex("scroll_up"))
                .hoverTexture(tex("scroll_up"))
                .pressedTexture(tex("scroll_up")));
        v.tailButton.buttonStyle(style -> style
                .baseTexture(tex("scroll_down"))
                .hoverTexture(tex("scroll_down"))
                .pressedTexture(tex("scroll_down")));

        // 滑轨
        v.scrollContainer.style(s -> s.backgroundTexture(tex("scroll_track")));

        // 滑块
        v.scrollBar.buttonStyle(style -> style
                .baseTexture(tex("scroll_thumb"))
                .hoverTexture(tex("scroll_thumb_hover"))
                .pressedTexture(tex("scroll_thumb_pressed")));
    }
}