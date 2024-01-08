package com.diamantino.voxelcraft.common.entities;

import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.World;

public abstract class Entity {
    /**
     * Represents the position of an entity in 3D space.
     */
    public Vector3 entityPos;
    public BlockPos blockPos;

    public Entity(World world, BlockPos spawnLocation) {
        this.blockPos = spawnLocation;
        this.entityPos = spawnLocation.toVec3();
    }

    public byte[] saveData() {
        return new byte[]{};
    }

    public void loadData(byte[] entityData) {

    }
}
