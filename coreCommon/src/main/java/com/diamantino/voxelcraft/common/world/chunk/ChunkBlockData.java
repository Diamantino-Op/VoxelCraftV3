package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.registration.Blocks;
import dev.ultreon.ubo.types.MapType;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the block data of a chunk.
 *
 * @author Diamantino
 */
public class ChunkBlockData {
    /**
     * The chunk this block data belongs to.
     */
    private final Chunk chunk;

    /**
     * The layers of the chunk.
     */
    private final IChunkLayer[] chunkLayers = new IChunkLayer[Chunk.sizeY];

    /**
     * The block map.
     * Key: Block ID.
     * Value: Block name.
     */
    private final Map<Short, String> blockMap = new HashMap<>();

    /**
     * Creates a new chunk block data.
     *
     * @param chunk The chunk this block data belongs to.
     */
    public ChunkBlockData(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Gets the block at the specified position.
     *
     * @param localPos The local position of the block.
     * @return The block at the specified position.
     */
    public Block getBlock(BlockPos localPos) {
        if (localPos.y() < 0 || localPos.y() >= Chunk.sizeY)
            return Blocks.air.getBlockInstance();

        IChunkLayer layer = chunkLayers[localPos.y()];
        return layer != null ? layer.getBlock(localPos.x(), localPos.z()) : Blocks.air.getBlockInstance();
    }

    /**
     * Gets the block ID at the specified position.
     *
     * @param localPos The local position of the block.
     * @return The block ID at the specified position.
     */
    public String getBlockName(BlockPos localPos) {
        if (localPos.y() < 0 || localPos.y() >= Chunk.sizeY)
            return Blocks.air.getRegistryName();

        IChunkLayer layer = chunkLayers[localPos.y()];
        return layer != null ? layer.getBlockName(localPos.x(), localPos.z()) : Blocks.air.getRegistryName();
    }

    /**
     * Gets the block map.
     *
     * @return The block map.
     */
    public Map<Short, String> getBlockMap() {
        return blockMap;
    }

    public void setBlock(Block block, BlockPos localPos) {
        int localY = localPos.y();
        IChunkLayer layer = chunkLayers[localY];

        if (layer != null) {
            if (layer instanceof SingleBlockChunkLayer singleLayer) {
                if (singleLayer.getBlock() != block) {
                    chunkLayers[localY] = new ChunkLayer(singleLayer);

                    chunkLayers[localY].setBlock(block, localPos.x(), localPos.z());
                }
            } else {
                layer.setBlock(block, localPos.x(), localPos.z());

                tryCompactLayer((byte) localY);
            }
        } else {
            chunkLayers[localY] = new SingleBlockChunkLayer(chunk, block);
        }
    }

    /**
     * Gets the layer at the specified y position.
     *
     * @param y The y position of the layer.
     * @return The layer at the specified y position.
     */
    public IChunkLayer getLayer(byte y) {
        return chunkLayers[y];
    }

    /**
     * Gets the layers of the chunk.
     *
     * @return The layers of the chunk.
     */
    public IChunkLayer[] getLayers() {
        return chunkLayers;
    }

    /**
     * Sets the layer at the specified y position.
     * @param layer The layer to set.
     * @param y The y position of the layer.
     */
    public void setLayer(IChunkLayer layer, byte y) {
        chunkLayers[y] = layer;
    }

    /**
     * Tries to compact the layer at the specified y position.
     *
     * @param localY The local y position of the layer.
     * @return Whether the layer was compacted.
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
                chunkLayers[localY] = new SingleBlockChunkLayer(this.chunk, Blocks.blocks.get(blockMap.get(prevId)).getBlockInstance());
                changed = true;
            }
        }

        return changed;
    }

    /**
     * Loads the block data.
     *
     * @param chunkBlockData The compound VDO loaded from the chunk file.
     */
    public void loadBlockData(MapType chunkBlockData) {
        MapType blockDictionaryMap = chunkBlockData.getMap("blockMap");
        short[] mapKeys = blockDictionaryMap.getShortArray("keys");

        for (int i = 0; i < mapKeys.length; i++) {
            String value = blockDictionaryMap.getString("value" + i);
            blockMap.put(mapKeys[i], value);
        }

        MapType layers = chunkBlockData.getMap("layers");

        for (int i = 0; i < layers.keys().size(); i++) {
            MapType blockLayerData = layers.getMap("layer" + i);
            String layerType = blockLayerData.getString("type");

            if (layerType.equals("single")) {
                chunkLayers[i] = new SingleBlockChunkLayer(this.chunk, blockLayerData);
            } else {
                chunkLayers[i] = new ChunkLayer(this.chunk, blockLayerData);
            }
        }
    }

    /**
     * Saves the block data.
     *
     * @return The compound VDO that needs to be saved in the chunk.
     */
    public MapType saveBlockData() {
        MapType layerData = new MapType();

        MapType blockDictionaryMap = new MapType();

        //TODO: Might create problems.
        short[] mapKeys = new short[blockMap.size()];
        System.arraycopy(blockMap.keySet().toArray(), 0, mapKeys, 0, blockMap.size());

        blockDictionaryMap.putShortArray("keys", mapKeys);

        int i = 0;
        for (Map.Entry<Short, String> entry : blockMap.entrySet()) {
            blockDictionaryMap.putString("value" + i, entry.getValue());

            i++;
        }

        layerData.put("blockMap", blockDictionaryMap);

        MapType layers = new MapType();

        for (int j = 0; j < chunkLayers.length; j++) {
            IChunkLayer layer = chunkLayers[j];
            MapType blockLayerData = new MapType();

            if (layer instanceof SingleBlockChunkLayer singleLayer) {
                blockLayerData = singleLayer.saveLayerData();

                blockLayerData.putString("type", "single");
            } else if (layer instanceof ChunkLayer chunkLayer) {
                blockLayerData = chunkLayer.saveLayerData();

                blockLayerData.putString("type", "multi");
            }

            layers.put("layer" + j, blockLayerData);
        }

        layerData.put("layers", layers);

        return layerData;
    }
}
