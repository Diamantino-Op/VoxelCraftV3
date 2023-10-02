package com.diamantino.voxelcraft.common.blocks;

import com.badlogic.gdx.math.Vector3;

public record BlockPos(int x, int y, int z) {
    public Vector3 toVec3() {
        return new Vector3(x, y, z);
    }
}
