package com.diamantino.voxelcraft.common.registration;

import com.diamantino.voxelcraft.common.mods.Mod;

import java.util.HashMap;
import java.util.Map;

/**
 * Mods registry.
 *
 * @author Diamantino
 */
public class Mods {
    /**
     *  Mods registry.
     *  Key: Mod ID.
     *  Value: Mod instance.
     */
    public static final Map<String, Mod> mods = new HashMap<>();
}
