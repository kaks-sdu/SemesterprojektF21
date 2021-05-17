package io.github.arkobat.kolorkarl.common.event;

public interface Cancelable {

    boolean isCanceled();

    void setCanceled(boolean canceled);

}
