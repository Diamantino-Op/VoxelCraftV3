package com.diamantino.voxelcraft.server.networking.c2s;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.IReceivePacket;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;
import com.diamantino.voxelcraft.server.VoxelCraftServer;
import com.diamantino.voxelcraft.server.networking.ServerNetworkManager;
import com.diamantino.voxelcraft.server.networking.s2c.SendChunkSyncPacket;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Packet sent by the client to request a chunk from the server.
 *
 * @author Diamantino
 */
public class ReceiveRequestChunkPacket extends BasePacket implements IReceivePacket {
    /**
     * Reads the raw packet data from the data stream.
     *
     * @param senderName Name of the sender.
     * @param buffer Data stream.
     */
    @Override
    public void readPacketData(String senderName, SimpleBytesNcsPacket buffer) throws IOException {
        ChunkPos chunkPos = new ChunkPos(buffer.decodeInt(), buffer.decodeInt(), buffer.decodeInt());
        String dimName = buffer.decodeString();

        ServerNetworkManager.sendToPlayer(senderName, new SendChunkSyncPacket(dimName, VoxelCraftServer.getInstance().world.getChunkForPos(dimName, chunkPos)));
    }
}
