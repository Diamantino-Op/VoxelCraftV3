package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;
import dev.ultreon.ubo.types.MapType;

/**
 * Base interface for a chunk layer.
 *
 * @author Diamantino
 */
public interface IChunkLayer {
    /**
     * Gets the block at the specified position.
     * @param localX The local x position.
     * @param localZ The local z position.
     * @return The block at the specified position.
     */
    Block getBlock(int localX, int localZ);

    /**
     * Gets the block id at the specified position.
     * @param localX The local x position.
     * @param localZ The local z position.
     * @return The block id at the specified position.
     */
    String getBlockName(int localX, int localZ);

    /**
     * Sets the block at the specified position.
     * @param block The block to set.
     * @param localX The local x position.
     * @param localZ The local z position.
     */
    void setBlock(Block block, int localX, int localZ);

    /**
     * Gets the chunk this layer belongs to.
     * @return The chunk this layer belongs to.
     */
    Chunk getChunk();

    /**
     * Loads the layer data.
     *
     * @param layerData The compound VDO to load the data from.
     */
    void loadLayerData(MapType layerData);

    /**
     * Saves the layer data.
     *
     * @return The compound VDO to save the data to.
     */
    MapType saveLayerData();
}
