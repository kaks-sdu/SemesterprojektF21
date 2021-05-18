package io.github.arkobat.kolorkarl.common.event;

import org.jetbrains.annotations.NotNull;

/**
 * An abstract Event class, used to call events
 */
public abstract class EventListener {

    /**
     * Called on {@link EntityEvent}
     * @param event the event
     */
    public void onEntity(EntityEvent event) {
    }

    /**
     * Called on {@link EntityColorChangeEvent}
     * @param event the event
     */
    public void onEntityColorChange(EntityColorChangeEvent event) {
    }

    /**
     * Called on {@link EntityDeathEvent}
     * @param event the event
     */
    public void onEntityDeath(EntityDeathEvent event) {
    }

    /**
     * Called on {@link EntityHealthChangeEvent}
     * @param event the event
     */
    public void onEntityHealthChange(EntityHealthChangeEvent event) {
    }

    /**
     * Called on {@link EntityMoveEvent}
     * @param event the event
     */
    public void onEntityMove(EntityMoveEvent event) {
    }

    /**
     * Called on {@link EntityShootEvent}
     * @param event the event
     */
    public void onEntityShoot(EntityShootEvent event) {
    }

    /**
     * Called on {@link EntityTurnEvent}
     * @param event the event
     */
    public void onEntityTurn(EntityTurnEvent event) {
    }

    /**
     * Called on {@link LevelChangeEvent}
     * @param event the event
     */
    public void onLevelChange(LevelChangeEvent event) {
    }

    /**
     * Called on {@link WorldStartEvent}
     * @param event the event
     */
    public void onWorldStart(WorldStartEvent event) {
    }

    /**
     * Figure out which event is called
     * @param event the called event
     */
    final void callEvent(@NotNull Event event) {
        if (event.getClass() == EntityEvent.class) {
            onEntity((EntityEvent) event);
        } else if (event.getClass() == EntityColorChangeEvent.class) {
            onEntityColorChange((EntityColorChangeEvent) event);
        } else if (event.getClass() == EntityDeathEvent.class) {
            onEntityDeath((EntityDeathEvent) event);
        } else if (event.getClass() == EntityHealthChangeEvent.class) {
            onEntityHealthChange((EntityHealthChangeEvent) event);
        } else if (event.getClass() == EntityMoveEvent.class) {
            onEntityMove((EntityMoveEvent) event);
        } else if (event.getClass() == EntityShootEvent.class) {
            onEntityShoot((EntityShootEvent) event);
        } else if (event.getClass() == EntityTurnEvent.class) {
            onEntityTurn((EntityTurnEvent) event);
        } else if (event.getClass() == LevelChangeEvent.class) {
            onLevelChange((LevelChangeEvent) event);
        } else if (event.getClass() == WorldStartEvent.class) {
            onWorldStart((WorldStartEvent) event);
        } else {
            throw new IllegalArgumentException("Unknown event");
        }
    }

}
