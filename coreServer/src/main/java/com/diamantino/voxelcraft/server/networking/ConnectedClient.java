package com.diamantino.voxelcraft.server.networking;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.ISendPacket;
import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

/**
 * Represents a connected client, holding the channel used to communicate with it.
 *
 * @author Diamantino
 */
public record ConnectedClient(NcsConnection channel, String playerName) {
    public void sendPacket(BasePacket packet) {
        SimpleBytesNcsPacket buffer = SimpleBytesNcsPacket.create();

        try {
            buffer.startEncoding();
            buffer.encodeString(packet.getId().toString().replace("_server", "_client"));

            ((ISendPacket) packet).writePacketData(buffer);
            buffer.finishEncoding();

            this.channel.sendAndFlush(buffer);
        } catch (Exception e) {
            Gdx.app.error(Constants.errorLogTag, "Error encoding packet: " + packet.getClass().getSimpleName(), e);
        }
    }
}
