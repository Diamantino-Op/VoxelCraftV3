package com.diamantino.voxelcraft.launchers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.diamantino.voxelcraft.client.networking.ClientInstance;
import com.diamantino.voxelcraft.client.screens.GameScreen;
import com.diamantino.voxelcraft.client.screens.MainMenuScreen;
import com.diamantino.voxelcraft.client.utils.AtlasManager;

public class VoxelCraftClient extends ApplicationAdapter {
    private AtlasManager atlasManager;
    public SpriteBatch batch;
    public BitmapFont font;

    private MainMenuScreen mainMenuScreen;
    private ClientInstance clientInstance;

    private final int logLevel;

    public VoxelCraftClient(int logLevel) {
        this.logLevel = logLevel;
    }

    @Override
    public void create() {
        Gdx.app.setLogLevel(logLevel);

        batch = new SpriteBatch();
        font = new BitmapFont();

        atlasManager = new AtlasManager();
        atlasManager.init();

        this.mainMenuScreen = new MainMenuScreen(this);
        this.mainMenuScreen.show();

        // TODO: Move
        //clientInstance = new ClientInstance("127.0.0.1", 25000);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        mainMenuScreen.render(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        mainMenuScreen.resize(width, height);
    }

    @Override
    public void dispose() {
        atlasManager.dispose();
        mainMenuScreen.dispose();
    }
}
