package com.Equatorial.toukenranbu.screen;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.lowdragmc.lowdraglib2.compat.network.RegistryFriendlyByteBuf;
import com.lowdragmc.lowdraglib2.gui.factory.IContainerUIHolder;
import com.lowdragmc.lowdraglib2.gui.factory.LDMenuTypes;
import com.lowdragmc.lowdraglib2.gui.holder.ModularUIContainerMenu;
import com.lowdragmc.lowdraglib2.gui.ui.ModularUI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class EntityUIMenuType {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ToukenRanbuMod.MOD_ID);

    public static final RegistryObject<MenuType<ModularUIContainerMenu>> ENTITY_UI =
            MENUS.register("entity_ui", () -> IForgeMenuType.create(EntityUIMenuType::create));

    /** 服务端调用：打开实体的 UI */
    public static boolean openUI(ServerPlayer player, ToukenDanshiEntity entity) {
        EntityUIHolder holder = new EntityUIHolder(player, entity);
        NetworkHooks.openScreen(player, holder,
                buf -> holder.writeClientSideData(null, LDMenuTypes.wrapMenuDataBuffer(buf)));
        return true;
    }

    /** 客户端/服务端收到 openScreen 后调这个构造 Menu */
    public static ModularUIContainerMenu create(int windowId, Inventory inv, FriendlyByteBuf data) {
        RegistryFriendlyByteBuf registryData = LDMenuTypes.wrapMenuDataBuffer(data);
        Player player = inv.player;
        int entityId = registryData.readVarInt();
        Entity entity = player.level().getEntity(entityId);
        if (entity instanceof ToukenDanshiEntity danshi) {
            EntityUIHolder holder = new EntityUIHolder(player, danshi);
            return new ModularUIContainerMenu(ENTITY_UI.get(), windowId, inv, holder);
        }
        throw new IllegalArgumentException("No touken danshi found for id " + entityId);
    }

    public static class EntityUIHolder implements MenuProvider, IContainerUIHolder {
        public final Player player;
        public final ToukenDanshiEntity entity;

        public EntityUIHolder(Player player, ToukenDanshiEntity entity) {
            this.player = player;
            this.entity = entity;
        }

        @Override
        public boolean isStillValid(Player player) {
            return this.entity.isAlive() && this.entity.distanceTo(player) < 8.0F;
        }

        @Override
        public Component getDisplayName() {
            return this.entity.getName();
        }

        @Nullable
        @Override
        public ModularUIContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
            return new ModularUIContainerMenu(ENTITY_UI.get(), containerId, playerInventory, this);
        }

        public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
            buffer.writeVarInt(this.entity.getId());
        }

        @Override
        public ModularUI createUI(Player player) {
            return ToukenEntityUI2.INSTANCE.createUI(player, this.entity);
        }
    }
}