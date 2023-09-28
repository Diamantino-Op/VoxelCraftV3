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
    public final Map<ChunkPos, Chunk> chunkMap = new HashMap<>();
    public final WorldSettings settings;

    public final short worldHeight = Chunk.sizeY * 32;

    public World(WorldSettings settings) {
        this.settings = settings;
    }

    public ChunkPos getChunkPosForBlockPos(BlockPos blockPos) {
        int chunkX = blockPos.x() / Chunk.sizeX;
        int chunkY = blockPos.y() / Chunk.sizeY;
        int chunkZ = blockPos.z() / Chunk.sizeZ;

        return new ChunkPos(chunkX, chunkY, chunkZ);
    }

    public Chunk getChunkForSide(ChunkPos pos) {
        return null;
    }

    public BlockPos chunkPosToWorldPos(BlockPos localPos, ChunkPos chunkPos) {
        return new BlockPos(localPos.x() + (chunkPos.x() * Chunk.sizeX), localPos.y() + (chunkPos.y() * Chunk.sizeY), localPos.z() + (chunkPos.z() * Chunk.sizeZ));
    }

    public BlockPos worldPosToChunkPos(BlockPos worldPos) {
        return new BlockPos(worldPos.x() % Chunk.sizeX, worldPos.y() % Chunk.sizeY, worldPos.z() % Chunk.sizeZ);
    }

    public Chunk getChunkForPos(ChunkPos chunkPos) {
        return chunkMap.containsKey(chunkPos) ? chunkMap.get(chunkPos) : chunkMap.put(chunkPos, getChunkForSide(chunkPos));
    }

    public Chunk getChunkForBlockPos(BlockPos blockPos) {
        return getChunkForPos(getChunkPosForBlockPos(blockPos));
    }

    public Block getBlock(BlockPos pos) {
        int x = pos.x() % Chunk.sizeX;
        int y = pos.y() % Chunk.sizeY;
        int z = pos.z() % Chunk.sizeZ;

        return getChunkForBlockPos(pos).getBlockAt(new BlockPos(x, y, z));
    }

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
