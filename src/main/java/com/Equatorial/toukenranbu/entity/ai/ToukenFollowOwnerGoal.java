package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class ToukenFollowOwnerGoal extends FollowOwnerGoal {
    private final ToukenDanshiEntity danshi;

    public ToukenFollowOwnerGoal(ToukenDanshiEntity danshi, double speed, float minDist, float maxDist, boolean canFly) {
        super(danshi, speed, minDist, maxDist, canFly);
        this.danshi = danshi;
    }

    @Override
    public boolean canUse() {
        return this.danshi.isFollowing() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.danshi.isFollowing() && super.canContinueToUse();
    }
}