package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.world.Dimension;

public class ServerDimension extends Dimension {
    /**
     * The constructor of the dimension.
     *
     * @param name  The name of the dimension.
     * @param world The world of the dimension.
     */
    public ServerDimension(String name, ServerWorld world) {
        super(name, world);
    }
}
