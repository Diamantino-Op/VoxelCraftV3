package com.diamantino.voxelcraft.common.blocks;

import com.diamantino.voxelcraft.client.utils.TextureManager;

/**
 * Block texture that uses the same texture for all faces.
 *
 * @author Diamantino
 */
public class SingleBlockTexture implements IBlockTexture {
    /**
     * Index of the texture in the texture atlas.
     */
    private final int blockTexIndex;

    /**
     * Constructor that receives the name of the texture.
     *
     * @param blockTexName Name of the texture.
     */
    // TODO: Get instance from VoxelCraftClient
    public SingleBlockTexture(String blockTexName) {
        this.blockTexIndex = TextureManager.getBlockTextureIndex(blockTexName);
    }

    /**
     * Constructor that receives the index of the texture in the texture atlas.
     */
    public SingleBlockTexture(int blockTexIndex) {
        this.blockTexIndex = blockTexIndex;
    }

    /**
     * Returns the index of the front texture in the texture atlas.
     *
     * @return Index of the front texture in the texture atlas.
     */
    @Override
    public int getFrontTexIndex() {
        return blockTexIndex;
    }

    /**
     * Returns the index of the back texture in the texture atlas.
     *
     * @return Index of the back texture in the texture atlas.
     */
    @Override
    public int getBackTexIndex() {
        return blockTexIndex;
    }

    /**
     * Returns the index of the right texture in the texture atlas.
     *
     * @return Index of the right texture in the texture atlas.
     */
    @Override
    public int getRightTexIndex() {
        return blockTexIndex;
    }

    /**
     * Returns the index of the left texture in the texture atlas.
     *
     * @return Index of the left texture in the texture atlas.
     */
    @Override
    public int getLeftTexIndex() {
        return blockTexIndex;
    }

    /**
     * Returns the index of the top texture in the texture atlas.
     *
     * @return Index of the top texture in the texture atlas.
     */
    @Override
    public int getTopTexIndex() {
        return blockTexIndex;
    }

    /**
     * Returns the index of the bottom texture in the texture atlas.
     *
     * @return Index of the bottom texture in the texture atlas.
     */
    @Override
    public int getBottomTexIndex() {
        return blockTexIndex;
    }
}
