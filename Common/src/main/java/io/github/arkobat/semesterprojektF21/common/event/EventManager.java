package io.github.arkobat.semesterprojektF21.common.event;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private static final List<EventListener> eventListeners = new ArrayList<>();

    public static void registerListener(EventListener eventListener) {
        System.out.println("Added listener: " + eventListener.getClass().getName());
        eventListeners.add(eventListener);
    }

    public static void unregisterListener(EventListener eventListener) {
        eventListeners.remove(eventListener);
    }

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
