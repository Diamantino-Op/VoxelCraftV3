package com.diamantino.voxelcraft.common.networking;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.channel.Channel;

/**
 * Represents a connected client, holding the channel used to communicate with it.
 *
 * @author Diamantino
 */
public record ConnectedClient(Channel channel) {
    public void sendPacket(BasePacket packet) {
        this.channel.writeAndFlush(packet);
    }
}
