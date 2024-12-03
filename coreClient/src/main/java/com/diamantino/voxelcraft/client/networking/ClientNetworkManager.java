package com.diamantino.voxelcraft.client.networking;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

public class ClientNetworkManager {
    public void initManager() {

    }

    public void connectToServer(String ip, int port) {

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
            buffer.encodeString(packet.getId() + "_server");

            packet.writePacketData(buffer);
            buffer.finishEncoding();

            ClientInstance.instance.serverConnection.writeAndFlush(buffer);
        } catch (Exception e) {
            Gdx.app.error(Constants.errorLogTag, "Error encoding packet: " + packet.getClass().getSimpleName(), e);
        }
    }
}
