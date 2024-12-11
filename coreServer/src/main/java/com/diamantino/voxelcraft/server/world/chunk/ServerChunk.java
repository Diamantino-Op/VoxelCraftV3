package com.diamantino.voxelcraft.server.world.chunk;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.registration.Blocks;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.server.world.ServerWorld;
import com.sudoplay.joise.module.SourcedModule;

/**
 * Server-side chunk class.
 * <p>
 * See {@link Chunk} for the common functions between client and server.
 *
 * @author Diamantino
 */
public class ServerChunk extends Chunk {
    /**
     * Does the chunk need generation?
     */
    public boolean isGenerated;

    /**
     * Constructor for the server-side chunk.
     *
     * @param world    The world instance.
     * @param chunkPos The chunk position.
     */
    public ServerChunk(World world, ChunkPos chunkPos) {
        super(world, chunkPos);

        this.isGenerated = false;
    }

    /**
     * Generate the chunk blocks.
     * @param dimName The dimension name.
     * @param noiseModule The noise instance from world.
     * @param world The world instance.
     */
    public void generate(String dimName, SourcedModule noiseModule, ServerWorld world) {
        //TODO Add params
        for (byte x = 0; x < Chunk.sizeX; x++) {
            for (byte z = 0; z < Chunk.sizeZ; z++) {
                BlockPos worldPos = world.chunkPosToWorldPos(new BlockPos(x, 0, z), this.chunkPos);
                byte y = (byte) Math.round(noiseModule.get(worldPos.x(), worldPos.z()));

                short worldY = (short) (y + (chunkPos.y() * Chunk.sizeY));

                for (short i = worldY; i > 0; i--) {
                    // TODO: Diff blocks
                    world.setBlock(dimName, Blocks.stone.getBlockInstance(), new BlockPos(worldPos.x(), worldY, worldPos.z()));
                }
            }
        }

        this.isGenerated = true;

        sendSyncPacket();
    }

    /**
     * Send the chunk sync packet to the players nearby.
     */
    public void sendSyncPacket() {
        //TODO
    }
}
