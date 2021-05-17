package io.github.arkobat.kolorkarl.common.event;

public class LevelChangeEvent extends Event {

    private final String nextLevel;

    public LevelChangeEvent(String nextLevel) {
        this.nextLevel = nextLevel;
    }

}
