package com.diamantino.voxelcraft.blocks;

public interface IBlockTexture {
    int getFrontTexIndex();
    int getBackTexIndex();
    int getRightTexIndex();
    int getLeftTexIndex();
    int getTopTexIndex();
    int getBottomTexIndex();
}
