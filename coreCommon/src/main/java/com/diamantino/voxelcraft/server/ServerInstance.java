package com.diamantino.voxelcraft.server;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.networking.ConnectedClient;
import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.server.world.ServerWorld;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * ServerInstance class is responsible for managing the server instance, including the server world and connected clients.
 *
 * @author Diamantino
 */
public class ServerInstance {
    /**
     * bossNioEventLoopGroup is responsible for accepting incoming connections.
     */
    private final NioEventLoopGroup bossNioEventLoopGroup = new NioEventLoopGroup(1);

    /**
     * workerNioEventLoopGroup is responsible for handling the traffic of the accepted connections.
     */
    private final NioEventLoopGroup workerNioEventLoopGroup = new NioEventLoopGroup(0);

    /**
     * Stores all connected clients.
     * Key: Client name.
     * Value: ConnectedClient instance.
     */
    public final Map<String, ConnectedClient> connectedClients = new HashMap<>();

    /**
     * Stores the future of the server channel.
     */
    private ChannelFuture channelFuture;

    /**
     * Stores the server IP.
     */
    private final String ip;

    /**
     * Stores the server port.
     */
    private final int port;

    /**
     * Stores the server world.
     */
    public ServerWorld world;

    /**
     * Stores the server instance.
     */
    public static ServerInstance instance;

    /**
     * Initializes the server instance.
     *
     * @param ip is the server IP.
     * @param port is the server port.
     */
    public ServerInstance(String ip, int port) {
        instance = this;

        this.ip = ip;
        this.port = port;

        Packets.registerPackets();

        this.world = new ServerWorld(new WorldSettings("Test", 123456));
    }

    /**
     * Used to start the server instance.
     *
     * @throws InterruptedException if the server instance fails to start.
     */
    public void start() throws InterruptedException {
        this.channelFuture = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(this.bossNioEventLoopGroup, this.workerNioEventLoopGroup)
                .childHandler(new ServerInitializer(this))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.AUTO_READ, true)
                .bind(this.ip, this.port)
                .addListener(ChannelFutureListener.CLOSE_ON_FAILURE)
                .addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE)
                .sync()
                .channel()
                .closeFuture();
    }

    /**
     * Used to update the server instance.
     */
    public void update() {

    }

    /**
     * Used to stop the server instance.
     */
    public void stop() {
        this.channelFuture.channel().close();
    }

    //TODO: Execute packet code here.
    /**
     * Used to read a packet.
     *
     * @param context is the channel handler context.
     * @param packet is the packet to read.
     */
    void readPacket(ChannelHandlerContext context, BasePacket packet) {

    }

    /**
     * Used to handle a client connection.
     * @param context is the channel handler context.
     */
    public void onConnect(ChannelHandlerContext context) {
        Gdx.app.getApplicationLogger().log(Constants.infoLogTag, "Client connected: " + context.name());

        this.connectedClients.put(context.name(), new ConnectedClient(context.channel()));
    }

    /**
     * Used to handle a client disconnection.
     * @param context is the channel handler context.
     */
    public void onDisconnect(ChannelHandlerContext context) {
        this.connectedClients.remove(context.name());
    }

    /**
     * Used to handle a client timeout.
     * @param context is the channel handler context.
     */
    public void onTimeOut(ChannelHandlerContext context) {
        this.onDisconnect(context);
    }
}
