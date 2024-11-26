package com.diamantino.voxelcraft.common.blocks;

import com.diamantino.voxelcraft.client.rendering.RenderType;

/**
 * Base block class.
 *
 * @author Diamantino
 */
public class Block {
    /**
     * The registry name of the block.
     */
    public final String name;

    /**
     * The texture of the block.
     */
    public final IBlockTexture texture;

    /**
     * The render type of the block.
     */
    public final RenderType renderType;

    /**
     * Constructor of the block.
     *
     * @param name The registry name of the block.
     * @param texture The texture of the block.
     * @param renderType The render type of the block.
     */
    public Block(String name, IBlockTexture texture, RenderType renderType) {
        this.name = name;
        this.texture = texture;
        this.renderType = renderType;
    }
}
