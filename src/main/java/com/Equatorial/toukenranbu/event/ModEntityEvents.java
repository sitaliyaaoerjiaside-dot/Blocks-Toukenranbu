package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ToukenRanbuMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEntityEvents {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            // 只在本模组维度生效
            if (!villager.level().dimension().location().getNamespace().equals(ToukenRanbuMod.MOD_ID)) {
                return;
            }

            ZombieVillager zombie = EntityType.ZOMBIE_VILLAGER.create(villager.level());
            if (zombie != null) {
                zombie.moveTo(villager.getX(), villager.getY(), villager.getZ(), villager.getYRot(), villager.getXRot());
                zombie.setVillagerData(villager.getVillagerData());
                villager.level().addFreshEntity(zombie);
                event.setCanceled(true);
            }
        }
    }
}