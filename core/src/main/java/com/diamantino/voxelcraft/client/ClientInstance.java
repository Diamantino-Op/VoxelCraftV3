package com.diamantino.voxelcraft.client;

import com.diamantino.voxelcraft.client.world.ClientWorld;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientInstance {
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    private final String ip;
    private final int port;

    public ClientWorld world;

    public static ClientInstance instance;

    public ClientInstance(String ip, int port) {
        instance = this;

        this.ip = ip;
        this.port = port;

        // TODO: Move
        world = new ClientWorld("Test", new WorldSettings());
    }

    public void connect() throws InterruptedException {
        this.channel = new Bootstrap()
                .group(this.group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(this))
                .connect(this.ip, this.port)
                .sync()
                .channel();
        this.channel.closeFuture();
    }

    public void disconnect() {
        this.group.shutdownGracefully();
    }

    void readPacket(BasePacket packet) {

    }
}
