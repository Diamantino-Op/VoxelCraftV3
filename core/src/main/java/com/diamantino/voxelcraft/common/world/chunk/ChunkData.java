package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.blocks.Blocks;

/**
 * The chunk data class.
 *
 * @author Diamantino
 */
public class ChunkData {
    /**
     * The chunk instance.
     */
    private final Chunk chunk;

    /**
     * The layers of the chunk.
     */
    private final IChunkLayer[] chunkLayers = new IChunkLayer[Chunk.sizeY];

    /**
     * The constructor for the chunk data.
     * @param chunk The chunk instance.
     */
    public ChunkData(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Get a block from a local position.
     * @param localPos The local position.
     * @return The block at the specified position.
     */
    public Block getBlock(BlockPos localPos) {
        if (localPos.y() < 0 || localPos.y() >= Chunk.sizeY)
            return Blocks.air;

        IChunkLayer layer = chunkLayers[localPos.y()];
        return layer != null ? layer.getBlock(localPos.x(), localPos.z()) : Blocks.air;
    }

    /**
     * Get a block ID from a local position.
     * @param localPos The local position.
     * @return The block ID at the specified position.
     */
    public short getBlockId(BlockPos localPos) {
        if (localPos.y() < 0 || localPos.y() >= Chunk.sizeY)
            return Blocks.air.id;

        IChunkLayer layer = chunkLayers[localPos.y()];
        return layer != null ? layer.getBlockId(localPos.x(), localPos.z()) : Blocks.air.id;
    }

    /**
     * Replace a layer at the specified local Y coordinate.
     * @param layer The new layer.
     * @param y The Y coordinate of the layer.
     */
    public void setLayer(IChunkLayer layer, byte y) {
        chunkLayers[y] = layer;
    }

    /**
     * Get the layer at the specified local Y coordinate.
     * @param y The Y coordinate of the layer.
     * @return The layer instance.
     */
    public IChunkLayer getLayer(byte y) {
        return chunkLayers[y];
    }

    /**
     * @return The array of chunk layers.
     */
    public IChunkLayer[] getLayers() {
        return chunkLayers;
    }

    /**
     * Replace a block at a specified position.
     * @param block The block to place.
     * @param localPos The local position of the block.
     */
    // TODO: Maybe optimize?
    public void setBlock(Block block, BlockPos localPos) {
        if (chunkLayers[localPos.y()] != null) {
            if (chunkLayers[localPos.y()] instanceof SingleBlockChunkLayer singleLayer) {
                if (singleLayer.getBlock() != block) {
                    chunkLayers[localPos.y()] = new ChunkLayer(singleLayer);

                    chunkLayers[localPos.y()].setBlock(block, localPos.x(), localPos.z());
                }
            } else {
                chunkLayers[localPos.y()].setBlock(block, localPos.x(), localPos.z());

                if (chunkLayers[localPos.y()].getBlock(0, 0) == block)
                    tryCompactLayer((byte) localPos.y());
            }
        } else {
            chunkLayers[localPos.y()] = new SingleBlockChunkLayer(chunk, block);
        }
    }

    /**
     * Tries to compact a layer into a single block layer.
     * @param localY The local Y coordinate of the layer that needs to be compacted.
     * @return True if the layer has been changed else false.
     */
    public boolean tryCompactLayer(byte localY) {
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
                chunkLayers[localY] = new SingleBlockChunkLayer(this.chunk, Blocks.blocks.get(prevId));
                changed = true;
            }
        }

        return changed;
    }
}
