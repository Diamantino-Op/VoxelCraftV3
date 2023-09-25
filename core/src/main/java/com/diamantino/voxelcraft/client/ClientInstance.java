package com.diamantino.voxelcraft.client;

import com.diamantino.voxelcraft.client.world.ClientWorld;
import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientInstance {
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    public Channel serverConnection;

    private final String ip;
    private final int port;

    public ClientWorld world;

    public static ClientInstance instance;

    public ClientInstance(String ip, int port) {
        instance = this;

        this.ip = ip;
        this.port = port;

        Packets.registerPackets();

        // TODO: Move
        world = new ClientWorld(new WorldSettings("Test", 123456));
    }

    public void connect() throws InterruptedException {
        this.serverConnection = new Bootstrap()
                .group(this.group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer(this))
                .connect(this.ip, this.port)
                .sync()
                .channel();
        this.serverConnection.closeFuture();
    }

    public void disconnect() {
        this.group.shutdownGracefully();
    }

    void readPacket(BasePacket packet) {

    }
}
