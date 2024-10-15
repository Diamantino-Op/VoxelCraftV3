package com.diamantino.voxelcraft.common.blocks;

import com.diamantino.voxelcraft.client.rendering.RenderType;
import com.diamantino.voxelcraft.common.registration.RegisteredBlock;

import java.util.HashMap;
import java.util.Map;

/**
 * Blocks registration class.
 *
 * @author Diamantino
 */
public class Blocks {
    /**
     *  Blocks registry.
     *  Key: Block ID.
     *  Value: Block instance.
     */
    public static final Map<String, RegisteredBlock<?>> blocks = new HashMap<>();

    public static final RegisteredBlock<Block> air = registerBlock("air", new SingleBlockTexture(0), RenderType.TRANSPARENT);
    public static final RegisteredBlock<Block> stone = registerBlock("stone", new SingleBlockTexture("stone"), RenderType.OPAQUE);
    public static final RegisteredBlock<Block> glass = registerBlock("glass", new SingleBlockTexture("glass"), RenderType.TRANSPARENT);

    /**
     *  Register a block.
     *
     *  @param name Block name.
     *  @param texture Block texture.
     *  @param renderType Block render type.
     */
    private static RegisteredBlock<Block> registerBlock(String name, IBlockTexture texture, RenderType renderType) {
        RegisteredBlock<Block> tmpBlock = new RegisteredBlock<>(name, new Block(name, texture, renderType));
        blocks.put(name, tmpBlock);
        return tmpBlock;
    }
}
