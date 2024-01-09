package com.diamantino.voxelcraft.client.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {
    /**
     * The core shader of the game.
     */
    public static ShaderProgram coreShader = new ShaderProgram(Gdx.files.internal("assets/voxelcraft/shaders/core.vert"), Gdx.files.internal("assets/voxelcraft/shaders/core.frag"));
}
