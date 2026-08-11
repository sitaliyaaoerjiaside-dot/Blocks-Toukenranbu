package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ToukenFriendlyFireHandler {

    @SubscribeEvent
    public static void onSetTarget(LivingChangeTargetEvent e) {
        if (!(e.getEntity() instanceof ToukenDanshiEntity a)) return;
        if (!(e.getNewTarget() instanceof ToukenDanshiEntity b)) return;
        UUID o1 = a.getOwnerUUID(), o2 = b.getOwnerUUID();
        if (o1 != null && o1.equals(o2)) e.setCanceled(true);
    }

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent e) {
        if (e.getSource() == null || !(e.getSource().getEntity() instanceof ToukenDanshiEntity a)) return;
        if (!(e.getEntity() instanceof ToukenDanshiEntity b)) return;
        UUID o1 = a.getOwnerUUID(), o2 = b.getOwnerUUID();
        if (o1 != null && o1.equals(o2)) e.setCanceled(true);
    }
}