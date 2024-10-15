package com.diamantino.voxelcraft.common.entities;

import com.diamantino.voxelcraft.client.entities.ClientEntity;
import com.diamantino.voxelcraft.common.registration.RegisteredEntity;
import com.diamantino.voxelcraft.server.entities.ServerEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Entities registry.
 *
 * @author Diamantino
 */
public class Entities {
    /**
     *  Entities registry.
     *  Key: Entity ID.
     *  Value: RegisteredEntity instance.
     */
    public static final Map<String, RegisteredEntity<?, ?>> entities = new HashMap<>();

    /**
     * Registers an entity.
     *
     * @param id Entity ID.
     * @param clientEntity Client-side entity class.
     * @param serverEntity Server-side entity class.
     * @param <C> Client-side entity type.
     * @param <S> Server-side entity type.
     * @return The registered entity.
     */
    public static <C extends ClientEntity, S extends ServerEntity> RegisteredEntity<C, S> registerEntity(String id, Class<C> clientEntity, Class<S> serverEntity) {
        RegisteredEntity<C, S> registeredEntity = new RegisteredEntity<>(id, clientEntity, serverEntity);
        entities.put(id, registeredEntity);

        return registeredEntity;
    }
}
