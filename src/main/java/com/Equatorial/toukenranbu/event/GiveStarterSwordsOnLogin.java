package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.item.ModItems;
import com.Equatorial.toukenranbu.util.StarterSwordHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "toukenranbu_mod")
public class GiveStarterSwordsOnLogin {

    private static final String KEY = "toukenranbu_mod_has_starter_swords";

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide) return;

        Player player = event.getEntity();
        if (player.isCreative()) return;

        var root = player.getPersistentData();
        var persisted = root.getCompound(Player.PERSISTED_NBT_TAG);
        if (persisted.getBoolean(KEY)) return;

        // ===== 第一次进入世界的时候会发五把初始刀【为了跟游戏对轴】玩家只能选其中一把 =====
        give(player, new ItemStack(ModItems.KASHUU_KIYOMITSU.get()));
        give(player, new ItemStack(ModItems.YAMANBAGIRI_KUNIHIRO.get()));
        give(player, new ItemStack(ModItems.MUTSUNOKAMI_YOSHIYUKI.get()));
        give(player, new ItemStack(ModItems.KASEN_KANESADA.get()));
        give(player, new ItemStack(ModItems.HACHISUKA_KOTETSU.get()));
        // ============================================

        persisted.putBoolean(KEY, true);
        root.put(Player.PERSISTED_NBT_TAG, persisted);
    }

    private static void give(Player player, ItemStack stack) {
        if (stack.isEmpty()) return;
        StarterSwordHelper.markAsStarter(stack);
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }
}