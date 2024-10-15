package com.diamantino.voxelcraft.common.world.chunk;

import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.entities.Entity;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;
import com.diamantino.voxelcraft.common.world.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a chunk of the world.
 *
 * @author Diamantino
 */
public abstract class Chunk {
    /**
     * The size of the chunk in blocks.
     */
    public static final byte sizeX = 32;
    public static final byte sizeY = 32;
    public static final byte sizeZ = 32;

    /**
     * The total number of blocks in a chunk.
     */
    public static final int blocksInChunk = sizeX * sizeY * sizeZ;

    /**
     * The chunk's block data.
     */
    protected final ChunkBlockData chunkBlockData;

    /**
     * The world the chunk is in.
     */
    protected final World world;

    /**
     * The position of the chunk in the world.
     */
    public final ChunkPos chunkPos;

    /**
     * The map of entities in the world.
     */
    public final Map<Vector3, Entity> entityMap = new HashMap<>();

    /**
     * Creates a new chunk.
     *
     * @param world The world the chunk is in.
     * @param chunkPos The position of the chunk in the world.
     */
    public Chunk(World world, ChunkPos chunkPos) {
        this.world = world;
        this.chunkPos = chunkPos;

        this.chunkBlockData = new ChunkBlockData(this);
    }

    /**
     * Creates a new chunk.
     *
     * @param world The world the chunk is in.
     * @param chunkPos The position of the chunk in the world.
     */
    public Chunk(World world, ChunkPos chunkPos, CompoundVDO compoundVDO) {
        this(world, chunkPos);

        this.loadChunkData(compoundVDO);
    }

    /**
     * Sets the block at the specified position in the chunk.
     * @param block The block to set.
     * @param localPos The position in the chunk.
     */
    public void setBlockAt(Block block, BlockPos localPos) {
        chunkBlockData.setBlock(block, localPos);
    }

    /**
     * Gets the block at the specified position in the chunk.
     * @param localPos The position in the chunk.
     * @return The block at the specified position.
     */
    public Block getBlockAt(BlockPos localPos) {
        return chunkBlockData.getBlock(localPos);
    }

    /**
     * Sets the layer at the specified y position in the chunk.
     * @param layer The layer to set.
     * @param y The y position.
     */
    public void setLayer(IChunkLayer layer, byte y) {
        chunkBlockData.setLayer(layer, y);
    }

    /**
     * Gets the layer at the specified y position in the chunk.
     * @param y The y position.
     * @return The layer at the specified y position.
     */
    public IChunkLayer getLayer(byte y) {
        return chunkBlockData.getLayer(y);
    }

    /**
     * Gets the chunk's block data.
     * @return The chunk's block data.
     */
    public ChunkBlockData getData() {
        return chunkBlockData;
    }

    public void loadChunkData(CompoundVDO compoundVDO) {
        chunkBlockData.loadBlockData(compoundVDO.getCompoundVDO("blockData"));
    }

    public CompoundVDO saveChunkData() {
        CompoundVDO compoundVDO = new CompoundVDO();

        compoundVDO.setCompoundVDO("blockData", chunkBlockData.saveBlockData());

        return compoundVDO;
    }
}
