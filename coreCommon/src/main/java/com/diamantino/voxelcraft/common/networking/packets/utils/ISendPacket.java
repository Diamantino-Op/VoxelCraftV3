package com.diamantino.voxelcraft.common.networking.packets.utils;

import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Interface for packets that are sent.
 *
 * @author Diamantino
 */
public interface ISendPacket {
    /**
     * Writes the raw packet data to the data stream.
     *
     * @param buffer Data stream.
     */
    void writePacketData(SimpleBytesNcsPacket buffer) throws IOException;
}
