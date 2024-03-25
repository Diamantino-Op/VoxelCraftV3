package com.diamantino.voxelcraft.client.networking;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Client connection handler class.
 *
 * @author Diamantino
 */
public class ClientHandler extends SimpleChannelInboundHandler<BasePacket> {
    /**
     * The client instance.
     */
    private final ClientInstance client;

    /**
     * Client handler class constructor.
     * @param client The instance of the client.
     */
    public ClientHandler(ClientInstance client) {
        this.client = client;
    }

    /**
     * Client channel reader.
     * @param ctx The channel handler context.
     * @param msg The packet message.
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePacket msg) {
        this.client.readPacket(msg);
    }
}
