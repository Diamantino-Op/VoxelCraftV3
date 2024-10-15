package com.diamantino.voxelcraft.common.blocks;

import com.badlogic.gdx.math.Vector3;

/**
 * Block position class.
 *
 * @author Diamantino
 */
public record BlockPos(int x, int y, int z) {
    /**
     *  @return A new Vector3 instance with the same coordinates as this BlockPos.
     */
    public Vector3 toVec3() {
        return new Vector3(x, y, z);
    }
}
