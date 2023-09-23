package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.server.world.chunk.ServerChunk;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex2DVariant;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex3DVariant;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex4DVariant;
import de.articdive.jnoise.pipeline.JNoise;

/**
 * The server-side world.
 * <p>
 * See {@link World} for the common functions between client and server.
 *
 * @author Diamantino
 */
public class ServerWorld extends World {
    public final JNoise noise;

    public ServerWorld(String name, WorldSettings settings) {
        super(name, settings);

        this.noise = JNoise.newBuilder().superSimplex(settings.seed(), Simplex2DVariant.CLASSIC, Simplex3DVariant.CLASSIC, Simplex4DVariant.CLASSIC).build();
    }

    @Override
    public Chunk getChunkForPos(ChunkPos chunkPos) {
        return super.getChunkForPos(chunkPos);
    }

    public BlockPos chunkPosToWorldPos(BlockPos localPos, ChunkPos chunkPos) {
        return new BlockPos(localPos.x() + (chunkPos.x() * Chunk.sizeX), localPos.y() + (chunkPos.y() * Chunk.sizeY), localPos.z() + (chunkPos.z() * Chunk.sizeZ));
    }

    public BlockPos worldPosToChunkPos(BlockPos worldPos) {
        return new BlockPos(worldPos.x() % Chunk.sizeX, worldPos.y() % Chunk.sizeY, worldPos.z() % Chunk.sizeZ);
    }

    @Override
    public Chunk getChunkForSide(ChunkPos pos) {
        return new ServerChunk(this, pos);
    }

    /**
     * Generate the chunk blocks.
     *
     * @param chunk The chunk that needs to be generated.
     */
    public void generateChunk(ServerChunk chunk) {
        if (!chunk.isGenerated) {
            chunk.generate(noise, this);
        }
    }
}
