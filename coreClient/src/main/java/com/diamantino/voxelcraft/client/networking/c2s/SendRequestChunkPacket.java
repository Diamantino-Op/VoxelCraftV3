package com.diamantino.voxelcraft.client.networking.c2s;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.ISendPacket;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Packet sent by the client to request a chunk from the server.
 *
 * @author Diamantino
 */
public class SendRequestChunkPacket extends BasePacket implements ISendPacket {
    /**
     * The position of the chunk requested.
     */
    private final ChunkPos chunkPos;

    /**
     * The name of the dimension.
     */
    private final String dimName;

    /**
     * Default constructor.
     *
     * @param dimName Name of the dimension.
     * @param chunkPos Position of the chunk requested.
     */
    public SendRequestChunkPacket(String dimName, ChunkPos chunkPos) {
        this.chunkPos = chunkPos;
        this.dimName = dimName;
    }

    /**
     * Writes the raw packet data to the data stream.
     *
     * @param buffer Data stream.
     */
    @Override
    public void writePacketData(SimpleBytesNcsPacket buffer) throws IOException {
        buffer.encodeInt(chunkPos.x());
        buffer.encodeInt(chunkPos.y());
        buffer.encodeInt(chunkPos.z());
        buffer.encodeString(dimName);
    }
}
