package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.blocks.Blocks;
import com.diamantino.voxelcraft.common.vdo.ArrayVDO;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;

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
     * @param compoundVDO The compound VDO loaded from the chunk file.
     */
    public void loadBlockData(CompoundVDO compoundVDO) {
        CompoundVDO blockMapVDO = compoundVDO.getCompoundVDO("blockMap");
        ArrayVDO mapKeys = blockMapVDO.getArrayVDO("keys");
        ArrayVDO mapValues = blockMapVDO.getArrayVDO("values");

        for (int i = 0; i < mapKeys.getContent().length(); i++) {
            short key = (short) mapKeys.getIntVDO(i);
            String value = mapValues.getStringVDO(i);
            blockMap.put(key, value);
        }

        ArrayVDO layers = compoundVDO.getArrayVDO("layers");

        for (int i = 0; i < layers.getContent().length(); i++) {
            CompoundVDO layerVDO = layers.getCompoundVDO(i);
            String layerType = layerVDO.getStringVDO("type");

            if (layerType.equals("single")) {
                chunkLayers[i] = new SingleBlockChunkLayer(this.chunk, layerVDO);
            } else {
                chunkLayers[i] = new ChunkLayer(this.chunk, layerVDO);
            }
        }
    }

    /**
     * Saves the block data.
     *
     * @return The compound VDO that needs to be saved in the chunk.
     */
    public CompoundVDO saveBlockData() {
        CompoundVDO blockDataVDO = new CompoundVDO();

        CompoundVDO blockMapVDO = new CompoundVDO();
        ArrayVDO mapKeys = new ArrayVDO();
        ArrayVDO mapValues = new ArrayVDO();

        int i = 0;
        for (Map.Entry<Short, String> entry : blockMap.entrySet()) {
            mapKeys.setIntVDO(i, entry.getKey());
            mapValues.setStringVDO(i, entry.getValue());

            i++;
        }

        blockMapVDO.setArrayVDO("keys", mapKeys);
        blockMapVDO.setArrayVDO("values", mapValues);
        blockDataVDO.setCompoundVDO("blockMap", blockMapVDO);

        ArrayVDO layers = new ArrayVDO();

        for (int j = 0; j < chunkLayers.length; j++) {
            IChunkLayer layer = chunkLayers[j];
            CompoundVDO layerVDO = new CompoundVDO();

            if (layer instanceof SingleBlockChunkLayer singleLayer) {
                layerVDO.setStringVDO("type", "single");

                layerVDO = singleLayer.saveLayerData();
            } else if (layer instanceof ChunkLayer chunkLayer) {
                layerVDO.setStringVDO("type", "multi");

                layerVDO = chunkLayer.saveLayerData();
            }

            layers.setCompoundVDO(j, layerVDO);
        }

        blockDataVDO.setArrayVDO("layers", layers);

        return blockDataVDO;
    }
}
