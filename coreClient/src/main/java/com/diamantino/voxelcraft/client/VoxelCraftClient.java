package com.diamantino.voxelcraft.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.diamantino.voxelcraft.client.networking.ClientNetworkManager;
import com.diamantino.voxelcraft.client.resources.VoxelAssetManager;
import com.diamantino.voxelcraft.client.screens.LoadingScreen;
import com.diamantino.voxelcraft.client.screens.MainMenuScreen;
import com.diamantino.voxelcraft.client.world.ClientWorld;
import lombok.Getter;

public class VoxelCraftClient extends ApplicationAdapter {
    public final VoxelAssetManager assetManager;

    public BitmapFont font;

    private LoadingScreen loadingScreen;
    private MainMenuScreen mainMenuScreen;

    public ClientNetworkManager networkManager;

    private final int logLevel;

    public ClientWorld world;

    @Getter
    private static VoxelCraftClient instance;

    public VoxelCraftClient(int logLevel) {
        instance = this;

        this.logLevel = logLevel;

        this.networkManager = new ClientNetworkManager();

        this.assetManager = new VoxelAssetManager();
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(logLevel);

        this.networkManager.initManager();

        font = new BitmapFont();

        //this.mainMenuScreen = new MainMenuScreen(this);
        //this.mainMenuScreen.show();

        this.loadingScreen = new LoadingScreen(this);
        this.loadingScreen.show();

        // TODO: Move
        //clientInstance = new ClientInstance("127.0.0.1", 25000);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //mainMenuScreen.render(deltaTime);
        this.loadingScreen.render(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        mainMenuScreen.resize(width, height);
    }

    @Override
    public void dispose() {
        //mainMenuScreen.dispose();
        this.loadingScreen.dispose();
        this.assetManager.dispose();
    }
}
