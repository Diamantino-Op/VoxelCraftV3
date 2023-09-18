package com.diamantino.voxelcraft.server;

import com.diamantino.voxelcraft.common.networking.ConnectedClient;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

public class ServerInstance {
    private final NioEventLoopGroup bossNioEventLoopGroup = new NioEventLoopGroup(1), workerNioEventLoopGroup = new NioEventLoopGroup(0);
    private final Map<String, ConnectedClient> connectedClients = new HashMap<>();
    private ChannelFuture channelFuture;

    private final String ip;
    private final int port;

    public ServerInstance(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

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

    public void update() {

    }

    public void stop() {
        this.channelFuture.channel().close();
    }

    void readPacket(ChannelHandlerContext context, BasePacket packet) {

    }

    public void onConnect(ChannelHandlerContext context) {
        System.out.println("Client Connected: " + context.name());
        this.connectedClients.put(context.name(), new ConnectedClient(context.channel()));
    }

    public void onDisconnect(ChannelHandlerContext context) {
        this.connectedClients.remove(context.name());
    }

    public void onTimeOut(ChannelHandlerContext context) {
        this.onDisconnect(context);
    }
}
