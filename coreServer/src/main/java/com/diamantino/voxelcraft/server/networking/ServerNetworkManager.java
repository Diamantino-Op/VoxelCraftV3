package com.diamantino.voxelcraft.server.networking;

import com.diamantino.voxelcraft.common.networking.packets.Packets;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.utils.ResourceLocation;
import com.diamantino.voxelcraft.common.utils.Side;
import com.diamantino.voxelcraft.server.VoxelCraftServer;
import com.diamantino.voxelcraft.server.networking.c2s.ReceiveRequestChunkPacket;
import com.diamantino.voxelcraft.server.networking.s2c.SendChunkSyncPacket;
import com.diamantino.voxelcraft.server.networking.s2c.SendSyncPropertyPacket;
import com.diamantino.voxelcraft.server.networking.s2c.SendWorldSettingsPacket;
import com.github.terefang.ncs.common.*;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import com.github.terefang.ncs.server.NcsServerHelper;
import com.github.terefang.ncs.server.NcsServerService;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the server network.
 *
 * @author Diamantino
 */
public class ServerNetworkManager implements NcsPacketListener<SimpleBytesNcsPacket>, NcsStateListener, NcsKeepAliveFailListener {
    /**
     * Stores all connected clients.
     * Key: Client name.
     * Value: ConnectedClient instance.
     */
    public final Map<String, ConnectedClient> connectedClients = new HashMap<>();

    /**
     * Stores the server IP.
     */
    @Getter
    private final String ip;

    /**
     * Stores the server port.
     */
    @Getter
    private final int port;

    /**
     * Stores the server instance.
     */
    private final NcsServerService server;

    /**
     * Creates a new server network manager.
     *
     * @param ip Server IP.
     * @param port Server port.
     */
    public ServerNetworkManager(String ip, int port) {
        this.ip = ip;
        this.port = port;

        this.server = NcsServerHelper.createSimpleServer(ip, port, this, this);
    }

    /**
     * Initializes the server network manager.
     */
    public void initManager() {
        registerPackets();

        server.getConfiguration().setUseEpoll(true);
        server.getConfiguration().setClientKeepAliveCounterMax(10);
        server.getConfiguration().setClientKeepAliveTimeout(1000);
        server.getConfiguration().setClientKeepAliveTcpAutoDisconnect(true);
        server.getConfiguration().setTcpNoDelay(true);

        //TODO: Ip ban
        //server.getConfiguration().banAddress();

        server.start();
    }

    /**
     * Stops the server network manager.
     */
    public void stopManager() {
        server.stop();
    }

    /**
     * Registers all packets.
     */
    private void registerPackets() {
        // Client to Server
        Packets.registerPacket(new ResourceLocation("request_chunk"), Side.SERVER, ReceiveRequestChunkPacket.class);

        // Server to Client
        Packets.registerPacket(new ResourceLocation("chunk_sync"), Side.SERVER, SendChunkSyncPacket.class);
        Packets.registerPacket(new ResourceLocation("sync_property"), Side.SERVER, SendSyncPropertyPacket.class);
        Packets.registerPacket(new ResourceLocation("world_settings"), Side.SERVER, SendWorldSettingsPacket.class);
    }

    /**
     * Called when the keep alive fails.
     *
     * @param connection The client connection.
     * @param timeout The timeout.
     * @param fails The amount fails.
     * @param endpoint The client endpoint.
     */
    @Override
    public void onKeepAliveFail(NcsConnection connection, long timeout, long fails, NcsEndpoint endpoint) {
        connection.getContext(ServerHandler.class).onKeepAliveFail(this, connection, timeout, fails, endpoint);
    }

    /**
     * Called when a packet is received.
     *
     * @param connection The client connection.
     * @param packet The packet.
     */
    @Override
    public void onPacket(NcsConnection connection, SimpleBytesNcsPacket packet) {
        connection.getContext(ServerHandler.class).onPacket(this, connection, packet);
    }

    /**
     * Called when a connection is established.
     *
     * @param connection The client connection.
     */
    @Override
    public void onConnect(NcsConnection connection) {
        connection.getContext(ServerHandler.class).onConnect(this, connection);
    }

    /**
     * Called when a connection is disconnected.
     *
     * @param connection The client connection.
     */
    @Override
    public void onDisconnect(NcsConnection connection) {
        connection.getContext(ServerHandler.class).onDisconnect(this, connection);
    }

    /**
     * Called when an error occurs.
     *
     * @param connection The client connection.
     * @param throwable The error.
     */
    @Override
    public void onError(NcsConnection connection, Throwable throwable) {
        connection.getContext(ServerHandler.class).onError(this, connection, throwable);
    }

    /**
     * Send a packet to a player.
     *
     * @param connectionName Connection name.
     * @param packet Packet to send.
     */
    public static void sendToPlayer(String connectionName, BasePacket packet) {
        VoxelCraftServer.getInstance().networkManager.connectedClients.get(connectionName).sendPacket(packet);
    }

    /**
     * Send a packet to all players.
     *
     * @param packet Packet to send.
     */
    public static void sendToAllPlayers(BasePacket packet) {
        VoxelCraftServer.getInstance().networkManager.connectedClients.values().forEach(client -> client.sendPacket(packet));
    }
}
