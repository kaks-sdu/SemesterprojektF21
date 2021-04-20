package io.github.arkobat.semesterprojektF21.common.event;

import org.jetbrains.annotations.NotNull;

public class EventListener {

    public void onEntity(EntityEvent event) {
    }

    public void onEntityMove(EntityMoveEvent event) {
    }

    public void onEntityTurn(EntityTurnEvent event) {

    }

    final void callEvent(@NotNull Event event) {
        if (EntityEvent.class.equals(event.getClass())) {
            onEntity((EntityEvent) event);
        } else if (EntityMoveEvent.class.equals(event.getClass())) {
            onEntityMove((EntityMoveEvent) event);
        } else if (EntityTurnEvent.class.equals(event.getClass())) {
            onEntityTurn((EntityTurnEvent) event);
        } else {
            throw new IllegalStateException("Unexpected value: " + event.getClass());
        }
    }

}
