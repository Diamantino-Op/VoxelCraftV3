package com.diamantino.voxelcraft.common.networking.packets.s2c;

import com.diamantino.voxelcraft.client.networking.ClientInstance;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.blocks.Blocks;
import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.world.chunk.*;

import java.io.IOException;

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
     * Default constructor.
     *
     * @param chunk Chunk to synchronize with the client.
     */
    public ChunkSyncPacket(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Reads the packet data from the packet buffer.
     *
     * @param senderName Name of the sender of the packet.
     * @param buffer Packet buffer containing the packet data.
     */
    @Override
    public void readPacketData(String senderName, PacketBuffer buffer) {
        // Read ChunkPos
        int chunkX = buffer.readInt();
        int chunkY = buffer.readInt();
        int chunkZ = buffer.readInt();

        // Get chunk
        ClientChunk clientChunk = (ClientChunk) ClientInstance.instance.world.getChunkForPos(new ChunkPos(chunkX, chunkY, chunkZ));

        // Read chunk data
        for (byte y = 0; y < Chunk.sizeY; y++) {
            switch (buffer.readByte()) {
                case 0 -> {
                    int nameLength = buffer.readInt();
                    clientChunk.setLayer(new SingleBlockChunkLayer(clientChunk, Blocks.blocks.get(buffer.readStringFromBuffer(nameLength)).getBlockInstance()), y);
                }

                case 1 -> {
                    ChunkLayer layer = new ChunkLayer(clientChunk);

                    for (int x = 0; x < Chunk.sizeX; x++) {
                        for (int z = 0; z < Chunk.sizeZ; z++) {
                            int nameLength = buffer.readInt();
                            layer.setBlock(Blocks.blocks.get(buffer.readStringFromBuffer(nameLength)).getBlockInstance(), x, z);
                        }
                    }

                    clientChunk.setLayer(layer, y);
                }

                default -> clientChunk.setLayer(null, y);
            }
        }

        clientChunk.regenerateMesh();
    }

    /**
     * Writes the packet data to the packet buffer.
     *
     * @param buffer Packet buffer to write the packet data to.
     */
    @Override
    public void writePacketData(PacketBuffer buffer) {
        // Write ChunkPos
        buffer.writeInt(chunk.chunkPos.x());
        buffer.writeInt(chunk.chunkPos.y());
        buffer.writeInt(chunk.chunkPos.z());

        // Write Layers
        for (byte y = 0; y < Chunk.sizeY; y++) {
            IChunkLayer layer = chunk.getLayer(y);

            switch (layer) {
                case SingleBlockChunkLayer sbcl -> {
                    buffer.writeByte(0);
                    buffer.writeInt(sbcl.getBlock().name.length());
                    buffer.writeString(sbcl.getBlock().name);
                }

                case ChunkLayer chunkLayer -> {
                    buffer.writeByte(1);

                    for (int x = 0; x < Chunk.sizeX; x++) {
                        for (int z = 0; z < Chunk.sizeZ; z++) {
                            String blockName = chunkLayer.getBlockName(x, z);
                            buffer.writeInt(blockName.length());
                            buffer.writeString(blockName);
                        }
                    }
                }

                default -> buffer.writeByte(2);
            }
        }
    }
}
