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
import com.diamantino.voxelcraft.client.utils.GuiHelper;
import com.diamantino.voxelcraft.client.VoxelCraftClient;

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

        //------------------- Images -------------------
        Image titleImage = new Image((Texture) TextureManager.graphicalObjects.get("voxelcraft"));

        GuiHelper.setObjectSize(titleImage, 1009, 155, width, height, guiScale, true, false, false);
        GuiHelper.setObjectPosition(titleImage, 0, height - ((int) titleImage.getHeight()) - 10, width, height, true, false);

        stage.addActor(titleImage);

        //------------------- Buttons -------------------

        // SinglePlayer button
        this.buttonDrawable = new NinePatchDrawable((NinePatch) TextureManager.graphicalObjects.get("button"));
        this.buttonHoverDrawable = new NinePatchDrawable((NinePatch) TextureManager.graphicalObjects.get("button_hover"));
        this.buttonPressedDrawable = new NinePatchDrawable((NinePatch) TextureManager.graphicalObjects.get("button_pressed"));

        TextButton playButton = new TextButton("Singleplayer", new TextButton.TextButtonStyle(this.buttonDrawable, this.buttonPressedDrawable, this.buttonDrawable, game.font));

        GuiHelper.setObjectSize(playButton, 300, 30, width, height, guiScale, true, false, false);
        GuiHelper.setObjectPosition(playButton, 0, 100, width, height, true, true);

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
    }
}
