package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.resources.ResourceLocation;
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

            // 修改这里：只匹配“废弃历史”维度的完整 ID
            ResourceLocation dimId = villager.level().dimension().location();
            ResourceLocation abandonedHistoryId = ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "abandoned_history");

            // 如果不是废弃历史维度，直接放行，白夜之庭正常刷村民
            if (!dimId.equals(abandonedHistoryId)) {
                return;
            }

            // ======= 以下只在废弃历史维度生效 =======
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