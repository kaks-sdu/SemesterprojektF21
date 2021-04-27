package io.github.arkobat.semesterprojektF21.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.arkobat.semesterprojektF21.common.game.Key;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KeyController {

    private static Map<Key, int[]> keyBindings = new HashMap<>();

    static {
        setKey(Key.LEFT, Input.Keys.LEFT, Input.Keys.A);
        setKey(Key.RIGHT, Input.Keys.RIGHT, Input.Keys.D);
        setKey(Key.JUMP, Input.Keys.UP, Input.Keys.W);
        setKey(Key.ATTACK, Input.Keys.SPACE);
    }

    private KeyController() {
    }

    private static void setKey(Key key, int... keys) {
        if (keys.length == 0) {
            throw new IllegalArgumentException("The key must have be bound");
        }
        keyBindings.put(key, keys);
    }

    public static @NotNull Set<Key> getPressedKeys() {
        Set<Key> set = new HashSet<>();
        for (Key key : Key.values()) {
            Input input = Gdx.input;
            for (int i : keyBindings.get(key)) {
                if (input.isKeyPressed(i)) {
                    set.add(key);
                    break;
                }
            }
        }
        return set;
    }

    public  void ca() {
    }

}
