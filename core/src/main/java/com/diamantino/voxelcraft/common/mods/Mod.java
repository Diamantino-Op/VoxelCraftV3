package com.diamantino.voxelcraft.common.mods;

public class Mod {
    public final String modId;
    public final String modName;
    public final String modDescription;
    public final String modVersion;

    public Mod(String modId, String modName, String modDescription, String modVersion) {
        this.modId = modId;
        this.modName = modName;
        this.modDescription = modDescription;
        this.modVersion = modVersion;
    }
}
