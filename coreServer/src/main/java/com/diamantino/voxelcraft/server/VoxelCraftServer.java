package com.diamantino.voxelcraft.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.diamantino.voxelcraft.common.networking.ConnectedClient;
import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.server.world.ServerWorld;
import com.github.terefang.ncs.common.*;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import com.github.terefang.ncs.server.NcsServerHelper;
import com.github.terefang.ncs.server.NcsServerService;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class VoxelCraftServer extends ApplicationAdapter implements NcsPacketListener<SimpleBytesNcsPacket>, NcsStateListener, NcsKeepAliveFailListener {
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
     * Stores the server world.
     */
    public ServerWorld world;

    /**
     * Stores the server instance.
     */
    @Getter
    private static VoxelCraftServer instance;

    private final NcsServerService server;

    public VoxelCraftServer(String ip, int port) {
        instance = this;

        this.ip = ip;
        this.port = port;

        this.server = NcsServerHelper.createSimpleServer(ip, port, this, this);
    }

    @Override
    public void create() {
        Packets.registerPackets();

        this.world = new ServerWorld(new WorldSettings("Test", 123456));

        server.getConfiguration().setUseEpoll(true);
        server.getConfiguration().setClientKeepAliveCounterMax(10);
        server.getConfiguration().setClientKeepAliveTimeout(1000);
        server.getConfiguration().setClientKeepAliveTcpAutoDisconnect(true);
        server.getConfiguration().setTcpNoDelay(true);

        //TODO: Ip ban
        //server.getConfiguration().banAddress();

        server.start();
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {
        server.stop();
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
}
