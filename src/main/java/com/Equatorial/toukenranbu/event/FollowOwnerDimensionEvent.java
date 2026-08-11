package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "toukenranbu_mod")
public class FollowOwnerDimensionEvent {

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.getServer() == null) return;

        // 获取玩家到达的新维度
        ServerLevel targetLevel = player.getServer().getLevel(event.getTo());
        if (targetLevel == null) return;

        UUID ownerUUID = player.getUUID();
        Set<ToukenDanshiEntity> danshiSet = ToukenDanshiEntity.getOwnedDanshi().get(ownerUUID);
        if (danshiSet == null || danshiSet.isEmpty()) return;

        for (ToukenDanshiEntity danshi : new ArrayList<>(danshiSet)) {
            if (danshi.isRemoved() || danshi.isOrderedToSit()) continue;
            if (!danshi.isFollowing()) continue;     // 没开跟随（闲逛状态）的不跟
            if (danshi.isFarming()) continue;        // 种地的不跟

            // 关键修复：已经在目标维度的实体也要拉到玩家身边
            // 防止实体被原版传送门机制提前传回主世界，卡在传送门附近
            if (danshi.level().dimension() == event.getTo()) {
                Vec3 safePos = ToukenDanshiEntity.findSafePosNear(targetLevel, player.getX(), player.getY(), player.getZ());
                danshi.teleportTo(safePos.x, safePos.y, safePos.z);
                continue;
            }

            // 强制跨维度传送
            Entity newEntity = danshi.changeDimension(targetLevel);
            if (newEntity == null) {
                System.out.println("[跨维度跟随] changeDimension 返回 null: " + danshi.getName().getString()
                        + " 目标维度: " + event.getTo().location());
            } else {
                // 先找安全位置再传送，防止卡进下界小隧道的墙壁/天花板窒息
                Vec3 safePos = ToukenDanshiEntity.findSafePosNear(targetLevel, player.getX(), player.getY(), player.getZ());
                newEntity.teleportTo(safePos.x, safePos.y, safePos.z);
            }
        }
    }
}
