package com.diamantino.voxelcraft.server.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.World;

public class Chunk {
    public static final int sizeX = 32;
    public static final int sizeY = 32;
    public static final int sizeZ = 32;
    public static final int blocksInChunk = sizeX * sizeY * sizeZ;
    protected final ChunkData chunkData;

    protected final World world;
    public final ChunkPos chunkPos;

    public Chunk(World world, ChunkPos chunkPos) {
        this.world = world;
        this.chunkPos = chunkPos;

        this.chunkData = new ChunkData(this);
    }

    public void setBlockAt(Block block, BlockPos localPos, boolean regenerateMesh) {
        chunkData.setBlock(block, localPos);

        // TODO: Send packet
        /*if (regenerateMesh) {
            regenerateMesh();
        }*/
    }

    public void setLayer(IChunkLayer layer, int y) {
        chunkData.setLayer(layer, y);
    }

    public IChunkLayer getLayer(int y) {
        return chunkData.getLayer(y);
    }

    public Block getBlockAt(BlockPos localPos) {
        return chunkData.getBlock(localPos);
    }
}
