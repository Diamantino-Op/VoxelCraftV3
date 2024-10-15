package com.diamantino.voxelcraft.server.entities;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.entities.Entity;
import com.diamantino.voxelcraft.common.world.World;

/**
 * Base class for all server-side entities in the game.
 *
 * @author Diamantino
 */
public abstract class ServerEntity extends Entity {
    protected ServerEntity(World world, BlockPos spawnLocation) {
        super(world, spawnLocation);
    }
}
