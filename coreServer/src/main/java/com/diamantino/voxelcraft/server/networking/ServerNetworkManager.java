package com.diamantino.voxelcraft.server.networking;

import com.diamantino.voxelcraft.common.networking.ConnectedClient;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.server.VoxelCraftServer;
import com.github.terefang.ncs.common.*;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import com.github.terefang.ncs.server.NcsServerHelper;
import com.github.terefang.ncs.server.NcsServerService;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

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

    public ServerNetworkManager(String ip, int port) {
        this.ip = ip;
        this.port = port;

        this.server = NcsServerHelper.createSimpleServer(ip, port, this, this);
    }

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

    public void stopManager() {
        server.stop();
    }

    private void registerPackets() {
        //Packets.registerPacket();
    }

    @Override
    public void onKeepAliveFail(NcsConnection connection, long timeout, long fails, NcsEndpoint endpoint) {
        connection.getContext(ServerHandler.class).onKeepAliveFail(this, connection, timeout, fails, endpoint);
    }

    @Override
    public void onPacket(NcsConnection connection, SimpleBytesNcsPacket packet) {
        connection.getContext(ServerHandler.class).onPacket(this, connection, packet);
    }

    @Override
    public void onConnect(NcsConnection connection) {
        connection.getContext(ServerHandler.class).onConnect(this, connection);
    }

    @Override
    public void onDisconnect(NcsConnection connection) {
        connection.getContext(ServerHandler.class).onDisconnect(this, connection);
    }

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
