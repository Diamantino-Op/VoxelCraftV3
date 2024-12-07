package com.diamantino.voxelcraft.client.blocks;

/**
 * Block texture interface.
 *
 * @author Diamantino
 */
public interface IBlockTexture {
    /**
     * Get the texture index for the front face of the block.
     */
    int getFrontTexIndex();

    /**
     * Get the texture index for the back face of the block.
     */
    int getBackTexIndex();

    /**
     * Get the texture index for the right face of the block.
     */
    int getRightTexIndex();

    /**
     * Get the texture index for the left face of the block.
     */
    int getLeftTexIndex();

    /**
     * Get the texture index for the top face of the block.
     */
    int getTopTexIndex();

    /**
     * Get the texture index for the bottom face of the block.
     */
    int getBottomTexIndex();
}
