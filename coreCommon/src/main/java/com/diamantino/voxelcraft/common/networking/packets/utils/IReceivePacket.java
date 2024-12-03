package com.diamantino.voxelcraft.common.networking.packets.utils;

import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Interface for packets that are received.
 *
 * @author Diamantino
 */
public interface IReceivePacket {
    /**
     * Reads the raw packet data from the data stream.
     *
     * @param senderName Name of the sender.
     * @param buffer Data stream.
     */
    void readPacketData(String senderName, SimpleBytesNcsPacket buffer) throws IOException;
}
