package com.diamantino.voxelcraft.launchers;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.diamantino.voxelcraft.client.ClientInstance;
import com.diamantino.voxelcraft.client.screens.GameScreen;
import com.diamantino.voxelcraft.client.utils.AtlasManager;

public class VoxelCraftClient extends ApplicationAdapter {
    private AtlasManager atlasManager;
    private GameScreen gameScreen;
    private ClientInstance clientInstance;

    @Override
    public void create() {
        atlasManager = new AtlasManager();
        atlasManager.init();

        this.gameScreen = new GameScreen(this);
        this.gameScreen.show();

        // TODO: Move
        clientInstance = new ClientInstance("127.0.0.1", 25000);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        gameScreen.render(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        gameScreen.resize(width, height);
    }

    @Override
    public void dispose() {
        atlasManager.dispose();
        gameScreen.dispose();
    }
}
