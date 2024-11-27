package com.diamantino.voxelcraft.client.networking;

import com.diamantino.voxelcraft.common.networking.packets.codec.PacketDecoder;
import com.diamantino.voxelcraft.common.networking.packets.codec.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.jetbrains.annotations.NotNull;

/**
 * Client connection initializer class.
 *
 * @author Diamantino
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * The client instance.
     */
    private final ClientInstance client;

    /**
     * Client initializer class constructor.
     * @param client The instance of the client.
     */
    public ClientInitializer(ClientInstance client) {
        this.client = client;
    }

    /**
     * Client channel initializer.
     * @param ch The channel to initialize.
     */
    @Override
    protected void initChannel(@NotNull SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("packet-decoder", new PacketDecoder());
        pipeline.addLast("packet-encoder", new PacketEncoder());
        pipeline.addLast("handler", new ClientHandler(this.client));
    }
}
