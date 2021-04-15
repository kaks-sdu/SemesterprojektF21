package io.github.arkobat.semesterprojektF21.common.event;

public interface Cancelable {

    boolean isCanceled();

    void setCanceled(boolean canceled);

}
