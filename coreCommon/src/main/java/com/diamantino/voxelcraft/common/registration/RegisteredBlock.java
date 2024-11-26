package com.diamantino.voxelcraft.common.registration;

import com.diamantino.voxelcraft.common.blocks.Block;

/**
 * Represents a registered block.
 *
 * @author Diamantino
 */
public class RegisteredBlock<B extends Block> {
    /**
     * The registry name of the block.
     */
    private final String registryName;

    /**
     * The block instance.
     */
    private final B blockInstance;

    /**
     * Creates a new registered block.
     *
     * @param registryName The registry name of the block.
     * @param blockInstance The block instance.
     */
    public RegisteredBlock(String registryName, B blockInstance) {
        this.registryName = registryName;
        this.blockInstance = blockInstance;
    }

    /**
     * Gets the registry name of the block.
     *
     * @return The registry name of the block.
     */
    public String getRegistryName() {
        return registryName;
    }

    /**
     * Gets the block instance.
     *
     * @return The block instance.
     */
    public B getBlockInstance() {
        return blockInstance;
    }
}
