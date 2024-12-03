package com.diamantino.voxelcraft.common.networking.packets.s2c;

import com.diamantino.voxelcraft.client.networking.ClientInstance;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.registration.Blocks;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.world.chunk.*;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

/**
 * Packet sent by the server to synchronize a chunk with the client.
 *
 * @author Diamantino
 */
public class ChunkSyncPacket extends BasePacket {
    /**
     * Chunk to synchronize with the client.
     */
    private final Chunk chunk;

    /**
     * Name of the dimension.
     */
    private String dimName;

    /**
     * Default constructor.
     * @param dimName Name of the dimension.
     * @param chunk Chunk to synchronize with the client.
     */
    public ChunkSyncPacket(String dimName, Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Reads the packet data from the packet buffer.
     *
     * @param senderName Name of the sender of the packet.
     * @param buffer Packet buffer containing the packet data.
     */
    @Override
    public void readPacketData(String senderName, SimpleBytesNcsPacket buffer) {
        // Read ChunkPos
        int chunkX = buffer.decodeInt();
        int chunkY = buffer.decodeInt();
        int chunkZ = buffer.decodeInt();

        this.dimName = buffer.decodeString();

        // Get chunk
        ClientChunk clientChunk = (ClientChunk) ClientInstance.instance.world.getChunkForPos(this.dimName, new ChunkPos(chunkX, chunkY, chunkZ));

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

        buffer.finishDecoding();
    }

    /**
     * Writes the packet data to the packet buffer.
     *
     * @param buffer Packet buffer to write the packet data to.
     */
    @Override
    public void writePacketData(SimpleBytesNcsPacket buffer) {
        // Write ChunkPos
        buffer.encodeInt(chunk.chunkPos.x());
        buffer.encodeInt(chunk.chunkPos.y());
        buffer.encodeInt(chunk.chunkPos.z());

        buffer.encodeString(dimName);

        // Write Layers
        for (byte y = 0; y < Chunk.sizeY; y++) {
            IChunkLayer layer = chunk.getLayer(y);

            switch (layer) {
                case SingleBlockChunkLayer sbcl -> {
                    buffer.encodeByte((byte) 0);
                    buffer.encodeString(sbcl.getBlock().name);
                }

                case ChunkLayer chunkLayer -> {
                    buffer.encodeByte((byte) 1);

                    for (int x = 0; x < Chunk.sizeX; x++) {
                        for (int z = 0; z < Chunk.sizeZ; z++) {
                            String blockName = chunkLayer.getBlockName(x, z);
                            buffer.encodeString(blockName);
                        }
                    }
                }

                default -> buffer.encodeByte((byte) 2);
            }
        }
    }
}
