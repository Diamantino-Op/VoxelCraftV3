package com.diamantino.voxelcraft.common.blocks;

import com.diamantino.voxelcraft.client.utils.AtlasManager;

public class SingleBlockTexture implements IBlockTexture {
    private final int blockTexIndex;

    // TODO: Get instance from VoxelCraftClient
    public SingleBlockTexture(String blockTexName) {
        this.blockTexIndex = AtlasManager.getBlockTextureIndex(blockTexName);
    }

    public SingleBlockTexture(int blockTexIndex) {
        this.blockTexIndex = blockTexIndex;
    }

    @Override
    public int getFrontTexIndex() {
        return blockTexIndex;
    }

    @Override
    public int getBackTexIndex() {
        return blockTexIndex;
    }

    @Override
    public int getRightTexIndex() {
        return blockTexIndex;
    }

    @Override
    public int getLeftTexIndex() {
        return blockTexIndex;
    }

    @Override
    public int getTopTexIndex() {
        return blockTexIndex;
    }

    @Override
    public int getBottomTexIndex() {
        return blockTexIndex;
    }
}
