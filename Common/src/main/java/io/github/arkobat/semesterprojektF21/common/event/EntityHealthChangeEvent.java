package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * An event class for handling health change events on entities
 */
public class EntityHealthChangeEvent extends EntityEvent implements Cancelable {

    private int health;
    private boolean canceled;

    /**
     * A constructor for creating a {@link EntityHealthChangeEvent}
     *
     * @param entity requires an {@link Entity} object
     * @param health takes a int value as the entities health
     */
    public EntityHealthChangeEvent(@NotNull Entity entity, int health) {
        super(entity);
        this.health = health;
    }

    /**
     * A method for getting the health value
     *
     * @return returns an int with the health value
     */
    public int getHealth() {
        return health;
    }

    /**
     * A method for setting the health
     *
     * @param health the int value representing the health value
     */
    public void setHealth(int health) {
        this.health = health;
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

}
