package com.diamantino.voxelcraft.blocks;

import com.diamantino.voxelcraft.client.rendering.RenderType;

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
