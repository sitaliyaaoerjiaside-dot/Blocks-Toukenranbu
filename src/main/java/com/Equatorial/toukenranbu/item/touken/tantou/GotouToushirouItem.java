package com.Equatorial.toukenranbu.item.touken.tantou;

import com.Equatorial.toukenranbu.capability.ModCapabilities;
import com.Equatorial.toukenranbu.entity.ModEntityTypes;
import com.Equatorial.toukenranbu.entity.touken.tantou.GotouToushirouEntity;
import com.Equatorial.toukenranbu.network.SpiritPowerSyncPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class GotouToushirouItem extends Item {

    public GotouToushirouItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide && context.getPlayer() != null) {

            ServerPlayer player = (ServerPlayer) context.getPlayer();
            if (!player.isCreative()) {
                boolean hasEnough = player.getCapability(ModCapabilities.SPIRIT_POWER).map(cap -> {
                    if (cap.consumeSpiritPower(30)) {
                        SpiritPowerSyncPacket.sendToPlayer(player);
                        return true;
                    }
                    return false;
                }).orElse(false);

                if (!hasEnough) {
                    player.sendSystemMessage(
                            Component.translatable("message.toukenranbu_mod.insufficient_spirit")
                    );
                    return InteractionResult.FAIL;
                }
            }

            GotouToushirouEntity danshi = new GotouToushirouEntity(ModEntityTypes.GOTOU_TOUSHIROU.get(), level);
            danshi.setTame(true);
            danshi.setOwnerUUID(context.getPlayer().getUUID());

            var pos = context.getClickedPos().above().getCenter();
            double ex = pos.x;
            double ey = pos.y;
            double ez = pos.z;

            double dx = context.getPlayer().getX() - ex;
            double dz = context.getPlayer().getZ() - ez;
            float faceYaw = (float) (Math.toDegrees(Math.atan2(dz, dx))) - 90.0F;

            danshi.moveTo(ex, ey, ez, faceYaw, 0);
            danshi.yBodyRot = faceYaw;
            danshi.yHeadRot = faceYaw;
            danshi.yRotO = faceYaw;
            danshi.yBodyRotO = faceYaw;
            danshi.yHeadRotO = faceYaw;

            level.addFreshEntity(danshi);

            if (!context.getPlayer().isCreative()) {
                context.getItemInHand().shrink(1);
            }

            context.getPlayer().sendSystemMessage(
                    Component.translatable("message.toukenranbu_mod.summon_gotou_toushirou")
            );
        }
        return InteractionResult.SUCCESS;
    }
}