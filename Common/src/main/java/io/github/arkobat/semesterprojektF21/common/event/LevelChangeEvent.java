package io.github.arkobat.semesterprojektF21.common.event;

public class LevelChangeEvent extends Event {

    private final String nextLevel;

    public LevelChangeEvent(String nextLevel) {
        this.nextLevel = nextLevel;
    }

}
