package io.github.arkobat.kolorkarl.common.event;

import io.github.arkobat.kolorkarl.common.Color;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * An event for entities changing colors
 */
public class EntityColorChangeEvent extends EntityEvent implements Cancelable {

    private final Color oldColor;
    private final Color newColor;
    private boolean canceled;

    /**
     * Create a new {@link EntityColorChangeEvent}
     * @param entity the entity that changes {@link Color}
     * @param oldColor the old {@link Color}
     * @param newColor the new {@link Color}
     */
    public EntityColorChangeEvent(@NotNull Entity entity, Color oldColor, Color newColor) {
        super(entity);
        this.newColor = newColor;
        this.oldColor = oldColor;
    }

    /**
     * Get the new {@link Color} the {@link Entity} changes to.
     * @return the new {@link Color}
     */
    public Color getNewColor() {
        return newColor;
    }

    /**
     * Get the old {@link Color}, the {@link Entity} changes away from
     * @return the old {@link Color}
     */
    public Color getOldColor() {
        return oldColor;
    }

    /**
     * Check if the event is canceled
     * @return true if canceled
     */
    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    /**
     * Set the canceled state of the event
     * @param canceled the canceled state
     */
    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
