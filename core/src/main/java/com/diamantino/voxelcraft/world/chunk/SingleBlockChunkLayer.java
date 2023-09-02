package com.diamantino.voxelcraft.world.chunk;

import com.diamantino.voxelcraft.blocks.Block;

public class SingleBlockChunkLayer implements IChunkLayer {
    private final Chunk chunk;
    private Block block;
    private final int yLevel;

    public SingleBlockChunkLayer(Chunk chunk, Block block, int yLevel) {
        this.chunk = chunk;
        this.block = block;
        this.yLevel = yLevel;
    }

    @Override
    public Block getBlock(int localX, int localZ) {
        return block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public short getBlockId(int localX, int localZ) {
        return block.id;
    }

    @Override
    public void setBlock(Block block, int localX, int localZ) {
        this.block = block;
    }

    @Override
    public int getLocalYLevel() {
        return yLevel;
    }

    @Override
    public Chunk getChunk() {
        return chunk;
    }
}
