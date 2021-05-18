package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * An event class for defining entity shoot events
 */
public class EntityShootEvent extends EntityEvent {

    /**
     * A constructor for {@link EntityShootEvent}
     *
     * @param entity requires an Entity object
     */
    public EntityShootEvent(@NotNull Entity entity) {
        super(entity);
    }

}
