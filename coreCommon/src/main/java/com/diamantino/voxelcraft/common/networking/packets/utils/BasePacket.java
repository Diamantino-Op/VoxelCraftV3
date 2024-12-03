package com.diamantino.voxelcraft.common.networking.packets.utils;

import com.diamantino.voxelcraft.common.networking.packets.data.Packets;
import com.diamantino.voxelcraft.common.utils.ResourceLocation;
import lombok.Getter;

/**
 * Base class for all packets, providing the packet ID.
 *
 * @author Diamantino
 */
@Getter
public abstract class BasePacket {
    private final ResourceLocation id;

    protected BasePacket() {
        this.id = Packets.packetIDs.get(getClass());
    }
}
