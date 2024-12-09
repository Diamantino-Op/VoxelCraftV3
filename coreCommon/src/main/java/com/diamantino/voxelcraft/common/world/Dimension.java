package com.diamantino.voxelcraft.common.world;

import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;

import java.util.HashMap;
import java.util.Map;

/**
 * Dimension class.
 *
 * @author Diamantino
 */
public abstract class Dimension {
    /**
     * The world of the dimension.
     */
    public World world;

    /**
     * The name of the dimension.
     */
    public String name;

    /**
     * The map of chunks in the dimension.
     */
    public final Map<ChunkPos, Chunk> chunkMap = new HashMap<>();

    /**
     * The constructor of the dimension.
     *
     * @param name The name of the dimension.
     * @param world The world of the dimension.
     */
    public Dimension(String name, World world) {
        this.world = world;
        this.name = name;
    }

    /**
     * The method to get the chunk position for a block position.
     *
     * @param blockPos The position of the block.
     */
    public ChunkPos getChunkPosForBlockPos(BlockPos blockPos) {
        int chunkX = blockPos.x() / Chunk.sizeX;
        int chunkY = blockPos.y() / Chunk.sizeY;
        int chunkZ = blockPos.z() / Chunk.sizeZ;

        return new ChunkPos(chunkX, chunkY, chunkZ);
    }

    /**
     * The method to get the world position for a local position and a chunk position.
     *
     * @param localPos The local position.
     * @param chunkPos The chunk position.
     * @return The position relative to the world.
     */
    public BlockPos chunkPosToWorldPos(BlockPos localPos, ChunkPos chunkPos) {
        return new BlockPos(localPos.x() + (chunkPos.x() * Chunk.sizeX), localPos.y() + (chunkPos.y() * Chunk.sizeY), localPos.z() + (chunkPos.z() * Chunk.sizeZ));
    }

    /**
     * The method to get the chunk position for a world position.
     *
     * @param worldPos The world position.
     * @return The chunk position.
     */
    public BlockPos worldPosToChunkPos(BlockPos worldPos) {
        return new BlockPos(worldPos.x() % Chunk.sizeX, worldPos.y() % Chunk.sizeY, worldPos.z() % Chunk.sizeZ);
    }

    /**
     * The method to get the chunk for a position.
     *
     * @param chunkPos The position of the chunk.
     * @return The chunk for the position.
     */
    public abstract Chunk getChunkForPos(ChunkPos chunkPos);

    /**
     * The method to get the chunk for a block position.
     *
     * @param blockPos The position of the block.
     * @return The chunk for the block position.
     */
    public Chunk getChunkForBlockPos(BlockPos blockPos) {
        return getChunkForPos(getChunkPosForBlockPos(blockPos));
    }

    /**
     * The method to get the block for a position.
     *
     * @param pos The position of the block.
     * @return The block for the position.
     */
    public Block getBlock(BlockPos pos) {
        int x = pos.x() % Chunk.sizeX;
        int y = pos.y() % Chunk.sizeY;
        int z = pos.z() % Chunk.sizeZ;

        return getChunkForBlockPos(pos).getBlockAt(new BlockPos(x, y, z));
    }

    /**
     * The method to set the block for a position.
     *
     * @param block The block to set.
     * @param pos The position of the block.
     */
    public void setBlock(Block block, BlockPos pos) {
        int x = pos.x() % Chunk.sizeX;
        int y = pos.y() % Chunk.sizeY;
        int z = pos.z() % Chunk.sizeZ;

        getChunkForBlockPos(pos).setBlockAt(block, new BlockPos(x, y, z));

        setBlockPost(pos);
    }

    /**
     * Method called after setting a block.
     *
     * @param pos The position of the block.
     */
    protected abstract void setBlockPost(BlockPos pos);
}
