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
        // 👈 只要还在手合状态，就不要停，除非实体死了。
        return entity.isSparring() && !entity.isDeadOrDying();
    }

    @Override
    public void start() {
        attackCooldown = 0;
    }

    @Override
    public void stop() {
        entity.getNavigation().stop();
        // 绝对不能在这里写 setSparring(false)，否则任何风吹草动都会导致手合提前结束！
    }

    @Override
    public void tick() {
        ToukenDanshiEntity partner = entity.getSparringPartner();
        // 如果对方不存在、死了、或者对方不是手合状态了，自己才跟着结束
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