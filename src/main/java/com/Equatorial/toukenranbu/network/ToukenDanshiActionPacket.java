package com.Equatorial.toukenranbu.network;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToukenDanshiActionPacket {
    private final int entityId;
    private final byte action;

    public ToukenDanshiActionPacket(int entityId, byte action) {
        this.entityId = entityId;
        this.action = action;
    }

    public static void encode(ToukenDanshiActionPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeByte(msg.action);
    }

    public static ToukenDanshiActionPacket decode(FriendlyByteBuf buf) {
        return new ToukenDanshiActionPacket(buf.readInt(), buf.readByte());
    }

    public static void handle(ToukenDanshiActionPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            Entity entity = player.level().getEntity(msg.entityId);
            if (entity instanceof ToukenDanshiEntity touken && touken.isOwnedBy(player)) {
                switch (msg.action) {
                    case 0 -> {
                        boolean newSit = !touken.isOrderedToSit();
                        touken.setOrderedToSit(newSit);
                    }
                    case 1 -> {
                        boolean newFollow = !touken.isFollowing();
                        touken.setFollowing(newFollow);
                    }
                    case 2 -> {
                        boolean newFarm = !touken.isFarming();
                        if (newFarm && !touken.hasSeeds()) {
                            return;
                        }
                        touken.setFarming(newFarm);
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static void send(int entityId, int action) {
        ModNetwork.CHANNEL.sendToServer(new ToukenDanshiActionPacket(entityId, (byte) action));
    }
}