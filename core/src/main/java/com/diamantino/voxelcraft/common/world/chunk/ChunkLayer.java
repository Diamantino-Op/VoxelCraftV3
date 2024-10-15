package com.diamantino.voxelcraft.common.world.chunk;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.Blocks;
import com.diamantino.voxelcraft.common.utils.MathUtils;
import com.diamantino.voxelcraft.common.vdo.ArrayVDO;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;
import org.json.JSONArray;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a layer of blocks in a chunk.
 *
 * @author Diamantino
 */
public class ChunkLayer implements IChunkLayer {
    /**
     * The chunk this layer belongs to.
     */
    private final Chunk chunk;

    /**
     * The number of blocks in a layer.
     */
    public final int blocksPerLayer = Chunk.sizeX * Chunk.sizeZ;

    /**
     * The blocks in this layer.
     */
    private final short[] blocksInLayer = new short[blocksPerLayer];

    /**
     * Creates a new chunk layer.
     *
     * @param chunk The chunk this layer belongs to.
     */
    public ChunkLayer(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Creates a new chunk layer.
     *
     * @param layer The single block layer to copy.
     */
    public ChunkLayer(SingleBlockChunkLayer layer) {
        this.chunk = layer.getChunk();

        for (Map.Entry<Short, String> entry : chunk.getData().getBlockMap().entrySet()) {
            if (Objects.equals(layer.getBlock().name, entry.getValue())) {
                Arrays.fill(blocksInLayer, entry.getKey());

                return;
            }
        }

        short nextId = MathUtils.getFirstAvailableShort(chunk.getData().getBlockMap());

        chunk.getData().getBlockMap().put(nextId, layer.getBlock().name);

        Arrays.fill(blocksInLayer, nextId);
    }

    /**
     * Creates a new chunk layer.
     * @param chunk The chunk this layer belongs to.
     * @param data The data to load the layer from.
     */
    public ChunkLayer(Chunk chunk, CompoundVDO data) {
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
            int pos = localX + (localZ * Chunk.sizeX);
            return Blocks.blocks.get(chunk.getData().getBlockMap().get(blocksInLayer[pos])).getBlockInstance();
        }
    }

    /**
     * Gets the block id at the specified position.
     * @param localX The local x position.
     * @param localZ The local z position.
     * @return The block id at the specified position.
     */
    @Override
    public String getBlockName(int localX, int localZ) {
        if (localX >= Chunk.sizeX || localX < 0 || localZ >= Chunk.sizeZ ||  localZ < 0) {
            return Blocks.air.getRegistryName();
        } else {
            int pos = localX + (localZ * Chunk.sizeX);
            return chunk.getData().getBlockMap().get(blocksInLayer[pos]);
        }
    }

    /**
     * Gets the block list.
     * @return The block list.
     */
    public short[] getBlockList() {
        return blocksInLayer;
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
            int pos = localX + (localZ * Chunk.sizeX);

            for (Map.Entry<Short, String> entry : chunk.getData().getBlockMap().entrySet()) {
                if (Objects.equals(block.name, entry.getValue())) {
                    blocksInLayer[pos] = entry.getKey();
                    return;
                }
            }

            short nextId = MathUtils.getFirstAvailableShort(chunk.getData().getBlockMap());

            chunk.getData().getBlockMap().put(nextId, block.name);
            blocksInLayer[pos] = nextId;
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
     * @param compoundVDO The compound VDO to load the data from.
     */
    @Override
    public void loadLayerData(CompoundVDO compoundVDO) {
        ArrayVDO blocksVDO = compoundVDO.getArrayVDO("blocks");

        for (int i = 0; i < blocksPerLayer; i++) {
            blocksInLayer[i] = (short) blocksVDO.getIntVDO(i);
        }
    }

    /**
     * Saves the layer data.
     *
     * @return The compound VDO to save the data to.
     */
    @Override
    public CompoundVDO saveLayerData() {
        CompoundVDO layerVDO = new CompoundVDO();
        ArrayVDO blocksVDO = new ArrayVDO();

        for (int i = 0; i < blocksPerLayer; i++) {
            blocksVDO.setIntVDO(i, blocksInLayer[i]);
        }

        layerVDO.setArrayVDO("blocks", blocksVDO);

        return layerVDO;
    }
}
