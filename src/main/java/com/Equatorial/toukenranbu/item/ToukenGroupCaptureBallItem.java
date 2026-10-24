package com.Equatorial.toukenranbu.item;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class ToukenGroupCaptureBallItem extends GroupCaptureBallItem {

    public ToukenGroupCaptureBallItem(Properties properties) {
        super(properties);
    }

    @Override
    protected String getTranslationKey() {
        return "touken_group_capture_ball";
    }

    @Override
    protected List<LivingEntity> findTargets(Level level, Player player, AABB area) {
        return new ArrayList<>(level.getEntitiesOfClass(
                ToukenDanshiEntity.class,
                area,
                e -> e.isTame() && e.isOwnedBy(player)));
    }
}