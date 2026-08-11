package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.advancement.ModAdvancementTriggers;
import com.Equatorial.toukenranbu.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AmuletDeathHandler {

    //御守护符
    private static final List<RegistryObject<Item>> ALL_AMULETS = List.of(
            ModItems.AMULET,
            ModItems.SUPREME_AMULET
    );

    //效果数值常量
    private static final float NORMAL_RESURRECTION_HEALTH = 1.0f;
    private static final int RESISTANCE_DURATION = 200; // 10秒
    private static final int RESISTANCE_LEVEL = 1;
    private static final int REGENERATION_DURATION = 100; // 5秒
    private static final int REGENERATION_LEVEL = 1;

    //事件监听
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingDeath(LivingDeathEvent event) {
        // 如果其他模组已经处理了死亡，不再干预
        if (event.isCanceled()) return;

        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        AmuletLocation slot = findAmulet(player);
        if (slot == null) {
            return;
        }

        event.setCanceled(true);
        performResurrection(player, slot.stack);
        consumeTotem(slot);
    }

    //复活逻辑
    private static void performResurrection(ServerPlayer player, ItemStack totem) {
        player.clearFire();
        player.removeAllEffects();

        if (isSupremeAmulet(totem)) {
            //极御守
            player.setHealth(player.getMaxHealth());
            player.addEffect(new MobEffectInstance(
                    MobEffects.DAMAGE_RESISTANCE,
                    RESISTANCE_DURATION,
                    RESISTANCE_LEVEL
            ));
            player.addEffect(new MobEffectInstance(
                    MobEffects.REGENERATION,
                    REGENERATION_DURATION,
                    REGENERATION_LEVEL
            ));
            ModAdvancementTriggers.USE_SUPREME_AMULET.trigger(player);
        } else {
            // 御守
            player.setHealth(NORMAL_RESURRECTION_HEALTH);
            ModAdvancementTriggers.USE_AMULET.trigger(player);
        }

        // 手动生成不死图腾粒子，只出绿光不出举手旋转动画
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.TOTEM_OF_UNDYING,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    100,   // 粒子数量
                    0.5,   // X 扩散
                    0.5,   // Y 扩散
                    0.5,   // Z 扩散
                    0.5    // 速度
            );
        }

        player.level().playSound(
                null,
                player.getX(), player.getY(), player.getZ(),
                SoundEvents.TOTEM_USE,
                SoundSource.PLAYERS,
                1.0f, 1.0f
        );
    }

    //查找御守
    private static AmuletLocation findAmulet(ServerPlayer player) {
        ItemStack offhand = player.getOffhandItem();
        if (isValidTotem(offhand)) return new AmuletLocation(offhand);

        ItemStack mainhand = player.getMainHandItem();
        if (isValidTotem(mainhand)) return new AmuletLocation(mainhand);

        for (ItemStack stack : player.getInventory().items) {
            if (isValidTotem(stack)) {
                return new AmuletLocation(stack);
            }
        }
        return null;
    }

    private static boolean isValidTotem(ItemStack stack) {
        if (stack.isEmpty()) return false;
        //加新御守，不经常改这
        return ALL_AMULETS.stream().anyMatch(ref -> stack.is(ref.get()));
    }

    private static boolean isSupremeAmulet(ItemStack stack) {
        return stack.is(ModItems.SUPREME_AMULET.get());
    }

    //消耗御守
    private static void consumeTotem(AmuletLocation slot) {
        slot.stack.shrink(1);
    }

    //内部类
    private static class AmuletLocation {
        final ItemStack stack;

        AmuletLocation(ItemStack stack) {
            this.stack = stack;
        }
    }
}