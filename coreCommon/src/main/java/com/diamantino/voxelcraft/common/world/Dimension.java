package com.diamantino.voxelcraft.common.world;

import com.diamantino.voxelcraft.client.world.ClientWorld;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.blocks.Block;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.networking.packets.c2s.RequestChunkPacket;
import com.diamantino.voxelcraft.common.networking.packets.Packets;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.server.world.ServerWorld;
import com.diamantino.voxelcraft.server.world.chunk.ServerChunk;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex2DVariant;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex3DVariant;
import de.articdive.jnoise.generators.noise_parameters.simplex_variants.Simplex4DVariant;
import de.articdive.jnoise.pipeline.JNoise;

import java.util.HashMap;
import java.util.Map;

/**
 * Dimension class.
 *
 * @author Diamantino
 */
public class Dimension {
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
     */
    public ChunkPos getChunkPosForBlockPos(BlockPos blockPos) {
        int chunkX = blockPos.x() / Chunk.sizeX;
        int chunkY = blockPos.y() / Chunk.sizeY;
        int chunkZ = blockPos.z() / Chunk.sizeZ;

        return new ChunkPos(chunkX, chunkY, chunkZ);
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
        if (world instanceof ServerWorld) {
            return chunkMap.computeIfAbsent(chunkPos, pos -> {
               ServerChunk chunk = new ServerChunk(world, pos);

               generateChunk(name, chunk);

               return chunk;
            });
        } else if (world instanceof ClientWorld) {
            if (!chunkMap.containsKey(chunkPos)) {
                chunkMap.put(chunkPos, new ClientChunk(world, chunkPos));

                Packets.sendToServer(new RequestChunkPacket(name, chunkPos));
            }

            return chunkMap.get(chunkPos);
        }

        return null;
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
     * Generate the chunk blocks.
     * @param dimName The name of the dimension.
     * @param chunk The chunk that needs to be generated.
     */
    public void generateChunk(String dimName, ServerChunk chunk) {
        //TODO: Add params
        JNoise noise = JNoise.newBuilder().superSimplex(world.settings.seed(), Simplex2DVariant.CLASSIC, Simplex3DVariant.CLASSIC, Simplex4DVariant.CLASSIC).build();

        if (!chunk.isGenerated) {
            chunk.generate(dimName, noise, (ServerWorld) world);
        }
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

        if (world instanceof ClientWorld) {
            ((ClientChunk) getChunkForBlockPos(pos)).regenerateMesh();
        } else if (world instanceof ServerWorld) {
            ((ServerChunk) getChunkForBlockPos(pos)).sendSyncPacket();
        }
    }
}
