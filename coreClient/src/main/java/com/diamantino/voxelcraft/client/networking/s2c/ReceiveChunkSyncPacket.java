package com.diamantino.voxelcraft.client.networking.s2c;

import com.diamantino.voxelcraft.client.networking.ClientInstance;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.IReceivePacket;
import com.diamantino.voxelcraft.common.registration.Blocks;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkLayer;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.common.world.chunk.SingleBlockChunkLayer;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Packet that receives a chunk sync from the server.
 *
 * @author Diamantino
 */
public class ReceiveChunkSyncPacket extends BasePacket implements IReceivePacket {
    /**
     * Reads the raw packet data from the data stream.
     *
     * @param senderName Name of the sender.
     * @param buffer     Data stream.
     */
    @Override
    public void readPacketData(String senderName, SimpleBytesNcsPacket buffer) throws IOException {
        // Read ChunkPos
        int chunkX = buffer.decodeInt();
        int chunkY = buffer.decodeInt();
        int chunkZ = buffer.decodeInt();

        String dimName = buffer.decodeString();

        // Get chunk
        ClientChunk clientChunk = (ClientChunk) ClientInstance.instance.world.getChunkForPos(dimName, new ChunkPos(chunkX, chunkY, chunkZ));

        // Read chunk data
        for (byte y = 0; y < Chunk.sizeY; y++) {
            switch (buffer.decodeByte()) {
                case 0 -> {
                    clientChunk.setLayer(new SingleBlockChunkLayer(clientChunk, Blocks.blocks.get(buffer.decodeString()).getBlockInstance()), y);
                }

                case 1 -> {
                    ChunkLayer layer = new ChunkLayer(clientChunk);

                    for (int x = 0; x < Chunk.sizeX; x++) {
                        for (int z = 0; z < Chunk.sizeZ; z++) {
                            layer.setBlock(Blocks.blocks.get(buffer.decodeString()).getBlockInstance(), x, z);
                        }
                    }

                    clientChunk.setLayer(layer, y);
                }

                default -> clientChunk.setLayer(null, y);
            }
        }

        clientChunk.regenerateMesh();
    }
}
