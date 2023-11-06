package com.diamantino.voxelcraft.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.launchers.VoxelCraftClient;

public class GameScreen implements Screen {
    private final VoxelCraftClient game;


    // TODO: Move to settings
    private int FOV = 70;
    private int resX = 1920;
    private int rexY = 1080;

    // TODO: Just temporary
    private int dragX, dragY;
    private final float rotateSpeed = 0.4f;
    private final float movementSpeed = 0.6f;
    private boolean forward = false;
    private boolean back = false;
    private boolean right = false;
    private boolean left = false;
    private boolean up = false;
    private boolean down = false;

    public GameScreen(final VoxelCraftClient game) {
        this.game = game;

        this.camera = new PerspectiveCamera(FOV, resX, rexY);
    }

    @Override
    public void show() {
        Gdx.input.setCursorCatched(true);

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.W -> forward = true;
                    case Input.Keys.A -> left = true;
                    case Input.Keys.S -> back = true;
                    case Input.Keys.D -> right = true;
                    case Input.Keys.SPACE -> up = true;
                    case Input.Keys.SHIFT_LEFT -> down = true;
                }

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                switch (keycode) {
                    case Input.Keys.W -> forward = false;
                    case Input.Keys.A -> left = false;
                    case Input.Keys.S -> back = false;
                    case Input.Keys.D -> right = false;
                    case Input.Keys.SPACE -> up = false;
                    case Input.Keys.SHIFT_LEFT -> down = false;
                }

                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                Vector3 direction = camera.direction.cpy();

                // rotating on the y axis
                float x = dragX - screenX;
                // change this Vector3.y with cam.up if you have a dynamic up.
                camera.rotate(Vector3.Y,x * rotateSpeed);

                // rotating on the x and z axis is different
                float y = (float) Math.sin((double) (dragY - screenY) / 180f);
                if (Math.abs(camera.direction.y + y * (rotateSpeed * 5.0f)) < 0.9) {
                    camera.direction.y += y * (rotateSpeed * 5.0f) ;
                }

                camera.update();
                dragX = screenX;
                dragY = screenY;
                return true;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }

        });
    }

    private void walking(float timeElapsed) {
        if (forward) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.x *= movementSpeed * 4 * timeElapsed;
            v.z *= movementSpeed * 4 * timeElapsed;
            camera.translate(v);
            camera.update();
        }
        if (back) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.x = -v.x;
            v.z = -v.z;
            v.x *= movementSpeed * 4 * timeElapsed;
            v.z *= movementSpeed * 4 * timeElapsed;
            camera.translate(v);
            camera.update();
        }
        if (left) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.rotate(Vector3.Y, 90);
            v.x *= movementSpeed * 4 * timeElapsed;
            v.z *= movementSpeed * 4 * timeElapsed;
            camera.translate(v);
            camera.update();
        }
        if (right) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.rotate(Vector3.Y, -90);
            v.x *= movementSpeed * 4 * timeElapsed;
            v.z *= movementSpeed * 4 * timeElapsed;
            camera.translate(v);
            camera.update();

        }
        if (up) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.y += movementSpeed * 4 * timeElapsed;
            v.x = 0f;
            v.z = 0f;
            camera.translate(v);
            camera.update();
        }
        if (down) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.y += -movementSpeed * 4 * timeElapsed;
            v.x = 0f;
            v.z = 0f;
            camera.translate(v);
            camera.update();

        }
    }

    @Override
    public void render(float delta) {
        walking(delta);
    }

    @Override
    public void resize(int width, int height) {
        camera = new PerspectiveCamera(FOV, width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
