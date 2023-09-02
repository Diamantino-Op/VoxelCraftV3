package com.diamantino.voxelcraft.world.chunk;

import com.diamantino.voxelcraft.blocks.Block;
import com.diamantino.voxelcraft.blocks.Blocks;

public class ChunkData {
    private final Chunk chunk;
    private final IChunkLayer[] chunkLayers = new IChunkLayer[Chunk.sizeY];

    public ChunkData(Chunk chunk) {
        this.chunk = chunk;
    }

    public Block getBlock(int localX, int localY, int localZ) {
        if (localY < Chunk.sizeY || localY >= Chunk.sizeY)
            return Blocks.air;

        IChunkLayer layer = chunkLayers[localY];
        return layer != null ? layer.getBlock(localX, localZ) : Blocks.air;
    }

    public short getBlockId(int localX, int localY, int localZ) {
        if (localY < Chunk.sizeY || localY >= Chunk.sizeY)
            return Blocks.air.id;

        IChunkLayer layer = chunkLayers[localY];
        return layer != null ? layer.getBlockId(localX, localZ) : Blocks.air.id;
    }

    // TODO: Maybe optimize?
    public void setBlock(Block block, int localX, int localY, int localZ) {
        if (chunkLayers[localY] != null) {
            if (chunkLayers[localY] instanceof SingleBlockChunkLayer singleLayer) {
                if (singleLayer.getBlock() != block) {
                    chunkLayers[localY] = new ChunkLayer(singleLayer);

                    chunkLayers[localY].setBlock(block, localX, localZ);
                }
            } else {
                chunkLayers[localY].setBlock(block, localX, localZ);

                tryCompactLayer(localY);
            }
        } else {
            chunkLayers[localY] = new SingleBlockChunkLayer(chunk, block, localY);
        }
    }

    public IChunkLayer getChunkLayer(int localY) {
        return chunkLayers[localY];
    }

    public void setChunkLayer(IChunkLayer layer, int localY) {
        chunkLayers[localY] = layer;
    }

    public boolean tryCompactLayer(int localY) {
        boolean changed = false;

        IChunkLayer layer = chunkLayers[localY];
        short prevId = -1;

        if (layer instanceof ChunkLayer chunkLayer) {
            boolean sameBlock = true;

            for (short id : chunkLayer.getBlockList()) {
                if (prevId != -1) {
                    if (id != prevId) {
                        sameBlock = false;
                        break;
                    }
                } else {
                    prevId = id;
                }
            }

            if (sameBlock) {
                chunkLayers[localY] = new SingleBlockChunkLayer(this.chunk, Blocks.blocks.get(prevId), localY);
                changed = true;
            }
        }

        return changed;
    }
}
