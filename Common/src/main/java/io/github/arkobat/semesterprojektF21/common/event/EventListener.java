package io.github.arkobat.semesterprojektF21.common.event;

import org.jetbrains.annotations.NotNull;

public class EventListener {

    // private final static Map<Class<? extends Event>, BiConsumer<EventListener, Event>> eventMap = new HashMap<>();

    static {
        //  eventMap.put(EntityEvent.class, ((listener, event) -> listener.onEntity((EntityEvent) event)));
        //  eventMap.put(EntityMoveEvent.class, ((listener, event) -> listener.onEntityMove((EntityMoveEvent) event)));
        //  eventMap.put(EntityShootEvent.class, ((listener, event) -> listener.onEntityShoot((EntityShootEvent) event)));
        //  eventMap.put(EntityTurnEvent.class, ((listener, event) -> listener.onEntityTurn((EntityTurnEvent) event)));
    }

    public void onEntity(EntityEvent event) {
    }

    public void onEntityMove(EntityMoveEvent event) {
    }

    public void onEntityShoot(EntityShootEvent event) {
    }

    public void onEntityTurn(EntityTurnEvent event) {
    }

    final void callEvent(@NotNull Event event) {
        if (!event.getClass().getSimpleName().equals("EntityMoveEvent")) {
            System.out.println("  Called event " + event.getClass().getSimpleName());
        }
        if (event.getClass() == EntityEvent.class) {
            onEntity((EntityEvent) event);
        } else if (event.getClass() == EntityMoveEvent.class) {
            onEntityMove((EntityMoveEvent) event);
        } else if (event.getClass() == EntityShootEvent.class) {
            System.out.println("    is shoot event");
            onEntityShoot((EntityShootEvent) event);
        } else if (event.getClass() == EntityTurnEvent.class) {
            onEntityTurn((EntityTurnEvent) event);
        } else {
            throw new IllegalArgumentException("Unknown event");
        }

        //  eventMap.get(event.getClass()).accept(this, event);
    }

}
