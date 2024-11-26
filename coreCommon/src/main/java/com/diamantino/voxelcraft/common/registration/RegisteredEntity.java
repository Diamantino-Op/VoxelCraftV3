package com.diamantino.voxelcraft.common.registration;

import com.diamantino.voxelcraft.client.entities.ClientEntity;
import com.diamantino.voxelcraft.server.entities.ServerEntity;

import java.lang.reflect.InvocationTargetException;

/**
 * Represents a registered entity.
 *
 * @author Diamantino
 */
public class RegisteredEntity<C extends ClientEntity, S extends ServerEntity> {
    /**
     * The registry name of the entity.
     */
    public final String registryName;

    /**
     * The client-side entity class.
     */
    private final Class<C> clientEntityClass;

    /**
     * The server-side entity class.
     */
    private final Class<S> serverEntityClass;

    /**
     * Creates a new registered entity.
     *
     * @param registryName The registry name of the entity.
     * @param clientEntityClass The client-side entity class.
     * @param serverEntityClass The server-side entity class.
     */
    public RegisteredEntity(String registryName, Class<C> clientEntityClass, Class<S> serverEntityClass) {
        this.registryName = registryName;

        this.clientEntityClass = clientEntityClass;
        this.serverEntityClass = serverEntityClass;
    }

    /**
     * Gets the registry name of the entity.
     *
     * @return The registry name of the entity.
     */
    public String getRegistryName() {
        return registryName;
    }

    /**
     * Creates a new client entity instance.
     *
     * @param newEntityParameters The parameters to pass to the entity constructor.
     * @return The new client entity instance.
     * @throws NoSuchMethodException If the constructor does not exist.
     * @throws InvocationTargetException If the constructor throws an exception.
     * @throws InstantiationException If the entity cannot be instantiated.
     * @throws IllegalAccessException If the entity constructor is not accessible.
     */
    public C createNewClientEntity(Class<?>... newEntityParameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return clientEntityClass.getDeclaredConstructor(newEntityParameters).newInstance(newEntityParameters);
    }

    /**
     * Creates a new server entity instance.
     *
     * @param newEntityParameters The parameters to pass to the entity constructor.
     * @return The new server entity instance.
     * @throws NoSuchMethodException If the constructor does not exist.
     * @throws InvocationTargetException If the constructor throws an exception.
     * @throws InstantiationException If the entity cannot be instantiated.
     * @throws IllegalAccessException If the entity constructor is not accessible.
     */
    public S createNewServerEntity(Class<?>... newEntityParameters) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return serverEntityClass.getDeclaredConstructor(newEntityParameters).newInstance(newEntityParameters);
    }
}
