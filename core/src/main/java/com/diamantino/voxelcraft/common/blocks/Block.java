package com.diamantino.voxelcraft.common.blocks;

import com.diamantino.voxelcraft.client.rendering.RenderType;

/**
 * Base block class.
 *
 * @author Diamantino
 */
public class Block {
    public short id;
    public String name;
    public IBlockTexture texture;
    public RenderType renderType;

    public Block(short id, String name, IBlockTexture texture, RenderType renderType) {
        this.id = id;
        this.name = name;
        this.texture = texture;
        this.renderType = renderType;
    }
}
