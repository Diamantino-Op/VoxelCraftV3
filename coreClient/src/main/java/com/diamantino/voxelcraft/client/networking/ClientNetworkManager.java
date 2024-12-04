package com.diamantino.voxelcraft.client.networking;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.client.VoxelCraftClient;
import com.diamantino.voxelcraft.client.networking.c2s.SendRequestChunkPacket;
import com.diamantino.voxelcraft.client.networking.s2c.ReceiveChunkSyncPacket;
import com.diamantino.voxelcraft.client.networking.s2c.ReceiveSyncPropertyPacket;
import com.diamantino.voxelcraft.client.networking.s2c.ReceiveWorldSettingsPacket;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.networking.packets.Packets;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.ISendPacket;
import com.diamantino.voxelcraft.common.utils.ResourceLocation;
import com.diamantino.voxelcraft.common.utils.Side;
import com.github.terefang.ncs.client.NcsClientHelper;
import com.github.terefang.ncs.client.NcsClientService;
import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.NcsPacketListener;
import com.github.terefang.ncs.common.NcsStateListener;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import lombok.Getter;

/**
 * Manages the client network.
 *
 * @author Diamantino
 */
@Getter
public class ClientNetworkManager implements NcsPacketListener<SimpleBytesNcsPacket>, NcsStateListener {
    /**
     * Stores the client instance.
     */
    private NcsClientService clientService;

    /**
     * Initializes the client network manager.
     */
    public void initManager() {
        registerPackets();
    }

    /**
     * Stops the server network manager.
     */
    public void stopManager() {
        if (clientService != null) {
            disconnectFromServer();
        }
    }

    /**
     * Registers all packets.
     */
    private void registerPackets() {
        // Client to Server
        Packets.registerPacket(new ResourceLocation("request_chunk"), Side.CLIENT, SendRequestChunkPacket.class);

        // Server to Client
        Packets.registerPacket(new ResourceLocation("chunk_sync"), Side.CLIENT, ReceiveChunkSyncPacket.class);
        Packets.registerPacket(new ResourceLocation("sync_property"), Side.CLIENT, ReceiveSyncPropertyPacket.class);
        Packets.registerPacket(new ResourceLocation("world_settings"), Side.CLIENT, ReceiveWorldSettingsPacket.class);
    }

    /**
     * Connect to the server.
     *
     * @param ip Server IP.
     * @param port Server port.
     */
    public void connectToServer(String ip, int port) {
        Gdx.app.log(Constants.infoLogTag, "Connecting to server: " + ip + ":" + port);

        this.clientService = NcsClientHelper.createSimpleClient(ip, port, this, this);

        try {
            clientService.connectNow();
        } catch (Exception e) {
            Gdx.app.error(Constants.errorLogTag, "Error connecting to server.", e);
        }
    }

    /**
     * Disconnect from the server.
     */
    public void disconnectFromServer() {
        try {
            // TODO: Clear the world and add disconnection package.
            clientService.disconnectNow();
            clientService.shutdown();

            clientService = null;
        } catch (Exception e) {
            Gdx.app.error(Constants.errorLogTag, "Error disconnecting to server.", e);
        }
    }

    /**
     * Called when a packet is received.
     *
     * @param connection The client connection.
     * @param packet The packet.
     */
    @Override
    public void onPacket(NcsConnection connection, SimpleBytesNcsPacket packet) {
        connection.getContext(ClientHandler.class).onPacket(this, connection, packet);
    }

    /**
     * Called when a connection is established.
     *
     * @param connection The client connection.
     */
    @Override
    public void onConnect(NcsConnection connection) {
        connection.getContext(ClientHandler.class).onConnect(this, connection);
    }

    /**
     * Called when a connection is disconnected.
     *
     * @param connection The client connection.
     */
    @Override
    public void onDisconnect(NcsConnection connection) {
        connection.getContext(ClientHandler.class).onDisconnect(this, connection);
    }

    /**
     * Called when an error occurs.
     *
     * @param connection The client connection.
     * @param cause The error.
     */
    @Override
    public void onError(NcsConnection connection, Throwable cause) {
        connection.getContext(ClientHandler.class).onError(this, connection, cause);
    }

    /**
     * Send a packet to the server.
     *
     * @param packet Packet to send.
     */
    public static void sendToServer(BasePacket packet) {
        SimpleBytesNcsPacket buffer = SimpleBytesNcsPacket.create();

        try {
            buffer.startEncoding();
            buffer.encodeString(packet.getId().toString().replace("_client", "_server"));

            ((ISendPacket) packet).writePacketData(buffer);
            buffer.finishEncoding();

            VoxelCraftClient.getInstance().networkManager.clientService.sendAndFlush(buffer);
        } catch (Exception e) {
            Gdx.app.error(Constants.errorLogTag, "Error encoding packet: " + packet.getClass().getSimpleName(), e);
        }
    }
}
