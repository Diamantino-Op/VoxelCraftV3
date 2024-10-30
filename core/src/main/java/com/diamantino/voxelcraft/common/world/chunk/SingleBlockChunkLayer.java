package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.registration.Blocks;
import com.diamantino.voxelcraft.common.utils.MathUtils;
import dev.ultreon.ubo.types.MapType;

import java.util.Map;
import java.util.Objects;

public class SingleBlockChunkLayer implements IChunkLayer {
    /**
     * The chunk this layer belongs to.
     */
    private final Chunk chunk;

    /**
     * The id of the block the layer is made of.
     */
    private short blockId;

    /**
     * Creates a new single block chunk layer.
     * @param chunk The chunk this layer belongs to.
     * @param block The block to fill the layer with.
     */
    public SingleBlockChunkLayer(Chunk chunk, Block block) {
        this.chunk = chunk;

        for (Map.Entry<Short, String> entry : chunk.getData().getBlockMap().entrySet()) {
            if (Objects.equals(block.name, entry.getValue())) {
                this.blockId = entry.getKey();
                return;
            }
        }

        short nextId = MathUtils.getFirstAvailableShort(chunk.getData().getBlockMap());

        chunk.getData().getBlockMap().put(nextId, block.name);
        this.blockId = nextId;
    }

    /**
     * Creates a new single block chunk layer.
     * @param chunk The chunk this layer belongs to.
     * @param data The data to load the layer from.
     */
    public SingleBlockChunkLayer(Chunk chunk, MapType data) {
        this.chunk = chunk;

        loadLayerData(data);
    }

    /**
     * Gets the block at the specified position.
     * @param localX The local x position.
     * @param localZ The local z position.
     * @return The block at the specified position.
     */
    @Override
    public Block getBlock(int localX, int localZ) {
        if (localX >= Chunk.sizeX || localX < 0 || localZ >= Chunk.sizeZ ||  localZ < 0) {
            return Blocks.air.getBlockInstance();
        } else {
            return Blocks.blocks.get(chunk.getData().getBlockMap().get(blockId)).getBlockInstance();
        }
    }

    /**
     * Gets the block that the layer is made of.
     * @return The block that the layer is made of.
     */
    public Block getBlock() {
        return Blocks.blocks.get(chunk.getData().getBlockMap().get(blockId)).getBlockInstance();
    }

    /**
     * Gets the block id at the specified position.
     * @param localX The local x position.
     * @param localZ The local z position.
     * @return The block id at the specified position.
     */
    @Override
    public String getBlockName(int localX, int localZ) {
        if (localX >= Chunk.sizeX || localX < 0 || localZ >= Chunk.sizeZ || localZ < 0) {
            return Blocks.air.getRegistryName();
        } else {
            return Blocks.blocks.get(chunk.getData().getBlockMap().get(blockId)).getRegistryName();
        }
    }

    /**
     * Sets the block at the specified position.
     * @param block The block to set.
     * @param localX The local x position.
     * @param localZ The local z position.
     */
    @Override
    public void setBlock(Block block, int localX, int localZ) {
        if (!(localX >= Chunk.sizeX || localX < 0 || localZ >= Chunk.sizeZ ||  localZ < 0)) {
            for (Map.Entry<Short, String> entry : chunk.getData().getBlockMap().entrySet()) {
                if (Objects.equals(block.name, entry.getValue())) {
                    blockId = entry.getKey();
                    return;
                }
            }

            short nextId = MathUtils.getFirstAvailableShort(chunk.getData().getBlockMap());

            chunk.getData().getBlockMap().put(nextId, block.name);
            blockId = nextId;
        }
    }

    /**
     * Gets the chunk this layer belongs to.
     * @return The chunk this layer belongs to.
     */
    @Override
    public Chunk getChunk() {
        return chunk;
    }

    /**
     * Loads the layer data.
     *
     * @param layerData The compound VDO to load the data from.
     */
    @Override
    public void loadLayerData(MapType layerData) {
        this.blockId = layerData.getShort("blockId");
    }

    /**
     * Saves the layer data.
     *
     * @return The compound VDO to save the data to.
     */
    @Override
    public MapType saveLayerData() {
        MapType layerData = new MapType();

        layerData.putShort("blockId", this.blockId);

        return layerData;
    }
}
