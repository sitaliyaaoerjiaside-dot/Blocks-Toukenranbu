package com.Equatorial.toukenranbu.dimension;

import com.Equatorial.toukenranbu.world.registry.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class AbandonedHistoryTeleporter implements ITeleporter {
    private BlockPos blockPos;
    private Vec3 exactPos;

    public AbandonedHistoryTeleporter(BlockPos pos) {
        this.blockPos = pos;
        this.exactPos = null;
    }

    public AbandonedHistoryTeleporter(Vec3 pos) {
        this.exactPos = pos;
        this.blockPos = null;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity e = repositionEntity.apply(false);
        if (!(e instanceof ServerPlayer player)) {
            return e;
        }

        if (exactPos != null) {
            player.teleportTo(exactPos.x, exactPos.y, exactPos.z);
            return e;
        }

        LevelChunk chunk = (LevelChunk) destWorld.getChunk(blockPos);
        Vec3 spawnPos;

        if (destWorld.dimension().equals(ModDimensions.ABANDONED_HISTORY_LEVEL)) {
            spawnPos = findSpawnInDimension(destWorld, chunk);
        } else {
            spawnPos = findSpawnInOverworld(destWorld, chunk);
        }

        if (spawnPos != null) {
            player.teleportTo(spawnPos.x(), spawnPos.y(), spawnPos.z());
        }

        return e;
    }

    private Vec3 findSpawnInDimension(ServerLevel world, LevelChunk chunk) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        int minY = world.getMinBuildHeight();
        int maxY = world.getMaxBuildHeight() - 10;

        for (int y = maxY - 1; y >= minY; y--) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    mutablePos.set(x, y, z);
                    BlockPos feetPos = chunk.getPos().getWorldPosition().offset(x, y, z);
                    BlockPos below = feetPos.below();

                    if (!world.getBlockState(below).isAir()
                            && world.getBlockState(feetPos).isAir()
                            && world.getBlockState(feetPos.above()).isAir()
                            && world.getBlockState(feetPos.above(2)).isAir()) {
                        return Vec3.atBottomCenterOf(feetPos);
                    }
                }
            }
        }

        for (int y = maxY - 1; y >= minY; y--) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    mutablePos.set(x, y, z);
                    BlockPos base = chunk.getPos().getWorldPosition().offset(x, y, z);

                    if (isReplaceable(world, base)
                            && isReplaceable(world, base.above(1))
                            && isReplaceable(world, base.above(2))) {
                        clearAndSeal(world, base);
                        return Vec3.atBottomCenterOf(base.above());
                    }
                }
            }
        }

        return null;
    }

    private Vec3 findSpawnInOverworld(ServerLevel world, LevelChunk chunk) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = world.getMaxBuildHeight() - 1; y >= world.getMinBuildHeight(); y--) {
                    mutablePos.set(x, y, z);
                    if (isAir(chunk, mutablePos) && isAir(chunk, mutablePos.above(1)) && isAir(chunk, mutablePos.above(2))
                            && !chunk.getBlockState(mutablePos.below()).isAir()) {
                        BlockPos absolute = chunk.getPos().getWorldPosition().offset(mutablePos.getX(), mutablePos.getY(), mutablePos.getZ());
                        return Vec3.atBottomCenterOf(absolute.above());
                    }
                }
            }
        }
        return null;
    }

    private boolean isAir(LevelChunk chunk, BlockPos pos) {
        return chunk.getBlockState(pos).isAir();
    }

    private boolean isReplaceable(Level world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isAir()
                || state.is(Blocks.STONE)
                || state.is(Blocks.GRANITE)
                || state.is(Blocks.ANDESITE)
                || state.is(Blocks.DIORITE)
                || state.is(Blocks.DIRT)
                || state.is(Blocks.GRAVEL)
                || state.is(Blocks.LAVA);
    }

    private void clearAndSeal(ServerLevel world, BlockPos base) {
        world.setBlockAndUpdate(base.above(1), Blocks.AIR.defaultBlockState());
        world.setBlockAndUpdate(base.above(2), Blocks.AIR.defaultBlockState());

        for (Direction dir : Direction.Plane.HORIZONTAL) {
            world.setBlockAndUpdate(base.above(1).relative(dir), Blocks.STONE.defaultBlockState());
            world.setBlockAndUpdate(base.above(2).relative(dir), Blocks.STONE.defaultBlockState());
        }
        world.setBlockAndUpdate(base.above(3), Blocks.STONE.defaultBlockState());
    }
}