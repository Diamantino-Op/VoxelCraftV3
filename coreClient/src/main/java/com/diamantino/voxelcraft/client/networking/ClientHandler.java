package com.diamantino.voxelcraft.client.networking;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.networking.packets.Packets;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.IReceivePacket;
import com.diamantino.voxelcraft.common.utils.ResourceLocation;
import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles the client-side channel.
 *
 * @author Diamantino
 */
public class ClientHandler {
    /**
     * Handle the packet.
     *
     * @param connection The connection.
     * @param packet The packet.
     */
    public void onPacket(ClientNetworkManager client, NcsConnection connection, SimpleBytesNcsPacket packet) {
        Gdx.app.log(Constants.debugLogTag, "Packet: " + connection.getPeer().asString());
        Gdx.app.log(Constants.debugLogTag, packet.asHexString());

        try {
            packet.startDecoding();
            BasePacket pkt = Packets.registeredPackets.get(ResourceLocation.fromString(packet.decodeString())).getDeclaredConstructor().newInstance();

            ((IReceivePacket) pkt).readPacketData(connection.getPeer().asString(), packet);
            packet.finishDecoding();
        } catch (Exception e) {
            Gdx.app.log(Constants.errorLogTag, "Error handling client packet from: " + connection.getPeer().asString(), e);
        }
    }

    /**
     * Handle the connection.
     *
     * @param connection The connection.
     */
    public void onConnect(ClientNetworkManager client, NcsConnection connection) {
        Gdx.app.log(Constants.debugLogTag, "Connected to server: " + connection.getPeer().asString());

        //TODO: Send name packet
        //server.connectedClients.put(connection.getPeer().asString(), new ConnectedClient(context.channel()));
    }

    /**
     * Handle the disconnection.
     *
     * @param connection The connection.
     */
    public void onDisconnect(ClientNetworkManager client, NcsConnection connection) {
        Gdx.app.log(Constants.debugLogTag, "Disconnected from server: " + connection.getPeer().asString());
    }

    /**
     * Handle the error.
     *
     * @param connection The connection.
     * @param cause The error cause.
     */
    public void onError(ClientNetworkManager client, NcsConnection connection, Throwable cause) {
        Gdx.app.log(Constants.errorLogTag, "Network error: " + connection.getPeer().asString() + " -- " + cause.getMessage(), cause);
    }
}
