package com.diamantino.voxelcraft.client.networking.s2c;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.IReceivePacket;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Packet that receives a sync property from the server.
 *
 * @author Diamantino
 */
public class ReceiveSyncPropertyPacket extends BasePacket implements IReceivePacket {
    /**
     * Reads the raw packet data from the data stream.
     *
     * @param senderName Name of the sender.
     * @param buffer     Data stream.
     */
    @Override
    public void readPacketData(String senderName, SimpleBytesNcsPacket buffer) throws IOException {

    }
}
