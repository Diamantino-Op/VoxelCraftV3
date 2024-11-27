package com.diamantino.voxelcraft.server;

import com.badlogic.gdx.ApplicationAdapter;

public class VoxelCraftServer extends ApplicationAdapter {
    private ServerInstance serverInstance;

    @Override
    public void create() {
        serverInstance = new ServerInstance("127.0.0.1", 25000);

        try {
            serverInstance.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render() {
        serverInstance.update();
    }

    @Override
    public void dispose() {
        serverInstance.stop();
    }
}
