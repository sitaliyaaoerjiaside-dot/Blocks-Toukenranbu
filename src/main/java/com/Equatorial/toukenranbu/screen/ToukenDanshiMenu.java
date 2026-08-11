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

    public static final int SLOT_ARMOR_HEAD = 0;
    public static final int SLOT_ARMOR_CHEST = 1;
    public static final int SLOT_ARMOR_LEGS = 2;
    public static final int SLOT_ARMOR_FEET = 3;
    public static final int SLOT_KNIFE_1 = 4;
    public static final int SLOT_KNIFE_2 = 5;
    public static final int SLOT_KNIFE_3 = 6;
    public static final int SLOT_ENTITY_INV_START = 7;
    public static final int SLOT_ENTITY_INV_END = 31;
    public static final int SLOT_PLAYER_INV_START = 32;
    public static final int SLOT_PLAYER_INV_END = 58;
    public static final int SLOT_PLAYER_HOTBAR_START = 59;
    public static final int SLOT_PLAYER_HOTBAR_END = 67;

    public ToukenDanshiMenu(MenuType<?> type, int containerId, Inventory playerInventory, ToukenDanshiEntity entity) {
        super(type, containerId);
        this.entity = entity;

        this.addSlot(new ArmorSlot(entity.getArmorHandler(), 0, 101, 8, EquipmentSlot.HEAD));
        this.addSlot(new ArmorSlot(entity.getArmorHandler(), 1, 101, 26, EquipmentSlot.CHEST));
        this.addSlot(new ArmorSlot(entity.getArmorHandler(), 2, 101, 44, EquipmentSlot.LEGS));
        this.addSlot(new ArmorSlot(entity.getArmorHandler(), 3, 101, 62, EquipmentSlot.FEET));

        this.addSlot(new KnifeSlot(entity.getKnifeHandler(), 0, 8, 13));
        this.addSlot(new KnifeSlot(entity.getKnifeHandler(), 1, 26, 13));
        this.addSlot(new KnifeSlot(entity.getKnifeHandler(), 2, 44, 13));

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                this.addSlot(new SlotItemHandler(entity.getInventoryHandler(), j + i * 5,
                        8 + j * 18, 41 + i * 18));
            }
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9,
                        8 + j * 18, 140 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 198));
        }
    }

    public static ToukenDanshiMenu fromNetwork(int windowId, Inventory inv, FriendlyByteBuf data) {
        int entityId = data.readInt();
        Entity entity = inv.player.level().getEntity(entityId);
        if (entity instanceof ToukenDanshiEntity touken) {
            return new ToukenDanshiMenu(ModMenuTypes.TOUKEN_DANSHI_MENU.get(), windowId, inv, touken);
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

    public static class ArmorSlot extends SlotItemHandler {
        private final EquipmentSlot slotType;
        public ArmorSlot(net.minecraftforge.items.IItemHandler handler, int index, int x, int y, EquipmentSlot slotType) {
            super(handler, index, x, y);
            this.slotType = slotType;
        }
        @Override
        public boolean mayPlace(ItemStack stack) {
            if (stack.getItem() instanceof ArmorItem armor) {
                return armor.getEquipmentSlot() == this.slotType;
            }
            return false;
        }
        @Override
        public int getMaxStackSize() { return 1; }
    }

    public static class KnifeSlot extends SlotItemHandler {
        public KnifeSlot(net.minecraftforge.items.IItemHandler handler, int index, int x, int y) {
            super(handler, index, x, y);
        }
        @Override
        public boolean mayPlace(ItemStack stack) {
            return isKnifeItem(stack);
        }
        @Override
        public int getMaxStackSize() { return 1; }
    }
}