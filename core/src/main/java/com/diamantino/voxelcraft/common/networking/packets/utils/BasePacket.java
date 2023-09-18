package com.diamantino.voxelcraft.common.networking.packets.utils;

import com.diamantino.voxelcraft.common.networking.packets.data.Packets;

public abstract class BasePacket implements IPacket {
    private final int id;

    public BasePacket() {
        this.id = Packets.packetIDs.get(getClass());
    }

    public int getId() {
        return id;
    }
}
