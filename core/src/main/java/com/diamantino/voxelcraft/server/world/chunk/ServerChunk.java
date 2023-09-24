package com.diamantino.voxelcraft.server.world.chunk;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.blocks.Blocks;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.server.world.ServerWorld;
import de.articdive.jnoise.pipeline.JNoise;

/**
 * Server-side chunk class.
 * <p>
 * See {@link Chunk} for the common functions between client and server.
 *
 * @author Diamantino
 */
public class ServerChunk extends Chunk {
    public boolean isGenerated;

    public ServerChunk(World world, ChunkPos chunkPos) {
        super(world, chunkPos);

        this.isGenerated = false;
    }

    public void generate(JNoise noise, ServerWorld world) {
        for (byte x = 0; x < Chunk.sizeX; x++) {
            for (byte z = 0; z < Chunk.sizeZ; z++) {
                BlockPos worldPos = world.chunkPosToWorldPos(new BlockPos(x, 0, z), this.chunkPos);
                byte y = (byte) Math.round(noise.evaluateNoise(worldPos.x(), worldPos.z()));

                // TODO: Diff blocks
                setBlockAt(Blocks.stone, new BlockPos(x, y, z));
            }
        }

        this.isGenerated = true;

        sendSyncPacket();
    }

    public void sendSyncPacket() {

    }
}
