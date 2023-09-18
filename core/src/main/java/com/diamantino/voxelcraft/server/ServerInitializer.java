package com.diamantino.voxelcraft.server;

import com.diamantino.voxelcraft.common.networking.packets.codec.PacketDecoder;
import com.diamantino.voxelcraft.common.networking.packets.codec.PacketEncoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.jetbrains.annotations.NotNull;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    private final ServerInstance  server;

    public ServerInitializer(ServerInstance server) {
        this.server = server;
    }

    @Override
    protected void initChannel(@NotNull SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("packet-decoder", new PacketDecoder());
        pipeline.addLast("packet-encoder", new PacketEncoder());
        pipeline.addLast("handler", new ServerHandler(this.server));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, @NotNull Throwable cause) {
        cause.printStackTrace();
    }
}
