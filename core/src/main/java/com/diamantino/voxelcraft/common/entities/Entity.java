package com.diamantino.voxelcraft.common.entities;

import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.vdo.ArrayVDO;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;
import com.diamantino.voxelcraft.common.world.World;

/**
 * Base class for all entities in the game.
 *
 * @author Diamantino
 */
public abstract class Entity {
    /**
     * Current entity position.
     */
    public Vector3 entityPos;

    /**
     * Current entity block position.
     */
    public BlockPos blockPos;

    /**
     * The world the entity is in.
     */
    public World world;

    /**
     * The entity ID.
     */
    public int entityId;

    protected Entity(World world, BlockPos spawnLocation) {
        this.world = world;
        this.blockPos = spawnLocation;
        this.entityPos = spawnLocation.toVec3();
    }

    /**
     * Loads the entity data.
     *
     * @param compoundVDO The data loaded from the chunk file.
     */
    public void loadData(CompoundVDO compoundVDO) {
        ArrayVDO entityPosVDO = compoundVDO.getArrayVDO("entityPos");
        this.entityPos = new Vector3(entityPosVDO.getFloatVDO(0), entityPosVDO.getFloatVDO(1), entityPosVDO.getFloatVDO(2));

        ArrayVDO blockPosVDO = compoundVDO.getArrayVDO("blockPos");
        this.blockPos = new BlockPos(blockPosVDO.getIntVDO(0), blockPosVDO.getIntVDO(1), blockPosVDO.getIntVDO(2));

        this.entityId = compoundVDO.getIntVDO("entityId");
    }

    /**
     * Saves the entity data.
     *
     * @return The byte data that needs to be saved in the chunk.
     */
    public CompoundVDO saveData() {
        CompoundVDO compoundVDO = new CompoundVDO();

        ArrayVDO entityPosVDO = new ArrayVDO();
        entityPosVDO.setFloatVDO(0, this.entityPos.x);
        entityPosVDO.setFloatVDO(1, this.entityPos.y);
        entityPosVDO.setFloatVDO(2, this.entityPos.z);

        compoundVDO.setArrayVDO("entityPos", entityPosVDO);

        ArrayVDO blockPosVDO = new ArrayVDO();
        blockPosVDO.setIntVDO(0, this.blockPos.x());
        blockPosVDO.setIntVDO(1, this.blockPos.y());
        blockPosVDO.setIntVDO(2, this.blockPos.z());

        compoundVDO.setArrayVDO("blockPos", blockPosVDO);

        compoundVDO.setIntVDO("entityId", this.entityId);

        return compoundVDO;
    }
}
