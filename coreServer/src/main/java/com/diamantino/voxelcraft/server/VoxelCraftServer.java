package com.diamantino.voxelcraft.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.server.networking.ServerNetworkManager;
import com.diamantino.voxelcraft.server.world.ServerWorld;
import lombok.Getter;

public class VoxelCraftServer extends ApplicationAdapter {
    /**
     * Stores the server world.
     */
    public ServerWorld world;

    /**
     * Stores the server instance.
     */
    @Getter
    private static VoxelCraftServer instance;

    /**
     * Stores the server network manager.
     */
    public ServerNetworkManager networkManager;

    public VoxelCraftServer(String ip, int port) {
        instance = this;

        this.networkManager = new ServerNetworkManager(ip, port);
    }

    @Override
    public void create() {
        this.networkManager.initManager();

        this.world = new ServerWorld(new WorldSettings("Test", 123456));
    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {
        this.networkManager.stopManager();
    }
}
