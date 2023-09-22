package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.blocks.Blocks;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.server.world.chunk.ServerChunk;
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

    public BlockPos chunkPosToWorldPos(BlockPos localPos, ChunkPos chunkPos) {
        return new BlockPos(localPos.x() + (chunkPos.x() * Chunk.sizeX), localPos.y() + (chunkPos.y() * Chunk.sizeY), localPos.z() + (chunkPos.z() * Chunk.sizeZ));
    }

    public BlockPos worldPosToChunkPos(BlockPos worldPos) {
        return new BlockPos(worldPos.x() % Chunk.sizeX, worldPos.y() % Chunk.sizeY, worldPos.z() % Chunk.sizeZ);
    }

    public void generateChunk(ServerChunk chunk) {
        for (byte x = 0; x < Chunk.sizeX; x++) {
            for (byte z = 0; z < Chunk.sizeZ; z++) {
                BlockPos worldPos = chunkPosToWorldPos(new BlockPos(x, 0, z), chunk.chunkPos);
                byte y = (byte) Math.round(noise.evaluateNoise(worldPos.x(), worldPos.z()));

                // TODO: Diff blocks
                chunk.setBlockAt(Blocks.stone, new BlockPos(x, y, z));
            }
        }

        chunk.sendSyncPacket();
    }
}
