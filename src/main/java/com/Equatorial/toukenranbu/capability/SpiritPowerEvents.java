package com.Equatorial.toukenranbu.capability;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.effect.ModEffects;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
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

    // ★ 护甲韧性 modifier 的 UUID
    private static final java.util.UUID SPIRIT_TOUGHNESS_UUID =
            java.util.UUID.fromString("c3d4e5f6-a7b8-9012-cdef-345678901234");

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(SPIRIT_POWER_CAP, new SpiritPowerProvider());
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(net.minecraftforge.event.entity.living.LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        player.getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(cap -> {
            cap.setSpiritPower(cap.getSpiritPower() - 70);
        });
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        event.getOriginal().reviveCaps();

        event.getOriginal().getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(oldCap -> {
            event.getEntity().getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(newCap -> {
                newCap.setSpiritPower(oldCap.getSpiritPower());
                newCap.setFriendlyFireEnabled(oldCap.isFriendlyFireEnabled());
                newCap.setDanshiXpEnabled(oldCap.isDanshiXpEnabled());
            });
        });

        event.getOriginal().invalidateCaps();
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

            // ★ 灵力 ≥ 30 → 护甲韧性 +8 / 伤害吸收 I / 抗性提升 II
            if (event.player.tickCount % 20 == 0) {
                event.player.getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(cap -> {
                    boolean hasBuff = cap.getSpiritPower() >= 30;

                    var toughnessAttr = event.player.getAttribute(
                            net.minecraft.world.entity.ai.attributes.Attributes.ARMOR_TOUGHNESS);

                    if (hasBuff) {
                        // 抗性提升 II（减伤 40%）
                        var currentResist = event.player.getEffect(
                                net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE);
                        if (currentResist == null || currentResist.getAmplifier() < 1) {
                            event.player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                    net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE,
                                    400, 1, false, false, true));
                        }
                        // 伤害吸收 I
                        if (!event.player.hasEffect(net.minecraft.world.effect.MobEffects.ABSORPTION)) {
                            event.player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                    net.minecraft.world.effect.MobEffects.ABSORPTION,
                                    400, 0, false, false, true));
                        }
                        // 护甲韧性 +8
                        if (toughnessAttr != null && toughnessAttr.getModifier(SPIRIT_TOUGHNESS_UUID) == null) {
                            toughnessAttr.addTransientModifier(
                                    new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                                            SPIRIT_TOUGHNESS_UUID, "spirit_power_toughness", 8.0,
                                            net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION));
                        }
                    } else {
                        // 灵力 < 30：移除
                        var currentResist = event.player.getEffect(
                                net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE);
                        if (currentResist != null && currentResist.getAmplifier() >= 1) {
                            event.player.removeEffect(
                                    net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE);
                        }
                        if (event.player.hasEffect(net.minecraft.world.effect.MobEffects.ABSORPTION)) {
                            event.player.removeEffect(net.minecraft.world.effect.MobEffects.ABSORPTION);
                        }
                        if (toughnessAttr != null && toughnessAttr.getModifier(SPIRIT_TOUGHNESS_UUID) != null) {
                            toughnessAttr.removeModifier(SPIRIT_TOUGHNESS_UUID);
                        }
                    }
                });
            }

            // ===== 灵力 → 推反隐 / 混合伤害给该玩家所有刀男 =====
            if (event.player.tickCount % 20 == 0) {
                event.player.getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(cap -> {
                    int spirit = cap.getSpiritPower();

                    var owned = ToukenDanshiEntity.getOwnedDanshi().get(event.player.getUUID());
                    if (owned == null || owned.isEmpty()) return;

                    var antiInvis = ModEffects.TOUKEN_ANTI_INVIS.get();
                    var mixed = ModEffects.TOUKEN_MIXED_DAMAGE.get();
                    boolean giveAntiInvis = spirit >= 50;
                    boolean giveMixed = spirit >= 70;

                    for (ToukenDanshiEntity danshi : new java.util.ArrayList<>(owned)) {
                        if (danshi.isRemoved() || !danshi.isAlive()) continue;

                        if (giveAntiInvis) {
                            if (!danshi.hasEffect(antiInvis)) {
                                danshi.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                        antiInvis, -1, 0, false, false, true));
                            }
                        } else if (danshi.hasEffect(antiInvis)) {
                            danshi.removeEffect(antiInvis);
                        }

                        if (giveMixed) {
                            if (!danshi.hasEffect(mixed)) {
                                danshi.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                                        mixed, -1, 0, false, false, true));
                            }
                        } else if (danshi.hasEffect(mixed)) {
                            danshi.removeEffect(mixed);
                        }
                    }
                });
            }

            // ===== 灵力回复 =====
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

            // ===== 刀剑男士防丢失：玩家侧主动检查 =====
            if (event.player instanceof ServerPlayer serverPlayer && serverPlayer.tickCount % 40 == 0) {
                var ownedSet = ToukenDanshiEntity.getOwnedDanshi().get(serverPlayer.getUUID());
                if (ownedSet != null && !ownedSet.isEmpty()) {
                    for (ToukenDanshiEntity danshi : new java.util.ArrayList<>(ownedSet)) {
                        if (danshi.isRemoved() || !danshi.isAlive()) continue;

                        if (!danshi.isFollowing() || danshi.isOrderedToSit()
                                || danshi.isFarming() || danshi.isMining()
                                || danshi.isPatrolling() || danshi.isSparring()
                                || danshi.isCaveClearing()) {
                            continue;
                        }

                        boolean crossDim = serverPlayer.level().dimension() != danshi.level().dimension();
                        boolean tooFar = !crossDim && danshi.distanceToSqr(serverPlayer) > 4096.0;

                        if (crossDim || tooFar) {
                            if (danshi.level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
                                net.minecraft.world.level.ChunkPos cp =
                                        new net.minecraft.world.level.ChunkPos(danshi.blockPosition());
                                serverLevel.getChunk(cp.x, cp.z);
                            }

                            if (crossDim) {
                                Entity newEntity = danshi.changeDimension(
                                        (net.minecraft.server.level.ServerLevel) serverPlayer.level());
                                if (newEntity instanceof ToukenDanshiEntity t) {
                                    net.minecraft.world.phys.Vec3 safe = ToukenDanshiEntity.findSafePosNear(
                                            (net.minecraft.server.level.ServerLevel) serverPlayer.level(),
                                            serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
                                    t.teleportTo(safe.x, safe.y, safe.z);
                                    t.setTarget(null);
                                    t.getNavigation().stop();
                                }
                            } else {
                                net.minecraft.world.phys.Vec3 safe = ToukenDanshiEntity.findSafePosNear(
                                        (net.minecraft.server.level.ServerLevel) serverPlayer.level(),
                                        serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());
                                danshi.teleportTo(safe.x, safe.y, safe.z);
                                danshi.setTarget(null);
                                danshi.getNavigation().stop();
                            }
                        }
                    }
                }
            }
            // ===== 防丢失结束 =====
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(net.minecraftforge.event.entity.living.LivingHurtEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        if (player.gameMode.getGameModeForPlayer() == GameType.CREATIVE
                || player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
            return;
        }

        if (event.getSource().getEntity() instanceof net.minecraft.world.entity.Mob attacker
                && !(attacker instanceof com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity)) {

            boolean isHostile = attacker instanceof net.minecraft.world.entity.monster.Monster
                    || attacker.getTarget() == player;

            if (isHostile) {
                player.getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(cap -> {
                    cap.setSpiritPower(cap.getSpiritPower() - 2);
                    SpiritPowerSyncPacket.sendToPlayer(player);
                });
            }
        }
    }
}