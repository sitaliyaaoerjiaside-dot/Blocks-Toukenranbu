package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.capability.ModCapabilities;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID)
public class DanshiXpHandler {

    @SubscribeEvent
    public static void onPickupXp(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        if (!(player instanceof ServerPlayer)) return;

        boolean enabled = player.getCapability(ModCapabilities.SPIRIT_POWER)
                .map(cap -> cap.isDanshiXpEnabled())
                .orElse(false);
        if (!enabled) return;

        List<ToukenDanshiEntity> danshiList = player.level().getEntitiesOfClass(
                ToukenDanshiEntity.class,
                player.getBoundingBox().inflate(16.0),
                d -> d.isAlive() && d.isOwnedBy(player));
        if (danshiList.isEmpty()) return;

        int total = event.getOrb().getValue();
        int n = danshiList.size();
        int per = total / n;
        int remainder = total % n;

        for (int i = 0; i < n; i++) {
            danshiList.get(i).addExperience(per + (i < remainder ? 1 : 0));
        }

        event.getOrb().discard();
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        var itemEntity = event.getEntity();
        var player = event.getPlayer();
        if (itemEntity.getItem().is(com.Equatorial.toukenranbu.item.ModItems.CAPTAIN_BADGE.get())) {
            itemEntity.getPersistentData().putUUID("touken_captain_owner", player.getUUID());
        }
    }
}