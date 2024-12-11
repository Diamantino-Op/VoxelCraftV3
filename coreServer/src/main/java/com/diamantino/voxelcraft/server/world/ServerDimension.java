package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.Dimension;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.server.world.chunk.ServerChunk;
import com.sudoplay.joise.module.ModuleAutoCorrect;
import com.sudoplay.joise.module.ModuleBasisFunction;

public class ServerDimension extends Dimension {
    /**
     * The constructor of the dimension.
     *
     * @param name  The name of the dimension.
     * @param world The world of the dimension.
     */
    public ServerDimension(String name, ServerWorld world) {
        super(name, world);
    }

    /**
     * The method to get the chunk for a position.
     * @param chunkPos The position of the chunk.
     * @return The chunk for the position.
     */
    @Override
    public Chunk getChunkForPos(ChunkPos chunkPos) {
        return chunkMap.computeIfAbsent(chunkPos, pos -> {
            ServerChunk chunk = new ServerChunk(world, pos);

            generateChunk(name, chunk);

            return chunk;
        });
    }

    /**
     * Generate the chunk blocks.
     * @param dimName The name of the dimension.
     * @param chunk The chunk that needs to be generated.
     */
    public void generateChunk(String dimName, ServerChunk chunk) {
        //TODO: Add params
        ModuleBasisFunction startModule = new ModuleBasisFunction();
        startModule.setType(ModuleBasisFunction.BasisType.SIMPLEX);
        startModule.setSeed(world.settings.seed());

        ModuleAutoCorrect correctModule = new ModuleAutoCorrect();
        correctModule.setSource(startModule);
        correctModule.calculateAll();

        if (!chunk.isGenerated) {
            chunk.generate(dimName, correctModule, (ServerWorld) world);
        }
    }

    /**
     * Method called after setting a block.
     *
     * @param pos The position of the block.
     */
    @Override
    protected void setBlockPost(BlockPos pos) {
        ((ServerChunk) getChunkForBlockPos(pos)).sendSyncPacket();
    }
}
