package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

import java.util.UUID;

public class ToukenHurtByTargetGoal extends HurtByTargetGoal {
    public ToukenHurtByTargetGoal(PathfinderMob mob) {
        super(mob);
    }

    @Override
    public boolean canUse() {
        if (!super.canUse()) return false;
        LivingEntity attacker = this.mob.getLastHurtByMob();
        if (attacker instanceof ToukenDanshiEntity && this.mob instanceof ToukenDanshiEntity self) {
            UUID o1 = self.getOwnerUUID();
            UUID o2 = ((ToukenDanshiEntity) attacker).getOwnerUUID();
            if (o1 != null && o1.equals(o2)) return false;
        }
        return true;
    }

    @Override
    protected void alertOther(Mob ally, LivingEntity target) {
        if (target instanceof ToukenDanshiEntity targetTouken && ally instanceof ToukenDanshiEntity allyTouken) {
            UUID o1 = allyTouken.getOwnerUUID();
            UUID o2 = targetTouken.getOwnerUUID();
            if (o1 != null && o1.equals(o2)) return;
        }
        super.alertOther(ally, target);
    }
}