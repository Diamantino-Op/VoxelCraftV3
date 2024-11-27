package com.diamantino.voxelcraft.common.networking;

import com.github.terefang.ncs.common.NcsConnection;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

/**
 * Represents a connected client, holding the channel used to communicate with it.
 *
 * @author Diamantino
 */
public record ConnectedClient(NcsConnection channel) {
    public void sendPacket(SimpleBytesNcsPacket packet) {
        this.channel.sendAndFlush(packet);
    }
}
