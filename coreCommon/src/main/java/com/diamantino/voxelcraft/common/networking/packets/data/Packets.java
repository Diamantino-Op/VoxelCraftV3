package com.diamantino.voxelcraft.common.networking.packets.data;

import com.diamantino.voxelcraft.client.networking.ClientInstance;
import com.diamantino.voxelcraft.common.networking.packets.c2s.RequestChunkPacket;
import com.diamantino.voxelcraft.common.networking.packets.s2c.ChunkSyncPacket;
import com.diamantino.voxelcraft.common.networking.packets.s2c.SyncPropertyPacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.utils.ResourceLocation;
import com.diamantino.voxelcraft.common.utils.Side;
import com.diamantino.voxelcraft.server.VoxelCraftServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for registering and managing the packets used in the networking system.
 *
 * @author Diamantino
 */
public class Packets {
    /**
     * Packet registry.
     * Key: Packet ID.
     * Value: Packet class.
     */
    public static final Map<ResourceLocation, Class<? extends BasePacket>> registeredPackets = new HashMap<>();

    /**
     * Packet registry.
     * Key: Packet class.
     * Value: Packet ID.
     */
    public static final Map<Class<? extends BasePacket>, ResourceLocation> packetIDs = new HashMap<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private Packets() {}

    /**
     * Register a packet.
     *
     * @param location The ID of the packet.
     * @param side The side of the packet (Client or Server).
     * @param packet Packet class.
     */
    public static void registerPacket(ResourceLocation location, Side side, Class<? extends BasePacket> packet) {
        String tmpLocation = location.location() + (side == Side.CLIENT ? "_client" : "_server");
        ResourceLocation newLocation = new ResourceLocation(location.modId(), tmpLocation);

        registeredPackets.put(newLocation, packet);
        packetIDs.put(packet, newLocation);
    }

    /**
     * Register all packets.
     */
    public static void registerPackets() {
        // S2C
        registerPacket(ChunkSyncPacket.class);
        registerPacket(SyncPropertyPacket.class);

        // C2S
        registerPacket(RequestChunkPacket.class);
    }
}
