package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ToukenSparringGoal extends Goal {
    private final ToukenDanshiEntity entity;
    private int attackCooldown = 0;

    public ToukenSparringGoal(ToukenDanshiEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return entity.isSparring() && entity.getSparringPartner() != null;
    }

    @Override
    public boolean canContinueToUse() {
        ToukenDanshiEntity partner = entity.getSparringPartner();
        return entity.isSparring() && partner != null && partner.isAlive() && partner.isSparring();
    }

    @Override
    public void start() {
        attackCooldown = 0;
    }

    @Override
    public void stop() {
        entity.getNavigation().stop();
        if (entity.isSparring()) {
            entity.setSparring(false);
        }
    }

    @Override
    public void tick() {
        ToukenDanshiEntity partner = entity.getSparringPartner();
        if (partner == null || !partner.isAlive() || !partner.isSparring()) {
            entity.setSparring(false);
            return;
        }

        entity.getLookControl().setLookAt(partner, 30.0F, 30.0F);

        double distSqr = entity.distanceToSqr(partner);
        if (distSqr > 2.5 * 2.5) {
            entity.getNavigation().moveTo(partner, 1.0D);
        } else {
            entity.getNavigation().stop();
            if (attackCooldown-- <= 0) {
                attackCooldown = 20;
                entity.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
}
