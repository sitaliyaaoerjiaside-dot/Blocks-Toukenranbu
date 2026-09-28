package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "toukenranbu_mod")
public class GiveBookOnLogin {

    private static final String KEY = "toukenranbu_mod_has_book";

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide) return;
        if (!ModList.get().isLoaded("patchouli")) return;

        Player player = event.getEntity();
        var root = player.getPersistentData();
        var persisted = root.getCompound(Player.PERSISTED_NBT_TAG);

        if (persisted.getBoolean(KEY)) return;

        ItemStack book = new ItemStack(ModItems.TOUKENRANBU_ENCYCLOPEDIA.get());
        if (!player.getInventory().add(book)) {
            player.drop(book, false);
        }

        persisted.putBoolean(KEY, true);
        root.put(Player.PERSISTED_NBT_TAG, persisted);
    }
}