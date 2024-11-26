package com.diamantino.voxelcraft.common.networking.packets.utils;

import com.diamantino.voxelcraft.common.networking.packets.data.Packets;

/**
 * Base class for all packets, providing the packet ID.
 *
 * @author Diamantino
 */
public abstract class BasePacket implements IPacket {
    private final int id;

    protected BasePacket() {
        this.id = Packets.packetIDs.get(getClass());
    }

    public int getId() {
        return id;
    }
}
