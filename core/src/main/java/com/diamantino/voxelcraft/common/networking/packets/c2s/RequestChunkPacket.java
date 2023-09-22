package com.diamantino.voxelcraft.common.networking.packets.c2s;

import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;
import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.networking.packets.s2c.ChunkSyncPacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.server.ServerInstance;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;

import java.io.IOException;

public class RequestChunkPacket extends BasePacket {
    private ChunkPos chunkPos;

    public RequestChunkPacket(ChunkPos chunkPos) {
        this.chunkPos = chunkPos;
    }

    @Override
    public void readPacketData(String senderName, PacketBuffer buffer) throws IOException {
        this.chunkPos = new ChunkPos(buffer.readInt(), buffer.readInt(), buffer.readInt());
        Packets.sendToPlayer(senderName, new ChunkSyncPacket(ServerInstance.instance.world.getChunkForPos(chunkPos)));
    }

    @Override
    public void writePacketData(PacketBuffer buffer) throws IOException {
        buffer.writeInt(chunkPos.x());
        buffer.writeInt(chunkPos.y());
        buffer.writeInt(chunkPos.z());
    }
}
