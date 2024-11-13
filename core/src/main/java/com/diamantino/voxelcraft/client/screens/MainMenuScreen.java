package com.diamantino.voxelcraft.client.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.diamantino.voxelcraft.client.utils.TextureManager;
import com.diamantino.voxelcraft.launchers.VoxelCraftClient;

/**
 * Main menu screen class.
 *
 * @author Diamantino
 */
public class MainMenuScreen implements Screen {
    private final VoxelCraftClient game;

    private Stage stage;

    private final OrthographicCamera camera;
    private final ScreenViewport viewport;

    private NinePatchDrawable buttonDrawable;
    private NinePatchDrawable buttonHoverDrawable;
    private NinePatchDrawable buttonPressedDrawable;

    public MainMenuScreen(VoxelCraftClient game) {
        this.game = game;

        camera = new OrthographicCamera();

        viewport = new ScreenViewport(camera);

        draw(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1f);
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

        float objectScale = (float) width / 1920;

        // Images
        Image titleImage = new Image((Texture) TextureManager.graphicalObjects.get("voxelcraft"));
        titleImage.setSize(Math.round((1009 * guiScale) * objectScale), 155 * guiScale);
        titleImage.setPosition(Math.round(((float) width / 2) - (titleImage.getWidth() / 2)), height - titleImage.getHeight() - 10);

        stage.addActor(titleImage);

        // Buttons
        this.buttonDrawable = new NinePatchDrawable((NinePatch) TextureManager.graphicalObjects.get("button"));
        this.buttonHoverDrawable = new NinePatchDrawable((NinePatch) TextureManager.graphicalObjects.get("button_hover"));
        this.buttonPressedDrawable = new NinePatchDrawable((NinePatch) TextureManager.graphicalObjects.get("button_pressed"));

        TextButton playButton = new TextButton("Play", new TextButton.TextButtonStyle(this.buttonDrawable, this.buttonPressedDrawable, this.buttonDrawable, game.font));
        playButton.setSize(Math.round((300f * guiScale) * objectScale), 30f * guiScale);
        playButton.setPosition(Math.round(((float) width / 2) - (playButton.getWidth() / 2)), 100);
        playButton.setTransform(true);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });

        stage.addActor(playButton);
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
     * @param width
     * @param height
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
    }
}
