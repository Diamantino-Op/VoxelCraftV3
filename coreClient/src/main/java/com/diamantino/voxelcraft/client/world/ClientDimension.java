package com.diamantino.voxelcraft.client.world;

import com.diamantino.voxelcraft.common.world.Dimension;

public class ClientDimension extends Dimension {
    /**
     * The constructor of the dimension.
     *
     * @param name  The name of the dimension.
     * @param world The world of the dimension.
     */
    public ClientDimension(String name, ClientWorld world) {
        super(name, world);
    }
}
