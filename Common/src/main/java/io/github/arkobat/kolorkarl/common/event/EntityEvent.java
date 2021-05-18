package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract event class for handling an Entity
 */
public abstract class EntityEvent extends Event {

    private final @NotNull Entity entity;

    /**
     * A constructor taking in an Entity object
     *
     * @param entity requires an Entity object
     */
    public EntityEvent(@NotNull Entity entity) {
        this.entity = entity;
    }

    /**
     * A method for getting the entity field
     *
     * @return an entity
     */
    public @NotNull Entity getEntity() {
        return entity;
    }
}
