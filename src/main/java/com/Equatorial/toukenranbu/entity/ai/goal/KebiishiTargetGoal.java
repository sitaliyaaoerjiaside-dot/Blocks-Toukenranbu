package com.Equatorial.toukenranbu.entity.ai.goal;

import com.Equatorial.toukenranbu.entity.custom.JikkoEntity;
import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class KebiishiTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    public KebiishiTargetGoal(Mob mob) {
        super(mob, LivingEntity.class, 10, true, false,
                entity -> entity instanceof Player
                        || entity instanceof ToukenDanshiEntity
                        || entity instanceof JikkoEntity);
    }
}