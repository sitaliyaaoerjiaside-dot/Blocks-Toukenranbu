package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ToukenPatrolGoal extends Goal {
    private final ToukenDanshiEntity entity;
    private final double speed = 1.0;
    private int recalcTicks = 0;
    private int stuckTicks = 0;
    private BlockPos lastPos = BlockPos.ZERO;

    public ToukenPatrolGoal(ToukenDanshiEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return entity.isPatrolling()
                && entity.getTarget() == null
                && entity.getPatrolCenter() != null
                && !entity.isOrderedToSit()
                && !entity.isFarming()
                && !entity.isMining()
                && !entity.isFollowing();
    }

    @Override
    public boolean canContinueToUse() {
        return canUse();
    }

    @Override
    public void start() {
        recalcTicks = 0;
        stuckTicks = 0;
    }

    @Override
    public void stop() {
        entity.getNavigation().stop();
    }

    @Override
    public void tick() {
        BlockPos center = entity.getPatrolCenter();
        if (center == null) return;

        double distToCenter = entity.distanceToSqr(center.getX() + 0.5, center.getY(), center.getZ() + 0.5);

        if (distToCenter > 16 * 16) {
            entity.getNavigation().moveTo(center.getX() + 0.5, center.getY(), center.getZ() + 0.5, speed * 1.5);
            return;
        }

        if (entity.tickCount % 20 == 0) {
            if (entity.blockPosition().equals(lastPos)) {
                stuckTicks++;
            } else {
                stuckTicks = 0;
            }
            lastPos = entity.blockPosition();
        }

        if (recalcTicks-- <= 0 || entity.getNavigation().isDone() || stuckTicks > 3) {
            recalcTicks = 30 + entity.getRandom().nextInt(30);
            stuckTicks = 0;
            BlockPos target = findPatrolPoint(center);
            if (target != null) {
                entity.getNavigation().moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, speed);
            }
        }
    }

    private BlockPos findPatrolPoint(BlockPos center) {
        for (int i = 0; i < 10; i++) {
            int x = center.getX() + entity.getRandom().nextInt(17) - 8;
            int z = center.getZ() + entity.getRandom().nextInt(17) - 8;
            int y = center.getY();

            BlockPos pos = new BlockPos(x, y, z);
            for (int dy = -3; dy <= 3; dy++) {
                BlockPos check = pos.offset(0, dy, 0);
                if (isValidStandPosition(check)) {
                    return check;
                }
            }
        }
        return center;
    }

    private boolean isValidStandPosition(BlockPos pos) {
        return entity.level().getBlockState(pos).isAir()
                && entity.level().getBlockState(pos.above()).isAir()
                && !entity.level().getBlockState(pos.below()).isAir();
    }
}