package com.diamantino.voxelcraft.server.world;

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
 * Server-side world class.
 * <p>
 * See {@link World} for the common functions between client and server.
 *
 * @author Diamantino
 */
public class ServerWorld extends World {
    /**
     * Noise generator for the world.
     */
    public final JNoise noise;

    public ServerWorld(String name, WorldSettings settings) {
        super(name, settings);

        this.noise = JNoise.newBuilder().superSimplex(settings.seed(), Simplex2DVariant.CLASSIC, Simplex3DVariant.CLASSIC, Simplex4DVariant.CLASSIC).build();
    }

    /**
     * Create the right chunk class for server-side.
     *
     * @param pos The position of the chunk.
     */
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
