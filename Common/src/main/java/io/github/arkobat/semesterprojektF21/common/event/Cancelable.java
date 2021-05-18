package io.github.arkobat.semesterprojektF21.common.event;

/**
 * Allow cancellation of events
 */
public interface Cancelable {

    /**
     * Gets the cancellation state of this event.
     * @return  true if this event is cancelled
     */
    boolean isCanceled();

    /**
     * Sets the cancellation state of this event.
     * @param canceled true if you wish to cancel this event
     */
    void setCanceled(boolean canceled);

}
