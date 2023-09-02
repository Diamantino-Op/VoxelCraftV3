package com.diamantino.voxelcraft.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.blocks.Blocks;
import com.diamantino.voxelcraft.client.VoxelCraftClient;
import com.diamantino.voxelcraft.world.chunk.Chunk;

public class GameScreen implements Screen {
    private final VoxelCraftClient game;
    private PerspectiveCamera camera;

    // TODO: Move to settings
    private int FOV = 70;
    private int resX = 1920;
    private int rexY = 1080;

    private Chunk tempChunk;

    // TODO: Just temporary
    private int dragX, dragY;
    private final float rotateSpeed = 0.2f;
    private final float movementSpeed = 0.2f;
    private boolean forward = false;
    private boolean back = false;
    private boolean right = false;
    private boolean left = false;

    public GameScreen(final VoxelCraftClient game) {
        this.game = game;

        this.camera = new PerspectiveCamera(FOV, resX, rexY);

        this.tempChunk = new Chunk(0, 0, 0);

        for (int x = 0; x < Chunk.sizeX; x++) {
            for (int y = 0; y < Chunk.sizeY; y++) {
                for (int z = 0; z < Chunk.sizeZ; z++) {
                    this.tempChunk.setBlockAt(Blocks.stone, x, y, z);
                }
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.W -> forward = true;
                    case Input.Keys.A -> left = true;
                    case Input.Keys.S -> back = true;
                    case Input.Keys.D -> right = true;
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
                float x = dragX -screenX;
                // change this Vector3.y with cam.up if you have a dynamic up.
                camera.rotate(Vector3.Y,x * rotateSpeed);

                // rotating on the x and z axis is different
                float y = (float) Math.sin( (double)(dragY -screenY)/180f);
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
        float speed = movementSpeed;
        if ((forward | back) & (right | left)) {
            speed /= Math.sqrt(2);
        }
        System.out.println(speed);
        if (forward) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.x *= speed * timeElapsed;
            v.z *= speed * timeElapsed;
            camera.translate(v);
            camera.update();
        }
        if (back) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.x = -v.x;
            v.z = -v.z;
            v.x *= speed * timeElapsed;
            v.z *= speed * timeElapsed;
            camera.translate(v);
            camera.update();
        }
        if (left) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.rotate(Vector3.Y, 90);
            v.x *= speed * timeElapsed;
            v.z *= speed * timeElapsed;
            camera.translate(v);
            camera.update();
        }
        if (right) {
            Vector3 v = camera.direction.cpy();
            v.y = 0f;
            v.rotate(Vector3.Y, -90);
            v.x *= speed * timeElapsed;
            v.z *= speed * timeElapsed;
            camera.translate(v);
            camera.update();

        }
    }

    @Override
    public void render(float delta) {
        walking(delta);

        camera.update();

        tempChunk.render(camera.combined);
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
        tempChunk.dispose();
    }
}
