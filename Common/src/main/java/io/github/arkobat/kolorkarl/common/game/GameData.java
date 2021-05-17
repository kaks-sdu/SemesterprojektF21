package io.github.arkobat.kolorkarl.common.game;

public class GameData {

    private final float delta;

    public GameData(float delta) {
        this.delta = Math.min(delta, 0.001F);
    }

    public float getDelta() {
        return delta;
    }

}
