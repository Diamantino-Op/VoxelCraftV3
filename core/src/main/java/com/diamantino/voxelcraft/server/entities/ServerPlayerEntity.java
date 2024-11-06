package com.diamantino.voxelcraft.server.entities;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.entities.PlayerEntity;
import com.diamantino.voxelcraft.common.world.World;

/**
 * Server-side player class.
 *
 * @author Diamantino
 */
public class ServerPlayerEntity extends PlayerEntity {
    /**
     * Constructor of the entity class.
     *
     * @param world The world instance.
     * @param spawnLocation The position where to spawn the entity.
     */
    protected ServerPlayerEntity(World world, BlockPos spawnLocation) {
        super(world, spawnLocation);
    }
}
