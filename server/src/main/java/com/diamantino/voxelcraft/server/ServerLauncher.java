package com.diamantino.voxelcraft.server;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.diamantino.voxelcraft.launchers.VoxelCraftServer;

/** Launches the server application. */
public class ServerLauncher {
    public static void main(String[] args) {
        createApplication();
    }

    private static void createApplication() {
        new HeadlessApplication(new VoxelCraftServer(), getDefaultConfiguration());
    }

    private static HeadlessApplicationConfiguration getDefaultConfiguration() {
        HeadlessApplicationConfiguration configuration = new HeadlessApplicationConfiguration();

        return configuration;
    }
}
