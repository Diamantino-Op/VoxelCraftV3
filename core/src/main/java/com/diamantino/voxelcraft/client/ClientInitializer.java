package com.diamantino.voxelcraft.client;

import com.diamantino.voxelcraft.common.networking.packets.codec.PacketDecoder;
import com.diamantino.voxelcraft.common.networking.packets.codec.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.jetbrains.annotations.NotNull;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    private final ClientInstance client;

    public ClientInitializer(ClientInstance client) {
        this.client = client;
    }

    @Override
    protected void initChannel(@NotNull SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("packet-decoder", new PacketDecoder());
        pipeline.addLast("packet-encoder", new PacketEncoder());
        pipeline.addLast("handler", new ClientHandler(this.client));
    }
}
