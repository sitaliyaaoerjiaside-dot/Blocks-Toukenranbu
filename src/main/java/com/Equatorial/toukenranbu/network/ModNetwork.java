package com.Equatorial.toukenranbu.network;

import com.Equatorial.toukenranbu.ToukenRanbuMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetwork {
    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(ToukenRanbuMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        // 你原来就有的
        CHANNEL.registerMessage(
                packetId++,
                SpiritPowerSyncPacket.class,
                SpiritPowerSyncPacket::encode,
                SpiritPowerSyncPacket::decode,
                SpiritPowerSyncPacket::handle
        );

        // ===== 新增：刀剑男士按钮动作包 =====
        CHANNEL.registerMessage(
                packetId++,
                ToukenDanshiActionPacket.class,
                ToukenDanshiActionPacket::encode,
                ToukenDanshiActionPacket::decode,
                ToukenDanshiActionPacket::handle
        );
    }
}