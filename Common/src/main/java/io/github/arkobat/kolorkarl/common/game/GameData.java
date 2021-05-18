package io.github.arkobat.kolorkarl.common.game;

/**
 * A class that defines game data
 */
public class GameData {

    private final float delta;

    /**
     * A constructor for creating a {@link GameData} object
     * @param delta the delta value
     */
    public GameData(float delta) {
        this.delta = Math.min(delta, 0.001F);
    }

    /**
     * A method for getting the delta value
     * @return the delta value
     */
    public float getDelta() {
        return delta;
    }

}
