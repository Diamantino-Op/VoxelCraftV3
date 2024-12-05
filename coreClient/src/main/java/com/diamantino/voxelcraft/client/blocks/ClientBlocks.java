package com.diamantino.voxelcraft.client.blocks;

import com.diamantino.voxelcraft.common.registration.RegisteredBlock;

import java.util.HashMap;
import java.util.Map;

public class ClientBlocks {
    /**
     *  Blocks registry.
     *  Key: Block ID.
     *  Value: Block instance.
     */
    public static final Map<String, RegisteredBlock<?>> blocks = new HashMap<>();
}
