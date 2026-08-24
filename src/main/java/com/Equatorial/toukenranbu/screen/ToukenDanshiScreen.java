package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.network.ToukenDanshiActionPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.PickaxeItem;
import com.Equatorial.toukenranbu.network.FormationChangePacket;
import com.Equatorial.toukenranbu.touken.FormationType;

public class ToukenDanshiScreen extends AbstractContainerScreen<ToukenDanshiMenu> {
    private static final ResourceLocation STATUS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/gui/touken_danshi_status.png");
    private static final ResourceLocation ITEMS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "textures/gui/touken_danshi_items.png");

    private Button tabStatus;
    private Button tabItems;
    private int currentTab = 0;

    private Button sitButton;
    private Button followButton;
    private Button farmButton;
    private Button mineButton;
    private Button formationButton;
    private Button patrolButton;
    private Button sparrowButton;
    private Button caveClearButton;
    private ToggleSwitch pickupSwitch;

    public ToukenDanshiScreen(ToukenDanshiMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 256;
        this.imageHeight = 240;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = 10000;
        this.inventoryLabelY = 10000;

        int gx = this.leftPos;
        int gy = this.topPos;

        tabStatus = Button.builder(Component.translatable("gui.toukenranbu.tab.status"), b -> switchTab(0))
                .pos(gx + 10, gy + 220).size(60, 16).build();
        tabItems = Button.builder(Component.translatable("gui.toukenranbu.tab.items"), b -> switchTab(1))
                .pos(gx + 74, gy + 220).size(60, 16).build();
        this.addRenderableWidget(tabStatus);
        this.addRenderableWidget(tabItems);

        pickupSwitch = new ToggleSwitch(gx + 180, gy + 6,
                menu.getEntity() != null && menu.getEntity().isPickupWhenFollowing(),
                v -> {
                    if (menu.getEntity() != null) {
                        ToukenDanshiActionPacket.send(menu.getEntity().getId(), 7);
                    }
                });
        this.addRenderableWidget(pickupSwitch);

        // AI 按钮上移，跟底部标签页中间留 20px 空位给后续按键
        sitButton = Button.builder(Component.translatable("gui.toukenranbu.button.sit"), b -> sendAction(0))
                .pos(gx + 10, gy + 160).size(50, 18).build();
        followButton = Button.builder(Component.translatable("gui.toukenranbu.button.follow"), b -> sendAction(1))
                .pos(gx + 65, gy + 160).size(50, 18).build();
        farmButton = Button.builder(Component.translatable("gui.toukenranbu.button.farm"), b -> sendAction(2))
                .pos(gx + 120, gy + 160).size(50, 18).build();
        // 挖矿按钮（action = 3）
        mineButton = Button.builder(Component.translatable("gui.toukenranbu.button.mine"), b -> sendAction(3))
                .pos(gx + 175, gy + 160).size(50, 18).build();
        this.addRenderableWidget(mineButton);
        formationButton = Button.builder(Component.translatable("gui.toukenranbu.formation.none"), b -> {
            if (menu.getEntity() != null) {
                FormationType[] values = FormationType.values();
                FormationType current = menu.getEntity().getFormationType();
                int next = (current.ordinal() + 1) % values.length;
                FormationChangePacket.send(menu.getEntity().getId(), values[next]);
            }
        }).pos(gx + 190, gy + 5).size(50, 18).build();

        this.addRenderableWidget(formationButton);
        patrolButton = Button.builder(Component.translatable("gui.toukenranbu.button.patrol"), b -> {
            if (menu.getEntity() != null) {
                ToukenDanshiActionPacket.send(menu.getEntity().getId(), 4);
            }
        }).pos(gx + 10, gy + 182).size(50, 18).build();

        this.addRenderableWidget(patrolButton);
        sparrowButton = Button.builder(Component.translatable("gui.toukenranbu.button.sparrow"), b -> {
            if (menu.getEntity() != null) {
                ToukenDanshiActionPacket.send(menu.getEntity().getId(), 5);
            }
        }).pos(gx + 65, gy + 182).size(50, 18).build();

        // 矿洞清缴按钮（action = 6）
        caveClearButton = Button.builder(Component.translatable("gui.toukenranbu.button.cave_clear"), b -> {
            if (menu.getEntity() != null) {
                ToukenDanshiActionPacket.send(menu.getEntity().getId(), 6);
            }
        }).pos(gx + 120, gy + 182).size(50, 18).build();
        this.addRenderableWidget(caveClearButton);

        this.addRenderableWidget(sparrowButton);
        this.addRenderableWidget(sitButton);
        this.addRenderableWidget(followButton);
        this.addRenderableWidget(farmButton);

    }

    private void switchTab(int tab) {
        this.currentTab = tab;
        this.menu.currentTab = tab;
    }

    private void sendAction(int action) {
        if (menu.getEntity() != null) {
            ToukenDanshiActionPacket.send(menu.getEntity().getId(), action);
        }
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        if (currentTab == 0) {
            gui.blit(STATUS_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
            renderStatusBg(gui);
        } else {
            gui.blit(ITEMS_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        }
    }

    private void renderStatusBg(GuiGraphics gui) {
        var entity = menu.getEntity();
        if (entity == null) return;
        var data = entity.toukenData;
        int l = this.leftPos;
        int t = this.topPos;

        // HP 文字移到顶部灰色长条内
        gui.drawString(this.font,
                Component.translatable("gui.toukenranbu.hp", (int) entity.getHealth(), (int) entity.getMaxHealth()),
                l + 20, t + 14, 0xFFFFFF, false);

        // 血条背景（宽 216，贴合顶部灰色长条）
        // 血条背景往上移到 t+24，高度保持10px（t+24 ~ t+34）
        gui.fill(l + 20, t + 24, l + 236, t + 34, 0xFF333333);
        float hpPct = entity.getHealth() / entity.getMaxHealth();
        int hpColor = hpPct > 0.5f ? 0xFF00FF00 : (hpPct > 0.25f ? 0xFFFFAA00 : 0xFFFF0000);
        gui.fill(l + 20, t + 24, l + 20 + (int)(216 * hpPct), t + 34, hpColor);

        // 实体名称（血条下方、黑色预览框上方居中）
        Component name = entity.getName();
        int nameWidth = this.font.width(name);
        // 左侧黑色框中心约 x=80，文字居中：80 - 文字宽/2
        gui.drawString(this.font, name, l + 57 - nameWidth / 2, t + 40, 0xFFFFFF, false);

        // 属性面板整体往左移 15px，稍微下移 5px
        int ax = l + 125;
        int ay = t + 55;
        int statColor = data.getFatigueStatusColor();
        // 阵型状态（属性面板上方，避免和名字重叠）
        FormationType ft = entity.getFormationType();
        int fl = entity.toukenData.formationLevel;
        int fc = entity.toukenData.formationCount;
        int formColor = fl > 0 ? 0xFF00FFFF : 0xFF888888;
        Component formComp = fl > 0
                ? Component.translatable("gui.toukenranbu.formation.status_with_count", Component.translatable(ft.translationKey), fc)
                : Component.translatable(ft.translationKey);
        gui.drawString(this.font, formComp, ax, t + 44, formColor, false);
        gui.drawString(this.font, Component.translatable("gui.toukenranbu.stat.impact", data.getEffectiveImpact()), ax, ay, statColor, false);
        gui.drawString(this.font, Component.translatable("gui.toukenranbu.stat.mobility", data.getEffectiveMobility()), ax, ay + 12, statColor, false);
        gui.drawString(this.font, Component.translatable("gui.toukenranbu.stat.killing", data.getEffectiveKilling()), ax, ay + 24, statColor, false);
        gui.drawString(this.font, Component.translatable("gui.toukenranbu.stat.scouting", data.getEffectiveScouting()), ax, ay + 36, statColor, false);
        gui.drawString(this.font, Component.translatable("gui.toukenranbu.stat.concealment", data.getEffectiveConcealment()), ax, ay + 48, statColor, false);
        gui.drawString(this.font, Component.translatable("gui.toukenranbu.stat.troops", data.getEffectiveTroops()), ax, ay + 60, statColor, false);

        // 疲劳度条
        gui.drawString(this.font, Component.translatable("gui.toukenranbu.stat.fatigue"), ax, ay + 78, 0x404040, false);
        int fColor = data.fatigue >= 50 ? 0xFFFF69B4 : (data.fatigue >= 40 ? 0xFFFFFFFF : (data.fatigue >= 20 ? 0xFFFFFF00 : 0xFFFF0000));
        gui.fill(ax + 28, ay + 80, ax + 100, ay + 88, 0xFF555555);
        gui.fill(ax + 28, ay + 80, ax + 28 + (int)(72 * data.getFatiguePercent()), ay + 88, fColor);
        gui.drawString(this.font, String.valueOf(data.fatigue), ax + 30, ay + 78, 0xFFFFFF, false);

        // 状态文字移到疲劳度条下方，避免跟粉色条重叠看不清
        gui.drawString(this.font, Component.translatable(data.getFatigueStatusKey()), ax + 28, ay + 92, data.getFatigueStatusColor(), false);
        // 工作状态提示（属性面板右上角，疲劳度条上方）
        if (entity.isMining()) {
            gui.drawString(this.font, Component.translatable("gui.toukenranbu.status.mining"),
                    ax + 60, ay + 68, 0xFFFFAA00, false);
        }
        // ===== 工作状态结束 =====
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTick);

        var entity = menu.getEntity();
        if (entity != null) {
            // 坐下按钮
            this.sitButton.setMessage(Component.translatable(
                    entity.isOrderedToSit() ? "gui.toukenranbu.button.stand" : "gui.toukenranbu.button.sit"
            ).withStyle(entity.isOrderedToSit() ? ChatFormatting.GREEN : ChatFormatting.GRAY));

            // 跟随按钮
            this.followButton.setMessage(Component.translatable(
                    entity.isFollowing() ? "gui.toukenranbu.button.unfollow" : "gui.toukenranbu.button.follow"
            ).withStyle(entity.isFollowing() ? ChatFormatting.RED : ChatFormatting.GREEN));

            // 种田按钮（种子检查和逃番显示）
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

        // 挖矿按钮（镐子检查）
        boolean hasPick = false;
        for (int i = 0; i < entity.getInventoryHandler().getSlots(); i++) {
            if (entity.getInventoryHandler().getStackInSlot(i).getItem() instanceof PickaxeItem) {
                hasPick = true; break;
            }
        }
        String mineKey;
        ChatFormatting mineColor;

        if (entity.isMining()) {
            mineKey = "gui.toukenranbu.button.stop_mine";
            mineColor = ChatFormatting.RED;
        } else {
            mineKey = hasPick ? "gui.toukenranbu.button.mine" : "gui.toukenranbu.button.mine_no_pickaxe";
            mineColor = hasPick ? ChatFormatting.DARK_GREEN : ChatFormatting.GRAY;
        }
        this.mineButton.setMessage(Component.translatable(mineKey).withStyle(mineColor));
        this.mineButton.active = hasPick || entity.isMining();

        // 巡逻按钮文本
        boolean patrolling = entity.isPatrolling();
        ChatFormatting patrolColor = patrolling ? ChatFormatting.GREEN : ChatFormatting.GRAY;
        this.patrolButton.setMessage(Component.translatable(
                patrolling ? "gui.toukenranbu.button.patrolling" : "gui.toukenranbu.button.patrol").withStyle(patrolColor));

        // 手合按钮文本
        boolean sparring = entity.isSparring();
        ChatFormatting sparrowColor = sparring ? ChatFormatting.GREEN : ChatFormatting.GRAY;
        this.sparrowButton.setMessage(Component.translatable(
                sparring ? "gui.toukenranbu.button.sparring" : "gui.toukenranbu.button.sparrow"
        ).withStyle(sparrowColor));

        // 矿洞清缴按钮文本
        boolean clearing = entity.isCaveClearing();
        boolean hasTorches = entity.hasTorches();
        String caveKey;
        ChatFormatting caveColor;

        if (clearing) {
            caveKey = "gui.toukenranbu.button.cave_clearing";
            caveColor = ChatFormatting.RED;
        } else {
            caveKey = hasTorches ? "gui.toukenranbu.button.cave_clear" : "gui.toukenranbu.button.cave_clear_no_torches";
            caveColor = hasTorches ? ChatFormatting.DARK_GREEN : ChatFormatting.GRAY;
        }
        this.caveClearButton.setMessage(Component.translatable(caveKey).withStyle(caveColor));
        this.caveClearButton.active = hasTorches || clearing;

        // 阵型按钮文本
        if (entity != null) {
            FormationType ft = entity.getFormationType();
            ChatFormatting ftColor = ft == FormationType.NONE ? ChatFormatting.GRAY : ChatFormatting.AQUA;
            formationButton.setMessage(Component.translatable(ft.translationKey).withStyle(ftColor));
        }

        // 是否拾取物品滑块
        pickupSwitch.setValue(entity.isPickupWhenFollowing());

        //分割线，我分我分——————————

        tabStatus.active = currentTab != 0;
        tabItems.active = currentTab != 1;

        boolean statusVisible = currentTab == 0;
        sitButton.visible = statusVisible;
        followButton.visible = statusVisible;
        farmButton.visible = statusVisible;
        mineButton.visible = statusVisible;
        formationButton.visible = statusVisible;
        patrolButton.visible = statusVisible;
        sparrowButton.visible = statusVisible;
        caveClearButton.visible = statusVisible;
        pickupSwitch.visible = currentTab == 1;

        if (currentTab == 0 && entity != null) {
            renderEntity(gui, this.leftPos + 57, this.topPos + 130, 28, mouseX, mouseY);
        }

        renderTooltip(gui, mouseX, mouseY);
    }

    private void renderEntity(GuiGraphics gui, int x, int y, int scale, int mouseX, int mouseY) {
        if (this.menu.getEntity() != null) {
            float offsetX = (float) x - mouseX;
            float offsetY = (float) (y - 50) - mouseY;
            InventoryScreen.renderEntityInInventoryFollowsMouse(
                    gui, x, y, scale, offsetX, offsetY, this.menu.getEntity());
        }
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        if (currentTab == 1) {

            // 标题：刀剑男士的物品栏（正中间）
            Component title = Component.translatable("gui.toukenranbu.label.entity_inventory");
            int tw = this.font.width(title);
            gui.drawString(this.font, title, 128 - tw / 2, 8, 0x404040, false);

            // 护甲栏（左上竖排上方）
            gui.drawString(this.font, Component.translatable("gui.toukenranbu.label.armor"), 20, 41, 0x404040, false);
            // 刀装栏（中上横排上方）
            gui.drawString(this.font, Component.translatable("gui.toukenranbu.label.knife"), 80, 20, 0x404040, false);
            // 马匹栏（刀装下方第1格上方）
            gui.drawString(this.font, Component.translatable("gui.toukenranbu.label.mount"), 65, 53, 0x404040, false);
            // 宝物栏（刀装下方第2格上方）
            gui.drawString(this.font, Component.translatable("gui.toukenranbu.label.treasure"), 65, 83, 0x404040, false);
            //本体刀栏
            gui.drawString(this.font, Component.translatable("gui.toukenranbu.label.blade"), 101, 53, 0x404040, false);
            // 背包栏（右上 5x5 上方）
            gui.drawString(this.font, Component.translatable("gui.toukenranbu.label.inventory"), 150, 24, 0x404040, false);
            // 拾取物品滑块标签（放滑块右边，避免跟标题重叠）
            Component pickupLabel = Component.translatable("gui.toukenranbu.label.auto_pickup");
            int switchRelX = pickupSwitch.getX() - this.leftPos;
            gui.drawString(this.font, pickupLabel, switchRelX + pickupSwitch.getWidth() + 3, 8, 0x404040, false);
        }
    }
}