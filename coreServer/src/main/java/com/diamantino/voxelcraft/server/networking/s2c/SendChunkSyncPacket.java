package com.diamantino.voxelcraft.server.networking.s2c;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.ISendPacket;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkLayer;
import com.diamantino.voxelcraft.common.world.chunk.IChunkLayer;
import com.diamantino.voxelcraft.common.world.chunk.SingleBlockChunkLayer;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Packet sent by the server to the client to synchronize a chunk.
 *
 * @author Diamantino
 */
public class SendChunkSyncPacket extends BasePacket implements ISendPacket {
    /**
     * Chunk to synchronize with the client.
     */
    private final Chunk chunk;

    /**
     * Name of the dimension.
     */
    private final String dimName;

    /**
     * Default constructor.
     * @param dimName Name of the dimension.
     * @param chunk Chunk to synchronize with the client.
     */
    public SendChunkSyncPacket(String dimName, Chunk chunk) {
        this.chunk = chunk;
        this.dimName = dimName;
    }

    /**
     * Writes the raw packet data to the data stream.
     *
     * @param buffer Data stream.
     */
    @Override
    public void writePacketData(SimpleBytesNcsPacket buffer) throws IOException {
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
