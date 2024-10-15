package com.diamantino.voxelcraft.server;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.networking.packets.codec.PacketDecoder;
import com.diamantino.voxelcraft.common.networking.packets.codec.PacketEncoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.jetbrains.annotations.NotNull;

/**
 * Initializes the server channel pipeline with the necessary handlers for packet encoding and decoding.
 *
 * @author Diamantino
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * The server instance that will handle the incoming packets.
     */
    private final ServerInstance  server;

    /**
     * Creates a new server initializer with the given server instance.
     *
     * @param server The server instance that will handle the incoming packets.
     */
    public ServerInitializer(ServerInstance server) {
        this.server = server;
    }

    /**
     * Initializes the server channel pipeline with the necessary handlers for packet encoding and decoding.
     *
     * @param ch The socket channel to initialize.
     * @throws Exception If an error occurs during the initialization.
     */
    @Override
    protected void initChannel(@NotNull SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("packet-decoder", new PacketDecoder());
        pipeline.addLast("packet-encoder", new PacketEncoder());
        pipeline.addLast("handler", new ServerHandler(this.server));
    }

    /**
     * Handles the exception caught during the channel operation.
     *
     * @param ctx The channel handler context.
     * @param cause The exception caught.
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, @NotNull Throwable cause) {
        Gdx.app.getApplicationLogger().error(Constants.errorLogTag, "An exception occurred in the server initializer.", cause);
    }
}
