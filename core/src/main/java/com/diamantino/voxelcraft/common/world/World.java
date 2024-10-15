package com.diamantino.voxelcraft.common.world;

import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.client.world.ClientWorld;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.entities.Entity;
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
     * The map of chunks in the world.
     */
    public final Map<ChunkPos, Chunk> chunkMap = new HashMap<>();

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
     */
    public ChunkPos getChunkPosForBlockPos(BlockPos blockPos) {
        int chunkX = blockPos.x() / Chunk.sizeX;
        int chunkY = blockPos.y() / Chunk.sizeY;
        int chunkZ = blockPos.z() / Chunk.sizeZ;

        return new ChunkPos(chunkX, chunkY, chunkZ);
    }

    /**
     * The method to get the chunk for a game side (Server or Client).
     * @param pos The position of the chunk.
     * @return The chunk for the side.
     */
    public Chunk getChunkForSide(ChunkPos pos) {
        return null;
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
     * @param chunkPos The position of the chunk.
     * @return The chunk for the position.
     */
    public Chunk getChunkForPos(ChunkPos chunkPos) {
        return chunkMap.containsKey(chunkPos) ? chunkMap.get(chunkPos) : chunkMap.put(chunkPos, getChunkForSide(chunkPos));
    }

    /**
     * The method to get the chunk for a block position.
     * @param blockPos The position of the block.
     * @return The chunk for the block position.
     */
    public Chunk getChunkForBlockPos(BlockPos blockPos) {
        return getChunkForPos(getChunkPosForBlockPos(blockPos));
    }

    /**
     * The method to get the block for a position.
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
     * @param block The block to set.
     * @param pos The position of the block.
     */
    public void setBlock(Block block, BlockPos pos) {
        int x = pos.x() % Chunk.sizeX;
        int y = pos.y() % Chunk.sizeY;
        int z = pos.z() % Chunk.sizeZ;

        getChunkForBlockPos(pos).setBlockAt(block, new BlockPos(x, y, z));

        if (this instanceof ClientWorld) {
            ((ClientChunk) getChunkForBlockPos(pos)).regenerateMesh();
        } else if (this instanceof ServerWorld) {
            ((ServerChunk) getChunkForBlockPos(pos)).sendSyncPacket();
        }
    }
}
