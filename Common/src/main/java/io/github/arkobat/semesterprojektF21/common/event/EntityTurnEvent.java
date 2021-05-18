package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.Direction;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * An event class for representing turn events for entities
 */
public class EntityTurnEvent extends EntityEvent implements Cancelable {

    private final @NotNull Direction direction;
    private boolean canceled;

    /**
     * A constructor for creating a {@link EntityTurnEvent}
     *
     * @param entity    requires a {@link Entity} object
     * @param direction requires a Direction object
     */
    public EntityTurnEvent(@NotNull Entity entity, @NotNull Direction direction) {
        super(entity);
        this.direction = direction;
        this.canceled = false;
    }

    /**
     * A method for checking if the event is canceled
     *
     * @return true if the event is canceled
     */
    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    /**
     * A method for setting the canceled state
     *
     * @param canceled true if you wish to cancel this event
     */
    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    /**
     * A method for getting the direction
     *
     * @return returns the direction
     */
    public @NotNull Direction getDirection() {
        return direction;
    }
}
