package com.diamantino.voxelcraft.server;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Handles the server-side channel.
 *
 * @author Diamantino
 */
public class ServerHandler extends SimpleChannelInboundHandler<BasePacket> {
    /**
     * The server instance.
     */
    private final ServerInstance server;

    /**
     * Creates a new server handler.
     *
     * @param server The server instance.
     */
    public ServerHandler(ServerInstance server) {
        this.server = server;
    }

    /**
     * Handles a received packet.
     *
     * @param ctx The channel handler context.
     * @param packet The received packet.
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BasePacket packet) {
        this.server.readPacket(ctx, packet);
    }

    /**
     * Handles the addition of the handler to the channel.
     *
     * @param ctx The channel handler context.
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.server.onConnect(ctx);
        super.handlerAdded(ctx);
    }

    /**
     * Handles the removal of the handler from the channel.
     *
     * @param ctx The channel handler context.
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        this.server.onDisconnect(ctx);
        super.handlerRemoved(ctx);
    }

    /**
     * Handles the channel becoming inactive.
     *
     * @param ctx The channel handler context.
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        this.server.onTimeOut(ctx);
    }

    /**
     * Handles the channel becoming unregistered.
     *
     * @param ctx The channel handler context.
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        this.server.onDisconnect(ctx);
    }

    /**
     * Handles an exception.
     *
     * @param ctx The channel handler context.
     * @param cause The exception.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Gdx.app.getApplicationLogger().error(Constants.errorLogTag, "An exception occurred in the server handler.", cause);
    }
}
