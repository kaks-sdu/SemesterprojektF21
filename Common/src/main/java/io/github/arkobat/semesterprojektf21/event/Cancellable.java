package io.github.arkobat.semesterprojektf21.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancel);

}
