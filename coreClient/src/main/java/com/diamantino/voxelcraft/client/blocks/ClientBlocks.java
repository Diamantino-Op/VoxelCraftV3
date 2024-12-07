package com.diamantino.voxelcraft.client.blocks;

import com.diamantino.voxelcraft.client.rendering.RenderType;

import java.util.HashMap;
import java.util.Map;

public class ClientBlocks {
    /**
     *  Client blocks registry.
     *  Key: Block ID.
     *  Value: Block instance.
     */
    public static final Map<String, ClientBlock> clientBlocks = new HashMap<>();

    /**
     *  Register a client block.
     *
     *  @param name Block name.
     *  @param texture Block texture.
     *  @param renderType Block render type.
     */
    public void registerBlock(String name, IBlockTexture texture, RenderType renderType) {
        clientBlocks.put(name, new ClientBlock(name, texture, renderType));
    }

    /**
     *  Register all client blocks.
     */
    public void registerClientBlocks() {
        registerBlock("air", new SingleBlockTexture(0), RenderType.TRANSPARENT);
        registerBlock("stone", new SingleBlockTexture("stone"), RenderType.OPAQUE);
        registerBlock("glass", new SingleBlockTexture("glass"), RenderType.TRANSPARENT);
    }
}
