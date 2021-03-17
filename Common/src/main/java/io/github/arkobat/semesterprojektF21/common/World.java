package io.github.arkobat.semesterprojektF21.common;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;

import java.util.Collection;

public interface World {

    /**
     * Gets all entities.
     * @return Collection<Entity> - a collection of all entities
     */
    Collection<Entity> getEntities();
    <E extends Entity> Collection<Entity> getEntities(Class<E>... entityTypes);


    /**
     * Adds an entity.
     * @param entity - an entity has a location and a vector for velocity.
     */
    void addEntity(Entity entity);

    /**
     * Removes an entity.
     * @param entity - an entity has a location and a vector for velocity.
     */
    void removeEntity(Entity entity);

}