package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.Blocks;

public class SingleBlockChunkLayer implements IChunkLayer {
    private final Chunk chunk;
    private Block block;

    public SingleBlockChunkLayer(Chunk chunk, Block block) {
        this.chunk = chunk;
        this.block = block;
    }

    @Override
    public Block getBlock(int localX, int localZ) {
        if (localX >= Chunk.sizeX || localX < 0 || localZ >= Chunk.sizeZ ||  localZ < 0) {
            return Blocks.air;
        } else {
            return block;
        }
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public short getBlockId(int localX, int localZ) {
        if (localX >= Chunk.sizeX || localX < 0 || localZ >= Chunk.sizeZ || localZ < 0) {
            return Blocks.air.id;
        } else {
            return block.id;
        }
    }

    @Override
    public void setBlock(Block block, int localX, int localZ) {
        if (!(localX >= Chunk.sizeX || localX < 0 || localZ >= Chunk.sizeZ ||  localZ < 0)) {
            this.block = block;
        }
    }

    @Override
    public Chunk getChunk() {
        return chunk;
    }
}
