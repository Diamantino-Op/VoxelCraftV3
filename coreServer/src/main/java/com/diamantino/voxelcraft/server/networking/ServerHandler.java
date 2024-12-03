package com.diamantino.voxelcraft.server.networking;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.networking.ConnectedClient;
import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.utils.ResourceLocation;
import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.NcsEndpoint;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

/**
 * Handles the server-side channel.
 *
 * @author Diamantino
 */
public class ServerHandler {
    /**
     * Handle the packet.
     *
     * @param connection The connection.
     * @param packet The packet.
     */
    public void onPacket(ServerNetworkManager server, NcsConnection connection, SimpleBytesNcsPacket packet) {
        Gdx.app.log(Constants.debugLogTag, "Packet: " + connection.getPeer().asString());
        Gdx.app.log(Constants.debugLogTag, packet.asHexString());

        try {
            packet.startDecoding();
            BasePacket pkt = Packets.registeredPackets.get(ResourceLocation.fromString(packet.decodeString())).getDeclaredConstructor().newInstance();

            pkt.readPacketData(connection.getPeer().asString(), packet);
            packet.finishDecoding();
        } catch (Exception e) {
            Gdx.app.log(Constants.errorLogTag, "Error handling packet from: " + connection.getPeer().asString(), e);
        }
    }

    /**
     * Handle the connection.
     *
     * @param connection The connection.
     */
    public void onConnect(ServerNetworkManager server, NcsConnection connection) {
        Gdx.app.log(Constants.debugLogTag, "Connect: " + connection.getPeer().asString());

        //TODO: Add username
        server.connectedClients.put(connection.getPeer().asString(), new ConnectedClient(connection, "Test"));

        //TODO: Send name packet
        //server.connectedClients.put(connection.getPeer().asString(), new ConnectedClient(context.channel()));
    }

    /**
     * Handle the disconnection.
     *
     * @param connection The connection.
     */
    public void onDisconnect(ServerNetworkManager server, NcsConnection connection) {
        Gdx.app.log(Constants.debugLogTag, "Disconnect: " + connection.getPeer().asString());

        server.connectedClients.remove(connection.getPeer().asString());
    }

    /**
     * Handle the error.
     *
     * @param connection The connection.
     * @param cause The error cause.
     */
    public void onError(ServerNetworkManager server, NcsConnection connection, Throwable cause) {
        Gdx.app.log(Constants.errorLogTag, "Network error: " + connection.getPeer().asString() + " -- " + cause.getMessage(), cause);
    }

    /**
     * Handle the keep alive fail.
     *
     * @param connection The connection.
     * @param timeout The timeout.
     * @param fails The fails.
     * @param endpoint The endpoint.
     */
    public void onKeepAliveFail(ServerNetworkManager server, NcsConnection connection, long timeout, long fails, NcsEndpoint endpoint) {
        Gdx.app.log(Constants.errorLogTag, "Keep alive fail: " + endpoint.asString());
    }
}
