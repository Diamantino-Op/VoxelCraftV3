package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;

public interface IChunkLayer {
    Block getBlock(int localX, int localZ);
    short getBlockId(int localX, int localZ);
    void setBlock(Block block, int localX, int localZ);
    Chunk getChunk();
}
