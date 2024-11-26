package com.diamantino.voxelcraft.client.entities;

import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.World;

/**
 * Client-side player class.
 *
 * @author Diamantino
 */
public class ClientPlayerEntity extends ClientEntity {
    protected ClientPlayerEntity(World world, BlockPos spawnLocation) {
        super(world, spawnLocation);
    }
}
