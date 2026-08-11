// src/main/java/com/Equatorial/toukenranbu/capability/SpiritPowerEvents.java
package com.Equatorial.toukenranbu.capability;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.effect.ModEffects;
import com.Equatorial.toukenranbu.network.SpiritPowerSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID)
public class SpiritPowerEvents {

    private static final ResourceLocation SPIRIT_POWER_CAP =
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "spirit_power");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(SPIRIT_POWER_CAP, new SpiritPowerProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(oldCap -> {
                event.getEntity().getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(newCap -> {
                    newCap.setSpiritPower(oldCap.getSpiritPower());
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SpiritPowerSyncPacket.sendToPlayer(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SpiritPowerSyncPacket.sendToPlayer(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SpiritPowerSyncPacket.sendToPlayer(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.END) {

            if (event.player instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE
                        || serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
                    return;
                }
            }

            boolean hasRegenBonus = event.player.hasEffect(ModEffects.SPIRIT_REGEN.get());

            int interval = hasRegenBonus ? 20 : 100;

            if (event.player.tickCount % interval == 0) {
                event.player.getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(cap -> {
                    if (cap.getSpiritPower() < cap.getMaxSpiritPower()) {
                        cap.addSpiritPower(1);
                        if (event.player instanceof ServerPlayer serverPlayer) {
                            SpiritPowerSyncPacket.sendToPlayer(serverPlayer);
                        }
                    }
                });
            }
        }
    }
}