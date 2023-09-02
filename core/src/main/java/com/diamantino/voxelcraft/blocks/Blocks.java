package com.diamantino.voxelcraft.blocks;

import com.diamantino.voxelcraft.client.rendering.RenderType;

import java.util.HashMap;

public class Blocks {
    public static HashMap<Short, Block> blocks = new HashMap<>(0);

    private static short id = 0;

    public static Block air = new Block(id++, "air", new SingleBlockTexture(0), RenderType.TRANSPARENT);
    public static Block stone = new Block(id++, "stone", new SingleBlockTexture("stone"), RenderType.OPAQUE);
}
