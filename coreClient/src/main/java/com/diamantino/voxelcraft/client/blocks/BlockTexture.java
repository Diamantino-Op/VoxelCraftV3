package com.diamantino.voxelcraft.client.blocks;

import com.diamantino.voxelcraft.client.VoxelCraftClient;
import com.diamantino.voxelcraft.client.utils.ClientLoadingUtils;

/**
 * Block texture class with different texture for all sides.
 *
 * @author Diamantino
 */
public class BlockTexture implements IBlockTexture {
    /**
     * Index of the front texture in the texture atlas.
     */
    private final int frontTexIndex;

    /**
     * Index of the back texture in the texture atlas.
     */
    private final int backTexIndex;

    /**
     * Index of the right texture in the texture atlas.
     */
    private final int rightTexIndex;

    /**
     * Index of the left texture in the texture atlas.
     */
    private final int leftTexIndex;

    /**
     * Index of the top texture in the texture atlas.
     */
    private final int topTexIndex;

    /**
     * Index of the bottom texture in the texture atlas.
     */
    private final int bottomTexIndex;

    /**
     * Constructor for a block texture.
     *
     * @param frontTexName Name of the front texture.
     * @param backTexName Name of the back texture.
     * @param rightTexName Name of the right texture.
     * @param leftTexName Name of the left texture.
     * @param topTexName Name of the top texture.
     * @param bottomTexName Name of the bottom texture.
     */
    public BlockTexture(String frontTexName, String backTexName, String rightTexName, String leftTexName, String topTexName, String bottomTexName) {
        this.frontTexIndex = ClientLoadingUtils.getBlockTextureIndex("blocks", frontTexName);
        this.backTexIndex = ClientLoadingUtils.getBlockTextureIndex("blocks", backTexName);
        this.rightTexIndex = ClientLoadingUtils.getBlockTextureIndex("blocks", rightTexName);
        this.leftTexIndex = ClientLoadingUtils.getBlockTextureIndex("blocks", leftTexName);
        this.topTexIndex = ClientLoadingUtils.getBlockTextureIndex("blocks", topTexName);
        this.bottomTexIndex = ClientLoadingUtils.getBlockTextureIndex("blocks", bottomTexName);
    }

    /**
     * Get the index of the front texture in the texture atlas.
     *
     * @return Index of the front texture.
     */
    @Override
    public int getFrontTexIndex() {
        return frontTexIndex;
    }

    /**
     * Get the index of the back texture in the texture atlas.
     *
     * @return Index of the back texture.
     */
    @Override
    public int getBackTexIndex() {
        return backTexIndex;
    }

    /**
     * Get the index of the right texture in the texture atlas.
     *
     * @return Index of the right texture.
     */
    @Override
    public int getRightTexIndex() {
        return rightTexIndex;
    }

    /**
     * Get the index of the left texture in the texture atlas.
     *
     * @return Index of the left texture.
     */
    @Override
    public int getLeftTexIndex() {
        return leftTexIndex;
    }

    /**
     * Get the index of the top texture in the texture atlas.
     *
     * @return Index of the top texture.
     */
    @Override
    public int getTopTexIndex() {
        return topTexIndex;
    }

    /**
     * Get the index of the bottom texture in the texture atlas.
     *
     * @return Index of the bottom texture.
     */
    @Override
    public int getBottomTexIndex() {
        return bottomTexIndex;
    }
}
