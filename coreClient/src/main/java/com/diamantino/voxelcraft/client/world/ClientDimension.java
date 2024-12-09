package com.diamantino.voxelcraft.client.world;

import com.diamantino.voxelcraft.client.networking.ClientNetworkManager;
import com.diamantino.voxelcraft.client.networking.c2s.SendRequestChunkPacket;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.Dimension;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;

public class ClientDimension extends Dimension {
    /**
     * The constructor of the dimension.
     *
     * @param name  The name of the dimension.
     * @param world The world of the dimension.
     */
    public ClientDimension(String name, ClientWorld world) {
        super(name, world);
    }

    /**
     * The method to get the chunk for a position.
     * @param chunkPos The position of the chunk.
     * @return The chunk for the position.
     */
    @Override
    public Chunk getChunkForPos(ChunkPos chunkPos) {
        if (!chunkMap.containsKey(chunkPos)) {
            chunkMap.put(chunkPos, new ClientChunk(world, chunkPos));

            ClientNetworkManager.sendToServer(new SendRequestChunkPacket(name, chunkPos));
        }

        return chunkMap.get(chunkPos);
    }

    /**
     * Method called after setting a block.
     *
     * @param pos The position of the block.
     */
    @Override
    protected void setBlockPost(BlockPos pos) {
        ((ClientChunk) getChunkForBlockPos(pos)).regenerateMesh();
    }
}
