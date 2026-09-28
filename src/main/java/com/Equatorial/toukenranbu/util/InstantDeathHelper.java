package com.Equatorial.toukenranbu.util;

import com.Equatorial.toukenranbu.effect.ModEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class InstantDeathHelper {

    public static final int MAX_LAYERS = 5;
    public static final int LAYER_DURATION = 30 * 20;   // 30 秒减一层
    public static final int COUNTDOWN_TICKS = 3 * 20;

    private static final String NBT_COUNTDOWN = "touken_death_countdown";
    private static final String NBT_LAST_ADD = "touken_death_last_add";

    /** BOSS 攻击玩家时调用：层数 +1，刷新减层计时 */
    public static void addLayer(ServerPlayer player) {
        int current = getLayers(player);
        int next = Math.min(current + 1, MAX_LAYERS);

        player.removeEffect(ModEffects.MOON_REFLECTION.get());
        player.addEffect(new MobEffectInstance(
                ModEffects.MOON_REFLECTION.get(),
                LAYER_DURATION * next,   // 视觉上按层数显示剩余时间
                next - 1,
                false, false, true
        ));

        player.getPersistentData().putLong(NBT_LAST_ADD, player.level().getGameTime());
    }

    /** 消层：月之樱或御守拦截时调用 */
    public static void removeLayers(ServerPlayer player, int count) {
        int current = getLayers(player);
        if (current == 0) return;

        int next = Math.max(0, current - count);

        player.removeEffect(ModEffects.MOON_REFLECTION.get());
        if (next > 0) {
            player.addEffect(new MobEffectInstance(
                    ModEffects.MOON_REFLECTION.get(),
                    LAYER_DURATION * next,
                    next - 1,
                    false, false, true
            ));
        }

        // 减层后重置计时，从这一刻重新数 30 秒
        player.getPersistentData().putLong(NBT_LAST_ADD, player.level().getGameTime());
    }

    public static int getLayers(ServerPlayer player) {
        var effect = player.getEffect(ModEffects.MOON_REFLECTION.get());
        return effect == null ? 0 : effect.getAmplifier() + 1;
    }

    /** 供 tick handler 判断是否该减一层 */
    public static boolean shouldDecayLayer(ServerPlayer player) {
        if (getLayers(player) <= 0) return false;
        long last = player.getPersistentData().getLong(NBT_LAST_ADD);
        return player.level().getGameTime() - last >= LAYER_DURATION;
    }

    public static boolean isInCountdown(ServerPlayer player) {
        return player.getPersistentData().contains(NBT_COUNTDOWN);
    }

    public static void startCountdown(ServerPlayer player) {
        player.getPersistentData().putLong(NBT_COUNTDOWN,
                player.level().getGameTime() + COUNTDOWN_TICKS);
    }

    public static boolean isCountdownExpired(ServerPlayer player) {
        var data = player.getPersistentData();
        if (!data.contains(NBT_COUNTDOWN)) return false;
        return player.level().getGameTime() >= data.getLong(NBT_COUNTDOWN);
    }

    public static void clearCountdown(ServerPlayer player) {
        player.getPersistentData().remove(NBT_COUNTDOWN);
    }
}