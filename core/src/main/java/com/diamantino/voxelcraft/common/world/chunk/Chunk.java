package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.World;

public class Chunk {
    public static final byte sizeX = 32;
    public static final byte sizeY = 32;
    public static final byte sizeZ = 32;
    public static final int blocksInChunk = sizeX * sizeY * sizeZ;
    protected final ChunkData chunkData;

    protected final World world;
    public final ChunkPos chunkPos;

    public Chunk(World world, ChunkPos chunkPos) {
        this.world = world;
        this.chunkPos = chunkPos;

        this.chunkData = new ChunkData(this);
    }

    public void setBlockAt(Block block, BlockPos localPos) {
        chunkData.setBlock(block, localPos);
    }

    public void setLayer(IChunkLayer layer, byte y) {
        chunkData.setLayer(layer, y);
    }

    public IChunkLayer getLayer(byte y) {
        return chunkData.getLayer(y);
    }

    public Block getBlockAt(BlockPos localPos) {
        return chunkData.getBlock(localPos);
    }
}
