package com.diamantino.voxelcraft.common.networking.packets.s2c;

import com.diamantino.voxelcraft.common.networking.packets.data.PacketBuffer;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.properties.SyncedProperty;

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
    public void readPacketData(String senderName, PacketBuffer buffer) throws IOException {

    }

    @Override
    public void writePacketData(PacketBuffer buffer) throws IOException {

    }
}
