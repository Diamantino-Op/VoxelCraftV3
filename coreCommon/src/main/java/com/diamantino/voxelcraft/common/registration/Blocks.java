package com.diamantino.voxelcraft.common.registration;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.blocks.Block;

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

    public static final RegisteredBlock<Block> air = registerBlock("air");
    public static final RegisteredBlock<Block> stone = registerBlock("stone");
    public static final RegisteredBlock<Block> glass = registerBlock("glass");

    /**
     *  Register a block.
     *
     *  @param name Block name.
     */
    private static RegisteredBlock<Block> registerBlock(String name) {
        RegisteredBlock<Block> tmpBlock = new RegisteredBlock<>(name, new Block(name));
        blocks.put(name, tmpBlock);
        return tmpBlock;
    }

    /**
     *  Register all blocks.
     */
    public static void registerBlocks() {
        Gdx.app.log(Constants.infoLogTag, "Registering blocks...");
    }
}
