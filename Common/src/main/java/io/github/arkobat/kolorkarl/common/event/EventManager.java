package io.github.arkobat.kolorkarl.common.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for handling events
 */
public class EventManager {

    private static final List<EventListener> eventListeners = new ArrayList<>();

    /**
     * Registers an {@link EventListener}
     * @param eventListener the listener
     */
    public static void registerListener(EventListener eventListener) {
        System.out.println("Added listener: " + eventListener.getClass().getName());
        eventListeners.add(eventListener);
    }

    /**
     * Unregisters an {@link EventListener}
     * @param eventListener the listener
     */
    public static void unregisterListener(EventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    /**
     * Calls an event to be executed.
     * @param event the {@link Event}
     */
    public static void callEvent(Event event) {
        for (EventListener eventListener : eventListeners) {
            try {
                eventListener.callEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
