package com.Equatorial.toukenranbu.event;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.world.registry.ModDimensions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * 统一的生物生成管控。
 *
 * 规则：
 * 1. 白夜之庭维度：
 *    - 拦截所有 MONSTER 分类（原版怪物 + 模组怪物 + 其他模组怪物）
 *    - 例外：白三日月宗近、BOSS 召唤的 minion
 *    - 其他全放行（动物、鱼类、村民、刀男、狐之助……）
 *
 * 2. 非白夜之庭维度（主世界、下界、末地、废弃的历史……）：
 *    - 只拦截无主刀男的自然生成
 *    - 狐之助不拦（它是动物分类，也不会命中下面的判断）
 *    - 原版怪物、其他模组怪物一律放行
 */
public class ToukenSpawnHandler {

    @SubscribeEvent
    public static void onMobSpawn(MobSpawnEvent.PositionCheck event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        if (isWhiteNightGarden(serverLevel)) {
            if (shouldBlockInWhiteNightGarden(event.getEntity().getType(),
                    event.getEntity().getPersistentData().getBoolean("touken_boss_minion"))) {
                event.setResult(Event.Result.DENY);
            }
        } else {
            if (shouldBlockWildDanshi(event.getEntity().getType())) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (event.getEntity() instanceof Player) return;

        if (isWhiteNightGarden(serverLevel)) {
            if (shouldBlockInWhiteNightGarden(event.getEntity().getType(),
                    event.getEntity().getPersistentData().getBoolean("touken_boss_minion"))) {
                event.setCanceled(true);
            }
        } else {
            if (event.getEntity() instanceof ToukenDanshiEntity danshi && !danshi.isTame()) {
                event.setCanceled(true);
            }
        }
    }

    // ===== 判定逻辑 =====

    private static boolean isWhiteNightGarden(ServerLevel level) {
        return level.dimension().equals(ModDimensions.WHITE_NIGHT_GARDEN_LEVEL);
    }

    /**
     * 白夜之庭：拦所有 MONSTER，BOSS 和 minion 除外。
     */
    private static boolean shouldBlockInWhiteNightGarden(EntityType<?> type, boolean isMinion) {
        if (type.getCategory() != MobCategory.MONSTER) return false;
        if (type.equals(ModEntityTypes.WHITE_MIKAZUKI_MUNECHIKA.get())) return false;
        return !isMinion;
    }

    /**
     * 非白夜之庭：拦无主刀男（模组 CREATURE 分类，狐之助除外）。
     * 已驯服的刀男、绘马召唤、收容符释放不受影响（它们走 onEntityJoin 的 isTame 判断）。
     */
    private static boolean shouldBlockWildDanshi(EntityType<?> type) {
        ResourceLocation id = type.builtInRegistryHolder().key().location();
        if (!id.getNamespace().equals(ToukenRanbuMod.MOD_ID)) return false;
        if (type.getCategory() != MobCategory.CREATURE) return false;
        return !type.equals(ModEntityTypes.KONNOSUKE.get());
    }
}