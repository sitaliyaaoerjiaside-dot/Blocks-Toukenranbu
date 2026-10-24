package com.Equatorial.toukenranbu.util;

import com.Equatorial.toukenranbu.effect.ModEffects;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InstantDeathHelper {

    public static final int MAX_LAYERS = 5;
    public static final int LAYER_DURATION = 30 * 20;   // 30 秒减一层

    private static final String NBT_LAST_ADD = "touken_death_last_add";

    /** BOSS 攻击生物时调用：层数 +1，刷新减层计时 */
    public static void addLayer(LivingEntity entity) {
        if (entity.level().isClientSide()) return;

        int current = getLayers(entity);
        int next = current + 1;

        if (next >= MAX_LAYERS) {
            // 只有这个被打到5层的实体才会被清空效果
            entity.removeEffect(ModEffects.MOON_REFLECTION.get());
            entity.getPersistentData().remove(NBT_LAST_ADD);

            // 判断目标类型分配伤害
            float finalDamage;
            if (entity instanceof Player) {
                finalDamage = 1000000.0f; // 玩家：100万
            } else if ((entity instanceof TamableAnimal tamable && tamable.isTame())
                    || (entity instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null)) {
                finalDamage = 100.0f;     // 驯服生物（刀男、女仆等）：100
            } else {
                finalDamage = (float) (Integer.MAX_VALUE - 1); // 野怪：21亿
            }

            // 借用白三日月专属的伤害类型（无视护甲、正常触发图腾/御守）
            Registry<DamageType> registry = entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
            ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE,
                    ResourceLocation.fromNamespaceAndPath("toukenranbu_mod", "white_mikazuki"));
            DamageSource source = new DamageSource(registry.getHolderOrThrow(key), null, null);

            entity.hurt(source, finalDamage);
            return;
        }

        // 没满5层的，保留/刷新效果
        entity.removeEffect(ModEffects.MOON_REFLECTION.get());
        entity.addEffect(new MobEffectInstance(
                ModEffects.MOON_REFLECTION.get(),
                LAYER_DURATION * next,
                next - 1,
                false, false, true
        ));

        entity.getPersistentData().putLong(NBT_LAST_ADD, entity.level().getGameTime());
    }

    /** 消层：外部 tick 调用，30秒自动减一层 */
    public static void removeLayers(LivingEntity entity, int count) {
        int current = getLayers(entity);
        if (current == 0) return;

        int next = Math.max(0, current - count);

        entity.removeEffect(ModEffects.MOON_REFLECTION.get());
        if (next > 0) {
            entity.addEffect(new MobEffectInstance(
                    ModEffects.MOON_REFLECTION.get(),
                    LAYER_DURATION * next,
                    next - 1,
                    false, false, true
            ));
        }

        entity.getPersistentData().putLong(NBT_LAST_ADD, entity.level().getGameTime());
    }

    public static int getLayers(LivingEntity entity) {
        var effect = entity.getEffect(ModEffects.MOON_REFLECTION.get());
        return effect == null ? 0 : effect.getAmplifier() + 1;
    }

    public static boolean shouldDecayLayer(LivingEntity entity) {
        if (getLayers(entity) <= 0) return false;
        long last = entity.getPersistentData().getLong(NBT_LAST_ADD);
        return entity.level().getGameTime() - last >= LAYER_DURATION;
    }

    @SubscribeEvent
    public static void onLivingTick(net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent event) {
        net.minecraft.world.entity.LivingEntity entity = event.getEntity();
        if (!entity.level().isClientSide && entity.tickCount % 20 == 0) {
            if (shouldDecayLayer(entity)) {
                removeLayers(entity, 1);
            }
        }
    }
}