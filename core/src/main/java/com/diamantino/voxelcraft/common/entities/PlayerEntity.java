package com.diamantino.voxelcraft.common.entities;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.World;

/**
 * Common player class.
 *
 * @author Diamantino
 */
public class PlayerEntity extends Entity {
    /**
     * Constructor of the entity class.
     *
     * @param world The world instance.
     * @param spawnLocation The position where to spawn the entity.
     */
    protected PlayerEntity(World world, BlockPos spawnLocation) {
        super(world, spawnLocation);
    }
}
