package com.diamantino.voxelcraft.common.networking.packets.s2c;

import com.diamantino.voxelcraft.client.ClientInstance;
import com.diamantino.voxelcraft.client.world.chunk.ClientChunk;
import com.diamantino.voxelcraft.common.blocks.Blocks;
import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.world.chunk.*;

import java.io.IOException;

public class ChunkSyncPacket extends BasePacket {
    private final Chunk chunk;

    public ChunkSyncPacket(Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void readPacketData(String senderName, PacketBuffer buffer) throws IOException {
        // Read ChunkPos
        int chunkX = buffer.readInt();
        int chunkY = buffer.readInt();
        int chunkZ = buffer.readInt();

        // Get chunk
        ClientChunk chunk = (ClientChunk) ClientInstance.instance.world.getChunkForPos(new ChunkPos(chunkX, chunkY, chunkZ));

        // Read chunk data
        for (byte y = 0; y < Chunk.sizeY; y++) {
            switch (buffer.readByte()) {
                case 0 -> chunk.setLayer(new SingleBlockChunkLayer(chunk, Blocks.blocks.get(buffer.readShort()), y), y);
                case 1 -> {
                    ChunkLayer layer = new ChunkLayer(chunk, y);

                    for (int x = 0; x < Chunk.sizeX; x++) {
                        for (int z = 0; z < Chunk.sizeZ; z++) {
                            layer.setBlock(Blocks.blocks.get(buffer.readShort()), x, z);
                        }
                    }

                    chunk.setLayer(layer, y);
                }
            }
        }

        chunk.regenerateMesh();
    }

    @Override
    public void writePacketData(PacketBuffer buffer) throws IOException {
        // Write ChunkPos
        buffer.writeInt(chunk.chunkPos.x());
        buffer.writeInt(chunk.chunkPos.y());
        buffer.writeInt(chunk.chunkPos.z());

        // Write Layers
        for (byte y = 0; y < Chunk.sizeY; y++) {
            IChunkLayer layer = chunk.getLayer(y);

            if (layer instanceof SingleBlockChunkLayer sbcl) {
                buffer.writeByte(0);

                buffer.writeShort(sbcl.getBlock().id);
            } else if (layer instanceof ChunkLayer chunkLayer) {
                buffer.writeByte(1);

                for (int x = 0; x < Chunk.sizeX; x++) {
                    for (int z = 0; z < Chunk.sizeZ; z++) {
                        buffer.writeShort(chunkLayer.getBlockId(x, z));
                    }
                }
            } else {
                buffer.writeByte(2);
            }
        }
    }
}
