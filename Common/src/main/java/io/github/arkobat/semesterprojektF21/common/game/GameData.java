package io.github.arkobat.semesterprojektF21.common.game;

//import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

//@AllArgsConstructor
public class GameData {

    private final float delta;
    private final int displayWidth;
    private final int displayHeight;
    private final Set<Key> keys;

    public GameData(float delta, int displayWidth, int displayHeight, Set<Key> keys) {
        this.delta = delta;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.keys = keys;
    }

    public boolean isPressed(@NotNull Key key) {
        return keys.contains(key);
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
