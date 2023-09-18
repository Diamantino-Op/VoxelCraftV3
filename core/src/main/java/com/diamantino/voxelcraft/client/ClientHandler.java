package com.diamantino.voxelcraft.client;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<BasePacket> {
    private final ClientInstance client;

    public ClientHandler(ClientInstance client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePacket msg) throws Exception {
        this.client.readPacket(msg);
    }
}
