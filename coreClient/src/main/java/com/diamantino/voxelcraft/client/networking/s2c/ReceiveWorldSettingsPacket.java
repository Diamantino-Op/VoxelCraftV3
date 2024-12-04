package com.diamantino.voxelcraft.client.networking.s2c;

import com.diamantino.voxelcraft.client.VoxelCraftClient;
import com.diamantino.voxelcraft.client.world.ClientWorld;
import com.diamantino.voxelcraft.common.networking.packets.utils.BasePacket;
import com.diamantino.voxelcraft.common.networking.packets.utils.IReceivePacket;
import com.diamantino.voxelcraft.common.world.WorldSettings;
import com.github.terefang.ncs.common.packet.SimpleBytesNcsPacket;

import java.io.IOException;

public class ReceiveWorldSettingsPacket extends BasePacket implements IReceivePacket {
    /**
     * Reads the raw packet data from the data stream.
     *
     * @param senderName Name of the sender.
     * @param buffer     Data stream.
     */
    @Override
    public void readPacketData(String senderName, SimpleBytesNcsPacket buffer) throws IOException {
        String name = buffer.decodeString();
        long seed = buffer.decodeLong();

        VoxelCraftClient.getInstance().world = new ClientWorld(new WorldSettings(name, seed));
    }
}
