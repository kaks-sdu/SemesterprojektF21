package io.github.arkobat.kolorkarl.common.event;

/**
 * An event for changing levels
 */
public class LevelChangeEvent extends Event {

    private final String nextLevel;

    /**
     * Creates a new LevelChangeEvent
     * @param nextLevel the next level name
     */
    public LevelChangeEvent(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    /**
     * Gets the name of the next level
     * @return the next level name
     */
    public String getNextLevel() {
        return nextLevel;
    }
}
