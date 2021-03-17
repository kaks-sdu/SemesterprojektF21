package io.github.semesterprojektF21;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.github.arkobat.semesterprojektF21.common.game.Key;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
            throw new IllegalArgumentException("The key must have be binded");
        }
        keyBindings.put(key, keys);
    }

    public static @NotNull Set<Key> getPressedKeys() {
        return Arrays.stream(Key.values()).filter(key -> Arrays.stream(keyBindings.get(key)).anyMatch(Gdx.input::isKeyPressed)).collect(Collectors.toSet());
    }

}
