package com.diamantino.voxelcraft.common.utils;

/**
 * Represents a resource location.
 *
 * @author Diamantino
 */
public record ResourceLocation(String modId, String location) {
    /**
     * The default constructor.
     *
     * @param location The location.
     */
    public ResourceLocation(String location) {
        this("voxelcraft", location);
    }

    /**
     * Create a resource location from a string.
     *
     * @param location The location.
     * @return The resource location.
     */
    public static ResourceLocation fromString(String location) {
        String[] parts = location.split(":");
        return new ResourceLocation(parts[0], parts[1]);
    }

    /**
     * Get the string representation of the resource location.
     *
     * @return The string representation.
     */
    public String toString() {
        return this.modId + ":" + this.location;
    }
}
