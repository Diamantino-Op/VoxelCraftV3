package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.server.world.chunk.Chunk;
import com.diamantino.voxelcraft.server.world.chunk.ChunkPos;

public class ServerWorld extends World {
    public ServerWorld(String name, WorldSettings settings) {
        super(name, settings);
    }

    @Override
    public Chunk getChunkForPos(ChunkPos chunkPos) {
        return super.getChunkForPos(chunkPos);
    }
}
