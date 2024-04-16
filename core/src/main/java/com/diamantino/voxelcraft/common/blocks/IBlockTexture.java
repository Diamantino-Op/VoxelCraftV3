package com.diamantino.voxelcraft.common.blocks;

/**
 * Client connection initializer class.
 *
 * @author Diamantino
 */
public interface IBlockTexture {
    /**
     *
     */
    int getFrontTexIndex();

    /**
     *
     */
    int getBackTexIndex();

    /**
     *
     */
    int getRightTexIndex();

    /**
     *
     */
    int getLeftTexIndex();

    /**
     *
     */
    int getTopTexIndex();

    /**
     *
     */
    int getBottomTexIndex();
}
