package io.github.arkobat.semesterprojektF21.common;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface World {

    /**
     * Get all entities in the world
     *
     * @return a collection on entities
     */
    @NotNull Collection<Entity> getEntities();

    /**
     * Get all of specific entities in the world
     *
     * @param entityTypes Filter of which types wanted
     * @param <E>         Filter of which types wanted
     * @return a filtered collection on entities
     */
    @NotNull <E extends Entity> Collection<Entity> getEntities(@NotNull Class<E>... entityTypes);


    /**
     * Adds an entity.
     *
     * @param entity - an entity has a location and a vector for velocity.
     */
    void addEntity(@NotNull Entity entity);

    /**
     * Removes an entity.
     *
     * @param entity - an entity has a location and a vector for velocity.
     */
    void removeEntity(@NotNull Entity entity);

}