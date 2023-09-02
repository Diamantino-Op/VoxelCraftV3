package com.diamantino.voxelcraft.client.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.PerspectiveCamera;
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

    }

    @Override
    public void render(float delta) {
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
