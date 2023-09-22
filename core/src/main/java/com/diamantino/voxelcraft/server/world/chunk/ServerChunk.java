package com.diamantino.voxelcraft.server.world.chunk;

import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;

public class ServerChunk extends Chunk {
    public ServerChunk(World world, ChunkPos chunkPos) {
        super(world, chunkPos);
    }

    public void sendSyncPacket() {

    }
}
