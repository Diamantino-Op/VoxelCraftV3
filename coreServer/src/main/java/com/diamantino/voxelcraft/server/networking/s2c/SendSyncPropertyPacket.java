package com.diamantino.voxelcraft.server.networking.s2c;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.ISendPacket;
import com.diamantino.voxelcraft.common.properties.SyncedProperty;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Packet sent by the server to synchronize a property with the client.
 *
 * @author Diamantino
 */
public class SendSyncPropertyPacket extends BasePacket implements ISendPacket {
    /**
     * Property to synchronize with the client.
     */
    public final SyncedProperty<?> property;

    /**
     * Default constructor.
     *
     * @param property Property to synchronize with the client.
     */
    public SendSyncPropertyPacket(SyncedProperty<?> property) {
        this.property = property;
    }

    /**
     * Writes the raw packet data to the data stream.
     *
     * @param buffer Data stream.
     */
    @Override
    public void writePacketData(SimpleBytesNcsPacket buffer) throws IOException {

    }
}
