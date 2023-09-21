package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.server.world.chunk.Chunk;
import com.diamantino.voxelcraft.server.world.chunk.ChunkPos;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex2DVariant;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex3DVariant;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex4DVariant;
import de.articdive.jnoise.pipeline.JNoise;

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

    public void generateChunk(Chunk chunk) {

    }
}
