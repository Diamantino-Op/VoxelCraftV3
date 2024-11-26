package com.diamantino.voxelcraft.common.networking.packets.data;

import com.diamantino.voxelcraft.client.networking.ClientInstance;
import com.diamantino.voxelcraft.common.networking.packets.c2s.RequestChunkPacket;
import com.diamantino.voxelcraft.common.networking.packets.s2c.ChunkSyncPacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.server.ServerInstance;

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
    public static final Map<Integer, Class<? extends BasePacket>> registeredPackets = new HashMap<>();

    /**
     * Packet registry.
     * Key: Packet class.
     * Value: Packet ID.
     */
    public static final Map<Class<? extends BasePacket>, Integer> packetIDs = new HashMap<>();

    /**
     * Packet ID counter.
     */
    private static int id = 0;

    /**
     * Private constructor to prevent instantiation.
     */
    private Packets() {}

    /**
     * Register a packet.
     *
     * @param packet Packet class.
     */
    private static void registerPacket(Class<? extends BasePacket> packet) {
        int tmpId = id++;
        registeredPackets.put(tmpId, packet);
        packetIDs.put(packet, tmpId);
    }

    /**
     * Register all packets.
     */
    public static void registerPackets() {
        // S2C
        registerPacket(ChunkSyncPacket.class);

        // C2S
        registerPacket(RequestChunkPacket.class);
    }

    /**
     * Send a packet to the server.
     *
     * @param packet Packet to send.
     */
    public static void sendToServer(BasePacket packet) {
        ClientInstance.instance.serverConnection.writeAndFlush(packet);
    }

    /**
     * Send a packet to a player.
     *
     * @param playerName Player name.
     * @param packet Packet to send.
     */
    public static void sendToPlayer(String playerName, BasePacket packet) {
        ServerInstance.instance.connectedClients.get(playerName).sendPacket(packet);
    }

    /**
     * Send a packet to all players.
     *
     * @param packet Packet to send.
     */
    public static void sendToAllPlayers(BasePacket packet) {
        ServerInstance.instance.connectedClients.values().forEach(client -> client.sendPacket(packet));
    }
}
