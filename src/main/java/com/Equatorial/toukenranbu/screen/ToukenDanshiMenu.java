package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.tag.ModItemTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ToukenDanshiMenu extends AbstractContainerMenu {

    private final ToukenDanshiEntity entity;
    public int currentTab = 0; // 0=状态页, 1=物品页

    public static final int SLOT_ARMOR_HEAD = 0;
    public static final int SLOT_ARMOR_CHEST = 1;
    public static final int SLOT_ARMOR_LEGS = 2;
    public static final int SLOT_ARMOR_FEET = 3;
    public static final int SLOT_KNIFE_1 = 4;
    public static final int SLOT_KNIFE_2 = 5;
    public static final int SLOT_KNIFE_3 = 6;
    public static final int SLOT_MOUNT = 7;
    public static final int SLOT_ENTITY_INV_START = 8;
    public static final int SLOT_ENTITY_INV_END = 32;
    public static final int SLOT_PLAYER_INV_START = 33;
    public static final int SLOT_PLAYER_INV_END = 59;
    public static final int SLOT_PLAYER_HOTBAR_START = 60;
    public static final int SLOT_PLAYER_HOTBAR_END = 68;
    public static final int SLOT_BLADE = 69;

    public ToukenDanshiMenu(MenuType<?> type, int containerId, Inventory playerInventory, ToukenDanshiEntity entity) {
        super(type, containerId);
        this.entity = entity;

        // ===== 左上：护甲栏 4格竖排 =====
        this.addSlot(new TabArmorSlot(entity.getArmorHandler(), 0, 21, 52, EquipmentSlot.HEAD, this));
        this.addSlot(new TabArmorSlot(entity.getArmorHandler(), 1, 21, 70, EquipmentSlot.CHEST, this));
        this.addSlot(new TabArmorSlot(entity.getArmorHandler(), 2, 21, 88, EquipmentSlot.LEGS, this));
        this.addSlot(new TabArmorSlot(entity.getArmorHandler(), 3, 21, 106, EquipmentSlot.FEET, this));

        // ===== 中上：刀装栏 3格横排 =====
        this.addSlot(new TabKnifeSlot(entity.getKnifeHandler(), 0, 65, 31, this));
        this.addSlot(new TabKnifeSlot(entity.getKnifeHandler(), 1, 83, 31, this));
        this.addSlot(new TabKnifeSlot(entity.getKnifeHandler(), 2, 101, 31, this));

        // ===== 刀装下方第1格：马匹栏 =====
        this.addSlot(new TabMountSlot(entity.getMountHandler(), 0, 65, 63, this));

        // ===== 刀装下方第2格：宝物栏（未实装，先注释）=====
        // this.addSlot(new TabTreasureSlot(entity.getTreasureHandler(), 0, 80, 86, this));

        // ===== 宝物栏旁边：本体刀栏 =====
        this.addSlot(new TabBladeSlot(entity.getBladeHandler(), 0, 101, 63, this));

        // ===== 右上：背包栏 25格 (5x5) =====
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.addSlot(new TabItemSlot(entity.getInventoryHandler(), j + i * 5,
                        149 + j * 18, 34 + i * 18, this));
            }
        }

        // ===== 底部：玩家背包 3x9 =====
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                final int idx = j + i * 9 + 9;
                this.addSlot(new Slot(playerInventory, idx, 48 + j * 18, 140 + i * 18) {
                    @Override public boolean isActive() { return ToukenDanshiMenu.this.currentTab == 1; }
                });
            }
        }

        // ===== 底部：快捷栏 =====
        for (int k = 0; k < 9; ++k) {
            final int idx = k;
            this.addSlot(new Slot(playerInventory, idx, 48 + k * 18, 198) {
                @Override public boolean isActive() { return ToukenDanshiMenu.this.currentTab == 1; }
            });
        }
    }

    public static ToukenDanshiMenu fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        int entityId = data.readInt();
        Entity entity = inv.player.level().getEntity(entityId);
        if (entity instanceof ToukenDanshiEntity touken) {
            return new ToukenDanshiMenu(com.Equatorial.toukenranbu.screen.ModMenuTypes.TOUKEN_DANSHI_MENU.get(), windowId, inv, touken);
        }
        throw new IllegalStateException("Entity not found or wrong type: " + entityId);
    }

    public ToukenDanshiEntity getEntity() {
        return entity;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.entity.isAlive() && this.entity.distanceTo(player) < 8.0D;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index <= SLOT_ENTITY_INV_END) {
                if (!this.moveItemStackTo(stackInSlot, SLOT_PLAYER_INV_START, SLOT_PLAYER_HOTBAR_END + 1, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                boolean moved = false;

                if (stackInSlot.getItem() instanceof ArmorItem armor) {
                    EquipmentSlot slotType = armor.getEquipmentSlot();
                    int target = switch (slotType) {
                        case HEAD -> SLOT_ARMOR_HEAD;
                        case CHEST -> SLOT_ARMOR_CHEST;
                        case LEGS -> SLOT_ARMOR_LEGS;
                        case FEET -> SLOT_ARMOR_FEET;
                        default -> -1;
                    };
                    if (target != -1) {
                        moved = this.moveItemStackTo(stackInSlot, target, target + 1, false);
                    }
                }

                if (!moved && isKnifeItem(stackInSlot)) {
                    moved = this.moveItemStackTo(stackInSlot, SLOT_KNIFE_1, SLOT_KNIFE_3 + 1, false);
                }

                if (!moved && ToukenDanshiEntity.isMountItem(stackInSlot)) {
                    moved = this.moveItemStackTo(stackInSlot, SLOT_MOUNT, SLOT_MOUNT + 1, false);
                }

                if (!moved && isBladeItem(stackInSlot)) {
                    moved = this.moveItemStackTo(stackInSlot, SLOT_BLADE, SLOT_BLADE + 1, false);
                }

                if (!moved) {
                    moved = this.moveItemStackTo(stackInSlot, SLOT_ENTITY_INV_START, SLOT_ENTITY_INV_END + 1, false);
                }

                if (!moved) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return itemstack;
    }

    public static boolean isKnifeItem(ItemStack stack) {
        return stack.is(ModItemTags.KNIFE_EQUIPMENT);
    }

    public static boolean isBladeItem(ItemStack stack) {
        return com.Equatorial.toukenranbu.item.ModItems.isBlade(stack.getItem());
    }

    // ========== 带标签页控制的槽位 ==========

    public static class TabArmorSlot extends SlotItemHandler {
        private final EquipmentSlot slotType;
        private final ToukenDanshiMenu menu;
        public TabArmorSlot(net.minecraftforge.items.IItemHandler h, int idx, int x, int y, EquipmentSlot type, ToukenDanshiMenu menu) {
            super(h, idx, x, y); this.slotType = type; this.menu = menu;
        }
        @Override public boolean mayPlace(ItemStack stack) {
            if (stack.getItem() instanceof ArmorItem armor) return armor.getEquipmentSlot() == this.slotType;
            return false;
        }
        @Override public int getMaxStackSize() { return 1; }
        @Override public boolean isActive() { return menu.currentTab == 1; }
    }

    public static class TabKnifeSlot extends SlotItemHandler {
        private final ToukenDanshiMenu menu;
        public TabKnifeSlot(net.minecraftforge.items.IItemHandler h, int idx, int x, int y, ToukenDanshiMenu menu) {
            super(h, idx, x, y); this.menu = menu;
        }
        @Override public boolean mayPlace(ItemStack stack) { return isKnifeItem(stack); }
        @Override public int getMaxStackSize() { return 1; }
        @Override public boolean isActive() { return menu.currentTab == 1; }
    }

    public static class TabMountSlot extends SlotItemHandler {
        private final ToukenDanshiMenu menu;
        public TabMountSlot(net.minecraftforge.items.IItemHandler h, int idx, int x, int y, ToukenDanshiMenu menu) {
            super(h, idx, x, y); this.menu = menu;
        }
        @Override public boolean mayPlace(ItemStack stack) { return ToukenDanshiEntity.isMountItem(stack); }
        @Override public int getMaxStackSize() { return 1; }
        @Override public boolean isActive() { return menu.currentTab == 1; }
    }

    public static class TabBladeSlot extends SlotItemHandler {
        private final ToukenDanshiMenu menu;
        public TabBladeSlot(net.minecraftforge.items.IItemHandler h, int idx, int x, int y, ToukenDanshiMenu menu) {
            super(h, idx, x, y); this.menu = menu;
        }
        @Override public boolean mayPlace(ItemStack stack) { return isBladeItem(stack); }
        @Override public int getMaxStackSize() { return 1; }
        @Override public boolean isActive() { return menu.currentTab == 1; }
    }

    public static class TabItemSlot extends SlotItemHandler {
        private final ToukenDanshiMenu menu;

        public TabItemSlot(net.minecraftforge.items.IItemHandler h, int idx, int x, int y, ToukenDanshiMenu menu) {
            super(h, idx, x, y);this.menu = menu;
        }
        @Override
        public boolean isActive() {
            return menu.currentTab == 1;
        }
        /*
// 宝物槽（未实装）
public static class TabTreasureSlot extends SlotItemHandler {
    private final ToukenDanshiMenu menu;
    public TabTreasureSlot(net.minecraftforge.items.IItemHandler h, int idx, int x, int y, ToukenDanshiMenu menu) {
        super(h, idx, x, y); this.menu = menu;
    }
    @Override public boolean mayPlace(ItemStack stack) {
        // TODO: 替换为 ModItemTags.TREASURE
        return false;
    }
    @Override public int getMaxStackSize() { return 1; }
    @Override public boolean isActive() { return menu.currentTab == 1; }
}
*/
    }
}