package com.diamantino.voxelcraft.common.utils;

public record ResourceLocation(String modId, String location) {
    public ResourceLocation(String location) {
        this("voxelcraft", location);
    }

    public static ResourceLocation fromString(String location) {
        String[] parts = location.split(":");
        return new ResourceLocation(parts[0], parts[1]);
    }

    public String toString() {
        return this.modId + ":" + this.location;
    }
}
