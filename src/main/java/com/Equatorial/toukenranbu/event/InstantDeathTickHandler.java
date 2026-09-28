package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.damage.ModDamageTypes;
import com.Equatorial.toukenranbu.util.InstantDeathHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID)
public class InstantDeathTickHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!event.side.isServer() || event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        // 兜底：万一血量已经异常，直接拉回来，避免 NaN 污染存档
        float hp = player.getHealth();
        if (Float.isNaN(hp) || hp < 0.0f || hp > player.getMaxHealth() * 10) {
            player.setHealth(player.getMaxHealth());
            return;
        }

        int layers = InstantDeathHelper.getLayers(player);

        if (layers >= InstantDeathHelper.MAX_LAYERS) {
            if (!InstantDeathHelper.isInCountdown(player)) {
                InstantDeathHelper.startCountdown(player);
            }
            if (InstantDeathHelper.isCountdownExpired(player)) {
                InstantDeathHelper.clearCountdown(player);

                var holder = player.level().registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(ModDamageTypes.BOSS_INSTANT_DEATH);
                DamageSource source = new DamageSource(holder, player, player);

                player.hurt(source, 10000000.0f);
            }
        } else {
            if (InstantDeathHelper.isInCountdown(player)) {
                InstantDeathHelper.clearCountdown(player);
            }
        }
    }
}