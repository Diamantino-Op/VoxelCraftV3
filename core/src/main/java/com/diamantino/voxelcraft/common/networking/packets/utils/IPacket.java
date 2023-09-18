package com.diamantino.voxelcraft.common.networking.packets.utils;

import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;

import java.io.IOException;

public interface IPacket {
    /**
     * Reads the raw packet data from the data stream.
     */
    void readPacketData(String senderName, PacketBuffer buffer) throws IOException;

    /**
     * Writes the raw packet data to the data stream.
     */
    void writePacketData(PacketBuffer buffer) throws IOException;
}
