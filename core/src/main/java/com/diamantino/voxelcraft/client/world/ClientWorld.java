package com.diamantino.voxelcraft.client.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.diamantino.voxelcraft.client.shaders.Shaders;
import com.diamantino.voxelcraft.client.utils.TextureManager;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.networking.packets.c2s.RequestChunkPacket;
import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;

/**
 * Client-side world class.
 *
 * @author Diamantino
 */
public class ClientWorld extends World {
    /**
     * The name of the dimension the player is currently in.
     */
    public String currentDimension = "";

    /**
     * Client world class constructor.
     * @param settings The settings of the world.
     */
    public ClientWorld(WorldSettings settings) {
        super(settings);
    }

    /**
     * Check if the dimension exists.
     * @param dimensionName The name of the dimension.
     */
    private boolean checkDimension(String dimensionName) {
        if (!dimensionMap.containsKey(dimensionName)) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag, "Dimension " + dimensionName + " does not exist.");

            return false;
        }

        return true;
    }

    /**
     * The method to get the block for a position.
     * @param pos The position of the block.
     * @return The block for the position.
     */
    public Block getBlock(BlockPos pos) {
        if (!checkDimension(currentDimension))
            return null;

        return dimensionMap.get(currentDimension).getBlock(pos);
    }

    /**
     * Renders the chunks in the ChunkMap.
     * @param projMatrix The Matrix4 on which to render the chunks.
     */
    public void renderChunks(Matrix4 projMatrix) {
        if (!checkDimension(currentDimension))
            return;

        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl.glCullFace(GL20.GL_NONE);
        Gdx.gl.glFrontFace(GL20.GL_CW);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        ((Texture) TextureManager.graphicalObjects.get("block_atlas")).bind();
        Shaders.coreShader.bind();
        Shaders.coreShader.setUniformMatrix("u_projTrans", projMatrix);
        Shaders.coreShader.setUniformi("u_texture", 0);

        dimensionMap.get(currentDimension).chunkMap.forEach((pos, chunk) -> {
            if (chunk instanceof ClientChunk clientChunk)
                clientChunk.render();
        });
    }

    /**
     * Dispose the chunks in the ChunkMap.
     */
    public void dispose(String dimensionName) {
        if (!checkDimension(currentDimension))
            return;

        dimensionMap.get(dimensionName).chunkMap.forEach((chunkPos, chunk) -> {
            if (chunk instanceof ClientChunk clientChunk)
                clientChunk.dispose();
        });
    }
}
