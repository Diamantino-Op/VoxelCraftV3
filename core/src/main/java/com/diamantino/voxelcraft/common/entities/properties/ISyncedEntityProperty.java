package com.diamantino.voxelcraft.common.entities.properties;

/**
 * Represents an entity property that can be synchronized between the server and the client.
 *
 * @author Diamantino
 */
public interface ISyncedEntityProperty {
    /**
     * Gets the property name.
     *
     * @return The property name.
     */
    String getName();

    /**
     * Gets the property value.
     *
     * @return The property value.
     */
    Object getValue();

    /**
     * Saves the entity property data.
     *
     * @return The byte data that needs to be saved in the chunk.
     */
    byte[] saveData();

    /**
     * Loads the entity property data.
     *
     * @param entityData The data loaded from the chunk file.
     */
    void loadData(byte[] entityData);
}
