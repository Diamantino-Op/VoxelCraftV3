package com.diamantino.voxelcraft.common.entities;

import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.world.World;

public abstract class Entity {
    /**
     * Current entity position.
     */
    public Vector3 entityPos;
    /**
     * Current entity block position.
     */
    public BlockPos blockPos;

    public Entity(World world, BlockPos spawnLocation) {
        this.blockPos = spawnLocation;
        this.entityPos = spawnLocation.toVec3();
    }

    /**
     * Saves the entity data.
     * @return The byte data that needs to be saved in the chunk.
     */
    public byte[] saveData() {
        return new byte[]{};
    }

    /**
     * Loads the entity data.
     * @param entityData The data loaded from the chunk file.
     */
    public void loadData(byte[] entityData) {

    }
}
