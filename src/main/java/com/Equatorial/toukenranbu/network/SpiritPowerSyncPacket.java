// src/main/java/com/Equatorial/toukenranbu/network/SpiritPowerSyncPacket.java
package com.Equatorial.toukenranbu.network;

import com.Equatorial.toukenranbu.capability.ModCapabilities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class SpiritPowerSyncPacket {
    private final int spiritPower;
    private final int maxSpiritPower;

    public SpiritPowerSyncPacket(int spiritPower, int maxSpiritPower) {
        this.spiritPower = spiritPower;
        this.maxSpiritPower = maxSpiritPower;
    }

    public static void encode(SpiritPowerSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.spiritPower);
        buf.writeInt(msg.maxSpiritPower);
    }

    public static SpiritPowerSyncPacket decode(FriendlyByteBuf buf) {
        return new SpiritPowerSyncPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(SpiritPowerSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isClient()) {
                ClientSpiritPowerData.set(msg.spiritPower, msg.maxSpiritPower);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static void sendToPlayer(ServerPlayer player) {
        player.getCapability(ModCapabilities.SPIRIT_POWER).ifPresent(cap -> {
            ModNetwork.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new SpiritPowerSyncPacket(cap.getSpiritPower(), cap.getMaxSpiritPower())
            );
        });
    }
}