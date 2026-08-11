package com.Equatorial.toukenranbu.item.custom;

import com.Equatorial.toukenranbu.dimension.AbandonedHistoryTeleporter;
import com.Equatorial.toukenranbu.world.registry.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DiceItem extends Item {

    public DiceItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (!level.isClientSide) {
            ServerPlayer player = (ServerPlayer) context.getPlayer();

            if (player.isPassenger()) {
                player.displayClientMessage(Component.literal("请先下坐骑再使用骰子。"), true);
                return InteractionResult.FAIL;
            }

            if (player.level().dimension() == Level.OVERWORLD) {
                saveReturnPos(player);
                teleportToAbandonedHistory(player, player.getOnPos());
            } else if (player.level().dimension() == ModDimensions.ABANDONED_HISTORY_LEVEL) {
                Vec3 returnPos = loadReturnPos(player);
                teleportToOverworld(player, returnPos);
                clearReturnPos(player);
            } else {
                player.displayClientMessage(Component.literal("无法从这个维度传送。"), true);
                return InteractionResult.FAIL;
            }

            player.getCooldowns().addCooldown(this, 20);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    private void saveReturnPos(ServerPlayer player) {
        CompoundTag tag = player.getPersistentData();
        tag.putDouble("return_x", player.getX());
        tag.putDouble("return_y", player.getY());
        tag.putDouble("return_z", player.getZ());
    }

    private Vec3 loadReturnPos(ServerPlayer player) {
        CompoundTag tag = player.getPersistentData();
        if (tag.contains("return_x")) {
            return new Vec3(tag.getDouble("return_x"), tag.getDouble("return_y"), tag.getDouble("return_z"));
        }
        return player.position();
    }

    private void clearReturnPos(ServerPlayer player) {
        CompoundTag tag = player.getPersistentData();
        tag.remove("return_x");
        tag.remove("return_y");
        tag.remove("return_z");
    }

    private void teleportToAbandonedHistory(ServerPlayer player, BlockPos pos) {
        ServerLevel abandonedHistory = player.getServer().getLevel(ModDimensions.ABANDONED_HISTORY_LEVEL);
        if (abandonedHistory != null) {
            player.changeDimension(abandonedHistory, new AbandonedHistoryTeleporter(pos));
        } else {
            player.displayClientMessage(Component.literal("废弃历史维度未找到。"), true);
        }
    }

    private void teleportToOverworld(ServerPlayer player, Vec3 pos) {
        ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
        if (overworld != null) {
            player.changeDimension(overworld, new AbandonedHistoryTeleporter(pos));
        } else {
            player.displayClientMessage(Component.literal("主世界未找到。"), true);
        }
    }
}