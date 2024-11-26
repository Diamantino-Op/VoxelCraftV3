package com.diamantino.voxelcraft.common.entities;

import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.common.blocks.BlockPos;
import com.diamantino.voxelcraft.common.vdo.ArrayVDO;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;
import com.diamantino.voxelcraft.common.world.World;
import dev.ultreon.ubo.types.FloatArrayType;
import dev.ultreon.ubo.types.MapType;

import java.util.UUID;

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
    public UUID entityId;

    protected Entity(World world, BlockPos spawnLocation) {
        this.world = world;
        this.blockPos = spawnLocation;
        this.entityPos = spawnLocation.toVec3();
    }

    /**
     * Loads the entity data.
     *
     * @param entityData The data loaded from the chunk file.
     */
    public void loadData(MapType entityData) {
        float[] entityPosArray = entityData.getFloatArray("entityPos");
        this.entityPos = new Vector3(entityPosArray[0], entityPosArray[1], entityPosArray[2]);

        int[] blockPosArray = entityData.getIntArray("blockPos");
        this.blockPos = new BlockPos(blockPosArray[0], blockPosArray[1], blockPosArray[2]);

        this.entityId = entityData.getUUID("entityId");
    }

    /**
     * Saves the entity data.
     *
     * @return The byte data that needs to be saved in the chunk.
     */
    public MapType saveData() {
        MapType entityData = new MapType();

        float[] entityPosArray = new float[3];
        entityPosArray[0] = this.entityPos.x;
        entityPosArray[1] = this.entityPos.y;
        entityPosArray[2] = this.entityPos.z;

        entityData.putFloatArray("entityPos", entityPosArray);

        int[] blockPosArray = new int[3];
        blockPosArray[0] = this.blockPos.x();
        blockPosArray[1] = this.blockPos.y();
        blockPosArray[2] = this.blockPos.z();

        entityData.putIntArray("blockPos", blockPosArray);

        entityData.putUUID("entityId", this.entityId);

        return entityData;
    }
}
