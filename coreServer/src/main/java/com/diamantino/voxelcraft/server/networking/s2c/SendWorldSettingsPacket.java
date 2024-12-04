package com.diamantino.voxelcraft.server.networking.s2c;

import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.ISendPacket;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.diamantino.voxelcraft.server.VoxelCraftServer;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

/**
 * Packet sent to the client to inform it of the world settings.
 *
 * @author Diamantino
 */
public class SendWorldSettingsPacket extends BasePacket implements ISendPacket {
    /**
     * Stores the world settings.
     */
    private final WorldSettings settings;

    public SendWorldSettingsPacket(WorldSettings settings) {
        this.settings = settings;
    }

    /**
     * Writes the raw packet data to the data stream.
     *
     * @param buffer Data stream.
     */
    @Override
    public void writePacketData(SimpleBytesNcsPacket buffer) throws IOException {
        buffer.encodeString(settings.name());
        buffer.encodeLong(settings.seed());
    }
}
