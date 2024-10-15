package com.diamantino.voxelcraft.client.entities;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.entities.Entity;
import com.diamantino.voxelcraft.common.world.World;

/**
 * Client-side entity class.
 *
 * @author Diamantino
 */
public abstract class ClientEntity extends Entity {
    protected ClientEntity(World world, BlockPos spawnLocation) {
        super(world, spawnLocation);
    }
}
