package io.github.arkobat.semesterprojektf21;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Arrays;

public enum Key {

    LEFT(Input.Keys.LEFT, Input.Keys.A),
    RIGHT(Input.Keys.RIGHT, Input.Keys.D),
    JUMP(Input.Keys.UP, Input.Keys.W),
    ATTACK(Input.Keys.SPACE);

    private int[] keys;

    Key(int... keys) {
        this.keys = keys;
    }

    public boolean isPressed() {
        return Arrays.stream(this.keys).anyMatch(Gdx.input::isKeyPressed);
    }

    public boolean isJustPressed() {
        return Arrays.stream(this.keys).anyMatch(Gdx.input::isKeyJustPressed);
    }

}
