package com.diamantino.voxelcraft.server;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<BasePacket> {
    private final ServerInstance server;

    public ServerHandler(ServerInstance server) {
        this.server = server;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePacket packet) {
        this.server.readPacket(ctx, packet);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.server.onConnect(ctx);
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        this.server.onDisconnect(ctx);
        super.handlerRemoved(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        this.server.onTimeOut(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        this.server.onDisconnect(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
