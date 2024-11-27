package com.diamantino.voxelcraft.common.world;

import com.diamantino.voxelcraft.client.world.ClientWorld;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.server.world.ServerWorld;
import com.diamantino.voxelcraft.server.world.chunk.ServerChunk;

import java.util.HashMap;
import java.util.Map;

/**
 * The common world class.
 *
 * @author Diamantino
 */
public abstract class World {
    /**
     * The map of dimensions in the world.
     */
    public final Map<String, Dimension> dimensionMap = new HashMap<>();

    /**
     * The settings of the world.
     */
    public final WorldSettings settings;

    /**
     * The height of the world.
     */
    public final short worldHeight = Chunk.sizeY * 32;

    /**
     * The constructor of the world.
     *
     * @param settings The settings of the world.
     */
    public World(WorldSettings settings) {
        this.settings = settings;
    }

    /**
     * The method to get the chunk position for a block position.
     * @param dimensionName The name of the dimension.
     * @return The chunk position for the block position.
     */
    public ChunkPos getChunkPosForBlockPos(String dimensionName, BlockPos blockPos) {
        return dimensionMap.get(dimensionName).getChunkPosForBlockPos(blockPos);
    }

    /**
     * The method to get the world position for a local position and a chunk position.
     * @param localPos The local position.
     * @param chunkPos The chunk position.
     * @return The position relative to the world.
     */
    public BlockPos chunkPosToWorldPos(BlockPos localPos, ChunkPos chunkPos) {
        return new BlockPos(localPos.x() + (chunkPos.x() * Chunk.sizeX), localPos.y() + (chunkPos.y() * Chunk.sizeY), localPos.z() + (chunkPos.z() * Chunk.sizeZ));
    }

    /**
     * The method to get the chunk position for a world position.
     * @param worldPos The world position.
     * @return The chunk position.
     */
    public BlockPos worldPosToChunkPos(BlockPos worldPos) {
        return new BlockPos(worldPos.x() % Chunk.sizeX, worldPos.y() % Chunk.sizeY, worldPos.z() % Chunk.sizeZ);
    }

    /**
     * The method to get the chunk for a position.
     * @param dimensionName The name of the dimension.
     * @param chunkPos The position of the chunk.
     * @return The chunk for the position.
     */
    public Chunk getChunkForPos(String dimensionName, ChunkPos chunkPos) {
        return dimensionMap.get(dimensionName).getChunkForPos(chunkPos);
    }

    /**
     * The method to get the chunk for a block position.
     * @param dimensionName The name of the dimension.
     * @param blockPos The position of the block.
     * @return The chunk for the block position.
     */
    public Chunk getChunkForBlockPos(String dimensionName, BlockPos blockPos) {
        return getChunkForPos(dimensionName, getChunkPosForBlockPos(dimensionName, blockPos));
    }

    /**
     * The method to get the block for a position.
     * @param dimensionName The name of the dimension.
     * @param pos The position of the block.
     * @return The block for the position.
     */
    public Block getBlock(String dimensionName, BlockPos pos) {
        return dimensionMap.get(dimensionName).getBlock(pos);
    }

    /**
     * The method to set the block for a position.
     * @param dimensionName The name of the dimension.
     * @param block The block to set.
     * @param pos The position of the block.
     */
    public void setBlock(String dimensionName, Block block, BlockPos pos) {
        dimensionMap.get(dimensionName).setBlock(block, pos);
    }
}
