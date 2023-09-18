package com.diamantino.voxelcraft.server.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;

public interface IChunkLayer {
    Block getBlock(int localX, int localZ);
    short getBlockId(int localX, int localZ);
    void setBlock(Block block, int localX, int localZ);
    int getLocalYLevel();
    Chunk getChunk();
}
