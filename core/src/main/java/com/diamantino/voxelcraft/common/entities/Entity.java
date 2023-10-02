package com.diamantino.voxelcraft.common.entities;

import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.World;

public class Entity {
    public Vector3 entityPos;
    public BlockPos blockPos;

    public Entity(World world, BlockPos spawnLocation) {
        this.blockPos = spawnLocation;
        this.entityPos = spawnLocation.toVec3();
    }
}
