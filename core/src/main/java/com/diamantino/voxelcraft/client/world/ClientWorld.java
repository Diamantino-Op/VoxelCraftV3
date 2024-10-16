package com.diamantino.voxelcraft.client.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.diamantino.voxelcraft.client.shaders.Shaders;
import com.diamantino.voxelcraft.client.utils.AtlasManager;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
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
     * Client world class constructor.
     * @param settings The settings of the world.
     */
    public ClientWorld(WorldSettings settings) {
        super(settings);
    }

    /**
     * @param chunkPos The position of the chunk.
     * @return The chunk instance at the selected position.
     */
    @Override
    public Chunk getChunkForPos(ChunkPos chunkPos) {
        return super.getChunkForPos(chunkPos);
    }

    /**
     * @param chunkPos The position of the chunk.
     * @return A new client chunk instance.
     */
    //TODO: Probably load the data.
    @Override
    public Chunk getChunkForSide(ChunkPos chunkPos) {
        if (!chunkMap.containsKey(chunkPos)) {
            chunkMap.put(chunkPos, new ClientChunk(this, chunkPos));

            Packets.sendToServer(new RequestChunkPacket(chunkPos));
        }

        return chunkMap.get(chunkPos);
    }

    /**
     * Renders the chunks in the ChunkMap.
     * @param projMatrix The Matrix4 on which to render the chunks.
     */
    public void renderChunks(Matrix4 projMatrix) {
        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glEnable(GL20.GL_CULL_FACE);
        Gdx.gl.glCullFace(GL20.GL_NONE);
        Gdx.gl.glFrontFace(GL20.GL_CW);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        AtlasManager.blockAtlasTexture.bind();
        Shaders.coreShader.bind();
        Shaders.coreShader.setUniformMatrix("u_projTrans", projMatrix);
        Shaders.coreShader.setUniformi("u_texture", 0);

        chunkMap.forEach((pos, chunk) -> {
            if (chunk instanceof ClientChunk clientChunk)
                clientChunk.render();
        });
    }

    /**
     * Dispose the chunks in the ChunkMap.
     */
    public void dispose() {
        chunkMap.forEach((chunkPos, chunk) -> {
            if (chunk instanceof ClientChunk clientChunk)
                clientChunk.dispose();
        });
    }
}
