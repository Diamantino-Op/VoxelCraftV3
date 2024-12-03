package com.diamantino.voxelcraft.common.networking.packets.s2c;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.properties.SyncedProperty;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * @author Diamantino
 */
public class SyncPropertyPacket extends BasePacket {
    public final SyncedProperty<?> property;

    public SyncPropertyPacket(SyncedProperty<?> property) {
        this.property = property;
    }

    @Override
    public void readPacketData(String senderName, SimpleBytesNcsPacket buffer) throws IOException {

    }

    @Override
    public void writePacketData(SimpleBytesNcsPacket buffer) throws IOException {

    }
}
