package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.entity.bubble.BubbleQueueManager; // 👈 新增导包
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.touken.FormationType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel; // 👈 新增导包
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ToukenContainerMenu extends AbstractContainerMenu {
    private final ToukenDanshiEntity entity;

    public ToukenContainerMenu(int windowId, Inventory playerInv, FriendlyByteBuf data) {
        this(windowId, playerInv, (ToukenDanshiEntity) playerInv.player.level().getEntity(data.readVarInt()));
    }

    public ToukenContainerMenu(int windowId, Inventory playerInv, ToukenDanshiEntity entity) {
        super(ModMenus.TOUKEN_MENU.get(), windowId);
        this.entity = entity;

        for (int i = 0; i < 4; i++) {
            this.addSlot(new SlotItemHandler(entity.getArmorHandler(), i, 7, 9 + i * 18));
        }
        for (int i = 0; i < 3; i++) {
            this.addSlot(new SlotItemHandler(entity.getKnifeHandler(), i, 88, 12 + i * 18));
        }
        this.addSlot(new SlotItemHandler(entity.getMountHandler(), 0, 114, 12));
        this.addSlot(new SlotItemHandler(entity.getBladeHandler(), 0, 114, 48));

        IItemHandler inv = entity.getInventoryHandler();
        for (int i = 0; i < 25; i++) {
            this.addSlot(new SlotItemHandler(inv, i, 160 + (i % 5) * 18, 12 + (i / 5) * 18));
        }

        for (int i = 0; i < 27; i++) {
            this.addSlot(new Slot(playerInv, i + 9, 88 + (i % 9) * 18, 144 + (i / 9) * 18));
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInv, i, 88 + i * 18, 202));
        }
    }

    public ToukenDanshiEntity getEntity() {
        return entity;
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (this.entity == null || !this.entity.isAlive() || !this.entity.isOwnedBy(player)) {
            return false;
        }

        switch (id) {
            case 0: // 阵型切换
                boolean isFollowingCaptain = this.entity.getCaptain() != null && this.entity.getCaptain() != this.entity;
                if (isFollowingCaptain) return false;
                FormationType[] types = FormationType.values();
                int nextIndex = (this.entity.getFormationType().ordinal() + 1) % types.length;
                this.entity.setFormationType(types[nextIndex]);
                this.entity.syncExtraData();
                return true;

            case 1: // 坐下
                boolean sit = !this.entity.isOrderedToSit();
                this.entity.setOrderedToSit(sit);
                // 只有刚坐下的时候才触发回应
                if (sit && this.entity.level() instanceof ServerLevel sl) {
                    BubbleQueueManager.enqueue(sl, this.entity, "gui.toukenranbu.bubble.mode_ack_sit", 3);
                }
                return true;

            case 2: // 跟随
                boolean follow = !this.entity.isFollowing();
                this.entity.setFollowing(follow);
                if (follow && this.entity.level() instanceof ServerLevel sl) {
                    BubbleQueueManager.enqueue(sl, this.entity, "gui.toukenranbu.bubble.mode_ack_follow", 3);
                }
                return true;

            case 3: // 种田
                boolean farm = !this.entity.isFarming();
                this.entity.setFarming(farm);
                if (farm && this.entity.level() instanceof ServerLevel sl) {
                    BubbleQueueManager.enqueue(sl, this.entity, "gui.toukenranbu.bubble.mode_ack_farming", 3);
                }
                return true;

            case 4: // 矿石采集
                if (!this.entity.isMining()) {
                    boolean hasPickaxe = false;
                    for (int i = 0; i < this.entity.getInventoryHandler().getSlots(); i++) {
                        if (this.entity.getInventoryHandler().getStackInSlot(i).getItem() instanceof PickaxeItem) {
                            hasPickaxe = true; break;
                        }
                    }
                    if (!hasPickaxe) return false; // 没镐子拦截
                }
                boolean mine = !this.entity.isMining();
                this.entity.setMining(mine);
                if (mine && this.entity.level() instanceof ServerLevel sl) {
                    BubbleQueueManager.enqueue(sl, this.entity, "gui.toukenranbu.bubble.mode_ack_mining", 3);
                }
                return true;

            case 5: // 巡逻
                if (!this.entity.isPatrolling()) {
                    this.entity.startPatrol();
                    if (this.entity.level() instanceof ServerLevel sl) {
                        BubbleQueueManager.enqueue(sl, this.entity, "gui.toukenranbu.bubble.mode_ack_patrol", 3);
                    }
                } else {
                    this.entity.setPatrolling(false);
                }
                return true;

            case 6: // 手合
                if (!this.entity.isSparring()) {
                    ToukenDanshiEntity partner = this.entity.findSparringPartner();
                    if (partner == null || partner == this.entity || !partner.isTame()) {
                        return false;
                    }
                    this.entity.startSparring(partner);
                    partner.startSparring(this.entity);
                    if (this.entity.level() instanceof ServerLevel sl) {
                        BubbleQueueManager.enqueue(sl, this.entity, "gui.toukenranbu.bubble.mode_ack_spar", 3);
                    }
                } else {
                    this.entity.setSparring(false);
                    ToukenDanshiEntity partner = this.entity.getSparringPartner();
                    if (partner != null && partner.isSparring()) {
                        partner.setSparring(false);
                    }
                }
                return true;

            case 7: // 矿洞清缴
                if (!this.entity.isCaveClearing()) {
                    if (!this.entity.hasTorches()) return false; // 没火把拦截
                }
                boolean cave = !this.entity.isCaveClearing();
                this.entity.setCaveClearing(cave);
                if (cave && this.entity.level() instanceof ServerLevel sl) {
                    BubbleQueueManager.enqueue(sl, this.entity, "gui.toukenranbu.bubble.mode_ack_cave", 3);
                }
                return true;

            case 8: // 跟随捡物
                this.entity.setPickupWhenFollowing(!this.entity.isPickupWhenFollowing());
                return true;
        }
        return super.clickMenuButton(player, id);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, this.slots.size(), true)) return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return entity.isAlive() && entity.distanceTo(player) < 8.0F;
    }
}