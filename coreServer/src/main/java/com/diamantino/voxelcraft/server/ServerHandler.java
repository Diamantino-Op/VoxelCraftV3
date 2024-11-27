package com.diamantino.voxelcraft.server;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
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
    public void onPacket(VoxelCraftServer server, NcsConnection connection, SimpleBytesNcsPacket packet) {
        Gdx.app.log(Constants.debugLogTag, "Packet: " + connection.getPeer().asString());
        Gdx.app.log(Constants.debugLogTag, packet.asHexString());
    }

    /**
     * Handle the connection.
     *
     * @param connection The connection.
     */
    public void onConnect(VoxelCraftServer server, NcsConnection connection) {
        Gdx.app.log(Constants.debugLogTag, "Connect: " + connection.getPeer().asString());
    }

    /**
     * Handle the disconnection.
     *
     * @param connection The connection.
     */
    public void onDisconnect(VoxelCraftServer server, NcsConnection connection) {
        Gdx.app.log(Constants.debugLogTag, "Disconnect: " + connection.getPeer().asString());
    }

    /**
     * Handle the error.
     *
     * @param connection The connection.
     * @param cause The error cause.
     */
    public void onError(VoxelCraftServer server, NcsConnection connection, Throwable cause) {
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
    public void onKeepAliveFail(VoxelCraftServer server, NcsConnection connection, long timeout, long fails, NcsEndpoint endpoint) {
        Gdx.app.log(Constants.errorLogTag, "Keep alive fail: " + endpoint.asString());
    }
}
