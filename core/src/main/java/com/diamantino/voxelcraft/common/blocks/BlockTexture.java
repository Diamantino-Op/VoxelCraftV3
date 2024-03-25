package com.diamantino.voxelcraft.common.blocks;

import com.diamantino.voxelcraft.client.utils.AtlasManager;

/**
 * Client connection initializer class.
 *
 * @author Diamantino
 */
public class BlockTexture implements IBlockTexture {
    private final int frontTexIndex;
    private final int backTexIndex;
    private final int rightTexIndex;
    private final int leftTexIndex;
    private final int topTexIndex;
    private final int bottomTexIndex;

    public BlockTexture(String frontTexName, String backTexName, String rightTexName, String leftTexName, String topTexName, String bottomTexName) {
        this.frontTexIndex = AtlasManager.getBlockTextureIndex(frontTexName);
        this.backTexIndex = AtlasManager.getBlockTextureIndex(backTexName);
        this.rightTexIndex = AtlasManager.getBlockTextureIndex(rightTexName);
        this.leftTexIndex = AtlasManager.getBlockTextureIndex(leftTexName);
        this.topTexIndex = AtlasManager.getBlockTextureIndex(topTexName);
        this.bottomTexIndex = AtlasManager.getBlockTextureIndex(bottomTexName);
    }

    @Override
    public int getFrontTexIndex() {
        return frontTexIndex;
    }

    @Override
    public int getBackTexIndex() {
        return backTexIndex;
    }

    @Override
    public int getRightTexIndex() {
        return rightTexIndex;
    }

    @Override
    public int getLeftTexIndex() {
        return leftTexIndex;
    }

    @Override
    public int getTopTexIndex() {
        return topTexIndex;
    }

    @Override
    public int getBottomTexIndex() {
        return bottomTexIndex;
    }
}
