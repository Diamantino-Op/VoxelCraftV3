package com.diamantino.voxelcraft.client.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.diamantino.voxelcraft.client.utils.ClientLoadingUtils;
import com.diamantino.voxelcraft.client.utils.GuiHelper;
import com.diamantino.voxelcraft.common.registration.Mods;
import com.diamantino.voxelcraft.launchers.VoxelCraftClient;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A screen that displays a loading bar while the game is loading resources.
 *
 * @author Diamantino
 */
public class LoadingScreen implements Screen {
    private final VoxelCraftClient game;

    private Stage stage;

    private final OrthographicCamera camera;
    private final ScreenViewport viewport;

    private ProgressBar loadingBar;

    /**
     *  Temporary atlas textures registry.
     *  Key: Texture location.
     *  Value: Atlas ID.
     */
    private final Map<String, JSONObject> modResources = new HashMap<>();

    private final Map<String, PixmapPacker> atlasPackers = new HashMap<>();

    private boolean shouldLoad = false;

    public LoadingScreen(VoxelCraftClient game) {
        this.game = game;

        camera = new OrthographicCamera();

        viewport = new ScreenViewport(camera);

        draw(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1f);
    }

    private void startLoadingResources() {
        this.shouldLoad = false;

        ClientLoadingUtils.loadResourcesFile(modResources, "voxelcraft");

        Mods.mods.forEach((modId, _) -> {
            ClientLoadingUtils.loadResourcesFile(modResources, modId);
        });

        //------------------- Stage 1 -------------------
        modResources.forEach((modId, resourcesJson) -> ClientLoadingUtils.loadTextures(game, modId, resourcesJson));

        //------------------- Stage 2 -------------------
        modResources.forEach((modId, resourcesJson) -> ClientLoadingUtils.saveAtlases(game, atlasPackers, modId, resourcesJson));

        ClientLoadingUtils.loadAtlases(game, atlasPackers);

        //------------------- Stage 3 -------------------

        this.shouldLoad = true;
    }

    public void draw(int width, int height, float guiScale) {
        if (stage != null) {
            stage.clear();
            stage.dispose();
        }

        camera.setToOrtho(false, width, height);

        viewport.update(width, height, true);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        NinePatchDrawable barBackground = new NinePatchDrawable(ClientLoadingUtils.getTexture(game, "voxelcraft", "gui", "loading_bar_background", NinePatch.class));
        NinePatchDrawable barKnob = new NinePatchDrawable(ClientLoadingUtils.getTexture(game, "voxelcraft", "gui", "loading_bar_background", NinePatch.class));

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle(barBackground, barKnob);

        loadingBar = new ProgressBar(0, 1, 0.1f, false, style);
        loadingBar.setSize(width - ((float) width / 10), 50);
        GuiHelper.setObjectPosition(loadingBar, 0, 0, true, false);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 1, 1);

        camera.update();
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1, 1, true);

        stage.act(delta);
        stage.draw();
    }

    /**
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        draw(width, height, 1f);
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();

        modResources.clear();
        atlasPackers.clear();
    }
}
