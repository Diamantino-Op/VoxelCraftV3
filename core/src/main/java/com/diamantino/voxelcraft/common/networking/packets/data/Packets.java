package com.diamantino.voxelcraft.common.networking.packets.data;

import com.diamantino.voxelcraft.common.networking.packets.s2c.ChunkSyncPacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;

import java.util.HashMap;

public class Packets {
    public static final HashMap<Integer, Class<? extends BasePacket>> packets = new HashMap<>();
    public static final HashMap<Class<? extends BasePacket>, Integer> packetIDs = new HashMap<>();

    private static int id = 0;

    private static void registerPacket(Class<? extends BasePacket> packet) {
        int tmpId = id++;
        packets.put(tmpId, packet);
        packetIDs.put(packet, tmpId);
    }

    public static void registerPackets() {
        // S2C
        registerPacket(ChunkSyncPacket.class);

        // C2S
    }
}
