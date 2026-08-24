package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModForgeEvents {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        UUID playerUUID = event.getOriginal().getUUID();
        Set<ToukenDanshiEntity> set = ToukenDanshiEntity.getOwnedDanshi().get(playerUUID);
        if (set == null) return;

        ServerPlayer newPlayer = (ServerPlayer) event.getEntity();
        ServerLevel targetLevel = newPlayer.serverLevel();

        for (ToukenDanshiEntity danshi : new java.util.ArrayList<>(set)) {
            if (danshi == null || !danshi.isAlive()) continue;

            // 种地的刀男继续种地，手合的继续手合，不动
            if (danshi.isFarming()) continue;
            if (danshi.isSparring()) continue;

            // 其他状态（挖矿、巡逻、手合、矿洞清缴、跟随、闲逛）停止工作，召回
            danshi.setOrderedToSit(false);
            danshi.setMining(false);
            danshi.setPatrolling(false);
            danshi.setSparring(false);
            danshi.setCaveClearing(false);
            danshi.setFollowing(true);

            if (danshi.level().dimension() != targetLevel.dimension()) {
                Entity moved = danshi.changeDimension(targetLevel);
                if (moved instanceof ToukenDanshiEntity newDanshi) {
                    Vec3 pos = ToukenDanshiEntity.findSafePosNear(targetLevel, newPlayer.getX(), newPlayer.getY(), newPlayer.getZ());
                    newDanshi.teleportTo(pos.x, pos.y, pos.z);
                }
            } else {
                Vec3 pos = ToukenDanshiEntity.findSafePosNear(targetLevel, newPlayer.getX(), newPlayer.getY(), newPlayer.getZ());
                danshi.teleportTo(pos.x, pos.y, pos.z);
            }
        }
    }
}