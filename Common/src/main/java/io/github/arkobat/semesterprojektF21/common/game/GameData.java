package io.github.arkobat.semesterprojektF21.common.game;

//import lombok.AllArgsConstructor;

//@AllArgsConstructor
public class GameData {

    private final float delta;
    private final int displayWidth;
    private final int displayHeight;

    public GameData(float delta, int displayWidth, int displayHeight) {
        this.delta = delta;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
    }

    public float getDelta() {
        return delta;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

}
