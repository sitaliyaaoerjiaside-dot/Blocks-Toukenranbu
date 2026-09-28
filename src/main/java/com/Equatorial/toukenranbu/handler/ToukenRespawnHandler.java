package com.Equatorial.toukenranbu.handler;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID)
public class ToukenRespawnHandler {

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (event.isEndConquered()) return;

        UUID ownerId = player.getUUID();
        ServerLevel targetLevel = player.serverLevel();

        Set<ToukenDanshiEntity> owned = ToukenDanshiEntity.getOwnedDanshi().get(ownerId);
        if (owned == null || owned.isEmpty()) return;

        for (ToukenDanshiEntity danshi : owned) {
            if (!danshi.isAlive()) continue;
            if (!danshi.isFollowing() || danshi.isOrderedToSit()) continue;
            if (danshi.isFarming() || danshi.isMining() || danshi.isPatrolling()
                    || danshi.isSparring() || danshi.isCaveClearing()) continue;

            if (danshi.level().dimension() != targetLevel.dimension()) {
                danshi.teleportToDimensionSafe(targetLevel, player);
            }
        }
    }
}