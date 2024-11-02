package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.WorldSettings;

/**
 * Server-side world class.
 * <p>
 * See {@link World} for the common functions between client and server.
 *
 * @author Diamantino
 */
public class ServerWorld extends World {
    /**
     * Constructor for the server-side world.
     *
     * @param settings The settings for the world.
     */
    public ServerWorld(WorldSettings settings) {
        super(settings);
    }
}
