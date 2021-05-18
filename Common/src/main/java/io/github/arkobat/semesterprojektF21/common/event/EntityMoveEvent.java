package io.github.arkobat.semesterprojektF21.common.event;

import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * A class for handling move events for entities
 */
public class EntityMoveEvent extends EntityEvent implements Cancelable {

    private final @NotNull Location newLocation;
    private final @NotNull Location oldLocation;
    private boolean canceled;

    /**
     * A constructor for creating an EntityMoveEvent
     *
     * @param entity      an Entity object
     * @param newLocation the new {@link Location}
     * @param oldLocation the old {@link Location}
     */
    public EntityMoveEvent(Entity entity, @NotNull Location newLocation, @NotNull Location oldLocation) {
        super(entity);
        this.newLocation = newLocation;
        this.oldLocation = oldLocation;
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
     * A method for getting the new location
     *
     * @return returns the new location
     */
    public @NotNull Location getNewLocation() {
        return newLocation;
    }

    /**
     * A method for getting the old location
     *
     * @return returns the old location
     */
    public @NotNull Location getOldLocation() {
        return oldLocation;
    }
}
