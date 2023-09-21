package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.server.world.chunk.Chunk;
import com.diamantino.voxelcraft.server.world.chunk.ChunkPos;
import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;

public class ServerWorld extends World {
    public final JNoise noise;

    public ServerWorld(String name, WorldSettings settings) {
        super(name, settings);

        this.noise = JNoise.newBuilder().perlin(settings.seed(), Interpolation.COSINE, FadeFunction.QUINTIC_POLY).build();
    }

    @Override
    public Chunk getChunkForPos(ChunkPos chunkPos) {
        return super.getChunkForPos(chunkPos);
    }

    public void generateChunk(Chunk chunk) {

    }
}
