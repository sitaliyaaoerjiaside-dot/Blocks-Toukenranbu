package com.Equatorial.toukenranbu.network;

import com.Equatorial.toukenranbu.entity.touken.ToukenDanshiEntity;
import com.Equatorial.toukenranbu.touken.FormationType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FormationChangePacket {
    private final int entityId;
    private final String formationName;

    public FormationChangePacket(int entityId, String formationName) {
        this.entityId = entityId;
        this.formationName = formationName;
    }

    public static void encode(FormationChangePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.entityId);
        buf.writeUtf(msg.formationName);
    }

    public static FormationChangePacket decode(FriendlyByteBuf buf) {
        return new FormationChangePacket(buf.readInt(), buf.readUtf());
    }

    public static void handle(FormationChangePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            Entity entity = player.level().getEntity(msg.entityId);
            if (!(entity instanceof ToukenDanshiEntity target)) return;
            if (!target.isOwnedBy(player)) return;

            FormationType newType;
            try {
                newType = FormationType.valueOf(msg.formationName);
            } catch (IllegalArgumentException e) {
                return;
            }

            // 32格内同主人刀男全部同步为同一阵型
            AABB box = target.getBoundingBox().inflate(32.0);
            for (ToukenDanshiEntity other : player.level().getEntitiesOfClass(ToukenDanshiEntity.class, box)) {
                if (other.isOwnedBy(player)) {
                    other.setFormationType(newType);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static void send(int entityId, FormationType type) {
        ModNetwork.CHANNEL.sendToServer(new FormationChangePacket(entityId, type.name()));
    }
}