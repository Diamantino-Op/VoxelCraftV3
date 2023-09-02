package com.diamantino.voxelcraft.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.diamantino.voxelcraft.client.screens.GameScreen;
import com.diamantino.voxelcraft.utils.AtlasManager;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class VoxelCraftClient extends ApplicationAdapter {
    private AtlasManager atlasManager;
    private GameScreen gameScreen;

    @Override
    public void create() {
        atlasManager = new AtlasManager();
        atlasManager.init();

        this.gameScreen = new GameScreen(this);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameScreen.render(deltaTime);
    }

    @Override
    public void resize(int width, int height) {
        gameScreen.resize(width, height);
    }

    @Override
    public void dispose() {
        atlasManager.dispose();
    }
}
