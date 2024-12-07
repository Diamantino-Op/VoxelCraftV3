package com.diamantino.voxelcraft.client.blocks;

import com.diamantino.voxelcraft.client.rendering.RenderType;

/**
 * Client side base block class.
 *
 * @author Diamantino
 */
public class ClientBlock {
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
    public ClientBlock(String name, IBlockTexture texture, RenderType renderType) {
        this.texture = texture;
        this.renderType = renderType;
    }
}
