package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * An event class for handling "deaths" of entities
 */
public class EntityDeathEvent extends EntityEvent {

    /**
     * A constructor for creating a death event for an entity
     *
     * @param entity requires an Entity object
     */
    public EntityDeathEvent(@NotNull Entity entity) {
        super(entity);
    }

}
