package com.Equatorial.toukenranbu.entity.ai;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CaveClearanceGoal extends Goal {
    private final ToukenDanshiEntity entity;

    private BlockPos anchorPos;
    private BlockPos exploreTarget;
    private Vec3 lastCheckPos = Vec3.ZERO;
    private boolean returning = false;
    private boolean backtracking = false;
    private BlockPos backtrackTarget = null;

    private int recalcTicks = 0;
    private int stuckTicks = 0;
    private int exploreTimer = 0;
    private int pathSettleTicks = 0;

    private final List<Junction> junctionStack = new ArrayList<>();
    private final Set<BlockPos> exploredPositions = new HashSet<>();

    private static final int ANCHOR_RADIUS = 32;
    private static final int ANCHOR_RADIUS_SQ = 32 * 32;
    private static final int MAX_EXPLORE_TICKS = 1200;
    private static final int STUCK_THRESHOLD = 60; // 【改1】3秒没动就算卡住
    private static final int RECALC_INTERVAL = 20;
    private static final int PATH_SETTLE = 30;
    private static final double SPEED = 1.0D;
    private static final int MAX_PATH_NODES = 20;

    public CaveClearanceGoal(ToukenDanshiEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    private static class Junction {
        final BlockPos pos;
        final Set<BlockPos> triedTargets = new HashSet<>();

        Junction(BlockPos pos) {
            this.pos = pos.immutable();
        }
    }

    @Override
    public boolean canUse() {
        return entity.isCaveClearing()
                && entity.toukenData.fatigue > 0
                && !entity.isOrderedToSit()
                && !entity.isDeadOrDying();
    }

    @Override
    public boolean canContinueToUse() {
        return entity.isCaveClearing()
                && !entity.isOrderedToSit()
                && !entity.isDeadOrDying();
    }

    @Override
    public void start() {
        anchorPos = entity.blockPosition();
        returning = false;
        backtracking = false;
        backtrackTarget = null;
        exploreTimer = 0;
        stuckTicks = 0;
        recalcTicks = 0;
        pathSettleTicks = 0;
        exploreTarget = null;
        lastCheckPos = entity.position();
        junctionStack.clear();
        exploredPositions.clear();
    }

    @Override
    public void stop() {
        entity.setCaveClearing(false);
        exploreTarget = null;
        backtrackTarget = null;
        junctionStack.clear();
        exploredPositions.clear();
        if (!entity.level().isClientSide) {
            entity.getNavigation().stop();
        }
    }

    @Override
    public void tick() {
        if (!entity.isCaveClearing() || entity.isFollowing() || entity.isOrderedToSit()
                || entity.isFarming() || entity.isMining() || entity.isPatrolling() || entity.isSparring()) {
            stop();
            return;
        }

        ServerLevel level = (ServerLevel) entity.level();
        Player owner = entity.getOwner() instanceof Player ? (Player) entity.getOwner() : null;

        if (owner == null || !owner.isAlive()
                || entity.distanceToSqr(owner) > ANCHOR_RADIUS_SQ * 4) {
            returning = true;
        }

        if (++exploreTimer > MAX_EXPLORE_TICKS) {
            returning = true;
        }
        if (entity.toukenData.fatigue <= 0) {
            returning = true;
        }

        if (entity.getTarget() != null && entity.getTarget().isAlive()) {
            stuckTicks = 0;
            lastCheckPos = entity.position();
            return;
        }

        if (returning) {
            if (owner == null) {
                stop();
                return;
            }
            if (!entity.level().isClientSide) {
                ServerLevel ownerLevel = (ServerLevel) owner.level();
                Vec3 safePos = ToukenDanshiEntity.findSafePosNear(
                        ownerLevel, owner.getX(), owner.getY(), owner.getZ());

                if (ownerLevel.dimension() != entity.level().dimension()) {
                    Entity newEntity = entity.changeDimension(ownerLevel);
                    if (newEntity instanceof ToukenDanshiEntity newDanshi) {
                        newDanshi.teleportTo(safePos.x, safePos.y, safePos.z);
                        newDanshi.setFollowing(true);
                    }
                } else {
                    entity.teleportTo(safePos.x, safePos.y, safePos.z);
                    entity.setFollowing(true);
                }
            }
            stop();
            return;
        }

        Monster monster = findNearestMonster(level, 24);
        if (monster != null) {
            entity.setTarget(monster);
            entity.toukenData.fatigue = Math.max(0, entity.toukenData.fatigue - 1);
            entity.syncExtraData();
            return;
        }

        if (pathSettleTicks > 0) {
            pathSettleTicks--;
        }

        if (entity.tickCount % 10 == 0) {
            exploredPositions.add(entity.blockPosition().immutable());
        }

        boolean needNewTarget = (exploreTarget == null);

        // 【改2】被墙挡住：拉黑墙后一大片，让主逻辑重选/回溯，绝不乱走
        if (!needNewTarget && pathSettleTicks <= 0) {
            if (entity.getNavigation().isDone()) {
                double dist = entity.distanceToSqr(
                        exploreTarget.getX() + 0.5, exploreTarget.getY(), exploreTarget.getZ() + 0.5);
                if (dist > 9.0D) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                exploredPositions.add(exploreTarget.offset(dx, dy, dz).immutable());
                            }
                        }
                    }
                    exploreTarget = null;
                    needNewTarget = true;
                } else {
                    exploredPositions.add(exploreTarget.immutable());
                    checkAndRecordJunction(level, exploreTarget);
                    needNewTarget = true;
                }
            }
        }

        if (!needNewTarget && backtracking && backtrackTarget != null) {
            if (entity.distanceToSqr(
                    backtrackTarget.getX() + 0.5, backtrackTarget.getY(), backtrackTarget.getZ() + 0.5) < 9.0D) {
                backtracking = false;
                backtrackTarget = null;
                needNewTarget = true;
            }
        }

        if (!needNewTarget && recalcTicks-- <= 0) {
            recalcTicks = RECALC_INTERVAL;
            if (entity.getNavigation().getPath() == null) {
                needNewTarget = true;
            }
        }

        if (needNewTarget) {
            if (backtracking && backtrackTarget != null) {
                exploreTarget = backtrackTarget;
                pathSettleTicks = PATH_SETTLE;
                recalcTicks = RECALC_INTERVAL;
                entity.getNavigation().moveTo(
                        backtrackTarget.getX() + 0.5, backtrackTarget.getY(), backtrackTarget.getZ() + 0.5, SPEED);
            } else {
                BlockPos next = findNextExplorePoint(level);
                if (next != null) {
                    exploreTarget = next;
                    stuckTicks = 0;
                    pathSettleTicks = PATH_SETTLE;
                    recalcTicks = RECALC_INTERVAL;
                    entity.getNavigation().moveTo(
                            next.getX() + 0.5, next.getY(), next.getZ() + 0.5, SPEED);
                } else {
                    if (!tryBacktrack(level)) {
                        returning = true;
                    }
                }
            }
        }

        if (entity.tickCount % 20 == 0) {
            Vec3 currentPos = entity.position();
            if (currentPos.distanceToSqr(lastCheckPos) < 0.04D) {
                stuckTicks++;
            } else {
                stuckTicks = 0;
            }
            lastCheckPos = currentPos;
        }

        // 【改3】卡住直接瞬移回家，绝不乱转
        if (stuckTicks > STUCK_THRESHOLD) {
            exploreTarget = null;
            stuckTicks = 0;
            pathSettleTicks = 0;
            recalcTicks = 0;
            entity.getNavigation().stop();
            returning = true;
        }

        if (anchorPos != null && entity.distanceToSqr(
                anchorPos.getX() + 0.5, anchorPos.getY(), anchorPos.getZ() + 0.5) > ANCHOR_RADIUS_SQ) {
            returning = true;
        }
    }

    private Monster findNearestMonster(ServerLevel level, int radius) {
        Monster best = null;
        double bestDist = Double.MAX_VALUE;
        AABB box = entity.getBoundingBox().inflate(radius);
        for (Monster mob : level.getEntitiesOfClass(Monster.class, box)) {
            if (!mob.isAlive()) continue;
            double d = entity.distanceToSqr(mob);
            if (d < bestDist && d <= ANCHOR_RADIUS_SQ && entity.hasLineOfSight(mob)) {
                bestDist = d;
                best = mob;
            }
        }
        return best;
    }

    private void checkAndRecordJunction(ServerLevel level, BlockPos atPos) {
        List<BlockPos> directions = findValidDirections(level, atPos);
        if (directions.size() >= 2) {
            for (Junction j : junctionStack) {
                if (j.pos.equals(atPos)) return;
            }
            junctionStack.add(new Junction(atPos));
        }
    }

    private List<BlockPos> findValidDirections(ServerLevel level, BlockPos center) {
        List<BlockPos> result = new ArrayList<>();
        for (int dist = 4; dist <= 12; dist += 2) {
            for (int angle = 0; angle < 360; angle += 30) {
                double rad = Math.toRadians(angle);
                int dx = (int) Math.round(Math.sin(rad) * dist);
                int dz = (int) Math.round(Math.cos(rad) * dist);
                for (int dy = -3; dy <= 3; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (isValidDarkPos(level, pos) && !exploredPositions.contains(pos)) {
                        var path = entity.getNavigation().createPath(pos, 0);
                        if (path != null && path.getNodeCount() <= MAX_PATH_NODES) {
                            result.add(pos);
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    private BlockPos findNextExplorePoint(ServerLevel level) {
        BlockPos center = entity.blockPosition();
        List<ScoredPos> candidates = new ArrayList<>();
        double yawRad = Math.toRadians(entity.getYRot());

        for (int dist = 4; dist <= 14; dist += 2) {
            for (int angle = -6; angle <= 6; angle++) {
                double rad = Math.toRadians(angle * 7.5) + yawRad;
                int dx = (int) Math.round(Math.sin(rad) * dist);
                int dz = (int) Math.round(Math.cos(rad) * dist);
                for (int dy = -5; dy <= 5; dy++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    if (pos.equals(center)) continue;
                    if (!isValidDarkPos(level, pos)) continue;
                    if (exploredPositions.contains(pos)) continue;

                    var path = entity.getNavigation().createPath(pos, 0);
                    if (path == null) continue;
                    if (path.getNodeCount() > MAX_PATH_NODES) continue;

                    int light = level.getLightEngine().getRawBrightness(pos, 0);
                    double distance = entity.distanceToSqr(
                            pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    double score = light * 10 + distance * 0.3 + Math.abs(angle) * 3;
                    candidates.add(new ScoredPos(pos, score));
                }
            }
        }

        if (candidates.isEmpty()) return null;
        candidates.sort((a, b) -> Double.compare(a.score, b.score));
        BlockPos chosen = candidates.get(0).pos;

        for (int i = junctionStack.size() - 1; i >= 0; i--) {
            Junction j = junctionStack.get(i);
            if (j.pos.distSqr(entity.blockPosition()) <= 16) {
                j.triedTargets.add(chosen.immutable());
                break;
            }
        }

        return chosen;
    }

    private boolean tryBacktrack(ServerLevel level) {
        while (!junctionStack.isEmpty()) {
            Junction j = junctionStack.get(junctionStack.size() - 1);
            List<BlockPos> remaining = findRemainingDirections(level, j);
            if (!remaining.isEmpty()) {
                backtracking = true;
                backtrackTarget = j.pos;
                exploreTarget = j.pos;
                entity.getNavigation().moveTo(
                        j.pos.getX() + 0.5, j.pos.getY(), j.pos.getZ() + 0.5, SPEED);
                pathSettleTicks = PATH_SETTLE;
                recalcTicks = RECALC_INTERVAL;
                stuckTicks = 0;
                return true;
            } else {
                junctionStack.remove(junctionStack.size() - 1);
            }
        }
        return false;
    }

    private List<BlockPos> findRemainingDirections(ServerLevel level, Junction j) {
        List<BlockPos> result = new ArrayList<>();
        for (int dist = 4; dist <= 12; dist += 2) {
            for (int angle = 0; angle < 360; angle += 30) {
                double rad = Math.toRadians(angle);
                int dx = (int) Math.round(Math.sin(rad) * dist);
                int dz = (int) Math.round(Math.cos(rad) * dist);
                for (int dy = -3; dy <= 3; dy++) {
                    BlockPos pos = j.pos.offset(dx, dy, dz);
                    if (!isValidDarkPos(level, pos)) continue;
                    if (exploredPositions.contains(pos)) continue;
                    if (j.triedTargets.contains(pos)) continue;

                    var path = entity.getNavigation().createPath(pos, 0);
                    if (path != null && path.getNodeCount() <= MAX_PATH_NODES) {
                        result.add(pos);
                        break;
                    }
                }
            }
        }
        return result;
    }

    private boolean isValidDarkPos(ServerLevel level, BlockPos pos) {
        if (level.getLightEngine().getRawBrightness(pos, 0) >= 7) return false;
        if (!level.isEmptyBlock(pos) || !level.isEmptyBlock(pos.above())) return false;
        BlockState below = level.getBlockState(pos.below());
        if (below.isAir()) return false;
        if (level.getFluidState(pos.below()).is(FluidTags.LAVA)) return false;
        if (level.getFluidState(pos).is(FluidTags.LAVA)) return false;
        return true;
    }

    private static class ScoredPos {
        final BlockPos pos;
        final double score;
        ScoredPos(BlockPos pos, double score) {
            this.pos = pos;
            this.score = score;
        }
    }
}