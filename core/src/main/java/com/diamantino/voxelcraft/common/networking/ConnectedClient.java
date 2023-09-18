package com.diamantino.voxelcraft.common.networking;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.channel.Channel;

public record ConnectedClient(Channel channel) {
    public void sendPacket(BasePacket packet) {
        this.channel.writeAndFlush(packet);
    }
}
