package com.diamantino.voxelcraft.launchers;

import com.badlogic.gdx.ApplicationAdapter;
import com.diamantino.voxelcraft.server.ServerInstance;

public class VoxelCraftServer extends ApplicationAdapter {
    private ServerInstance serverInstance;

    @Override
    public void create() {
        serverInstance = new ServerInstance("127.0.0.1", 25000);

        serverInstance.start();
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
