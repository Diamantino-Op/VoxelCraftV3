package com.diamantino.voxelcraft.client.networking;

import com.diamantino.voxelcraft.client.world.ClientWorld;
import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Client connection instance class.
 *
 * @author Diamantino
 */
public class ClientInstance {
    /**
     * The client event group.
     */
    private final NioEventLoopGroup group = new NioEventLoopGroup();

    /**
     * The instance of the connection to the server.
     */
    public Channel serverConnection;

    /**
     * The ip of the server.
     */
    private final String ip;

    /**
     * The port of the server.
     */
    private final int port;

    /**
     * The client world instance.
     */
    public ClientWorld world;

    /**
     * The client instance.
     */
    public static ClientInstance instance;

    /**
     * The constructor of the client instance class.
     */
    public ClientInstance(String ip, int port) {
        instance = this;

        this.ip = ip;
        this.port = port;

        Packets.registerPackets();

        // TODO: Move or receive from server
        world = new ClientWorld(new WorldSettings("Test", 123456));
    }

    /**
     * Connect to the server.
     */
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

    /**
     * Disconnect from the server.
     */
    public void disconnect() {
        this.group.shutdownGracefully();
    }

    //TODO: Execute packet code here.
    /**
     * Read the packet.
     * @param packet The packet to be read.
     */
    void readPacket(BasePacket packet) {

    }
}
