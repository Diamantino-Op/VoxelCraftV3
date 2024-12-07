package com.diamantino.voxelcraft.common.blocks;

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
     * Constructor of the block.
     *
     * @param name The registry name of the block.
     */
    public Block(String name) {
        this.name = name;
    }
}
