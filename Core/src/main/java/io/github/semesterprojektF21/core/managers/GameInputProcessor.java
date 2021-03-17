package io.github.semesterprojektF21.core.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.Key;

public class GameInputProcessor extends InputAdapter {
    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

    public boolean keyDown(int k) {
        if(k == Keys.UP) {
            gameData.getKeys().setKey(Key.UP, true);
        }
        if(k == Keys.LEFT) {
            gameData.getKeys().setKey(Key.LEFT, true);
        }
        if(k == Keys.DOWN) {
            gameData.getKeys().setKey(Key.DOWN, true);
        }
        if(k == Keys.RIGHT) {
            gameData.getKeys().setKey(Key.RIGHT, true);
        }
        if(k == Keys.ENTER) {
            gameData.getKeys().setKey(Key.ENTER, true);
        }
        if(k == Keys.ESCAPE) {
            gameData.getKeys().setKey(Key.ESCAPE, true);
        }
        if(k == Keys.SPACE) {
            gameData.getKeys().setKey(Key.SPACE, true);
        }
        if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(Key.SHIFT, true);
        }
        return true;
    }
	
    public boolean keyUp(int k) {
        if(k == Keys.UP) {
            gameData.getKeys().setKey(Key.UP, false);
        }
        if(k == Keys.LEFT) {
            gameData.getKeys().setKey(Key.LEFT, false);
        }
        if(k == Keys.DOWN) {
            gameData.getKeys().setKey(Key.DOWN, false);
        }
        if(k == Keys.RIGHT) {
            gameData.getKeys().setKey(Key.RIGHT, false);
        }
        if(k == Keys.ENTER) {
            gameData.getKeys().setKey(Key.ENTER, false);
        }
        if(k == Keys.ESCAPE) {
            gameData.getKeys().setKey(Key.ESCAPE, false);
        }
        if(k == Keys.SPACE) {
            gameData.getKeys().setKey(Key.SPACE, false);
        }
        if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(Key.SHIFT, false);
        }
        return true;
    }
}








