package com.diamantino.voxelcraft.world.chunk;

import com.diamantino.voxelcraft.blocks.Block;
import com.diamantino.voxelcraft.blocks.Blocks;

import java.util.Arrays;

public class ChunkLayer implements IChunkLayer {
    private final Chunk chunk;
    private final int blocksPerLayer = Chunk.sizeX * Chunk.sizeZ;
    private final short[] blocksInLayer = new short[blocksPerLayer];
    private final int yLevel;

    public ChunkLayer(Chunk chunk, int yLevel) {
        this.chunk = chunk;
        this.yLevel = yLevel;
    }

    public ChunkLayer(SingleBlockChunkLayer layer) {
        this.chunk = layer.getChunk();
        this.yLevel = layer.getLocalYLevel();

        Arrays.fill(blocksInLayer, layer.getBlock().id);
    }

    @Override
    public Block getBlock(int localX, int localZ) {
        if (localX >= Chunk.sizeX || localZ >= Chunk.sizeZ) {
            int pos = localX + (localZ * Chunk.sizeX);
            return Blocks.blocks.get(blocksInLayer[pos]);
        } else {
            return Blocks.air;
        }
    }

    @Override
    public short getBlockId(int localX, int localZ) {
        if (localX >= Chunk.sizeX || localZ >= Chunk.sizeZ) {
            int pos = localX + (localZ * Chunk.sizeX);
            return blocksInLayer[pos];
        } else {
            return Blocks.air.id;
        }
    }

    @Override
    public void setBlock(Block block, int localX, int localZ) {
        if (localX >= Chunk.sizeX || localZ >= Chunk.sizeZ) {
            int pos = localX + (localZ * Chunk.sizeX);
            blocksInLayer[pos] = block.id;
        }
    }

    @Override
    public int getLocalYLevel() {
        return yLevel;
    }

    public short[] getBlockList() {
        return blocksInLayer;
    }

    @Override
    public Chunk getChunk() {
        return chunk;
    }
}
