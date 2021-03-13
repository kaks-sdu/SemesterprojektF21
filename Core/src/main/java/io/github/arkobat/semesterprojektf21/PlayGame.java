package io.github.arkobat.semesterprojektf21;

import com.badlogic.gdx.Game;

public class PlayGame extends Game {

    @Override
    public void create() {
        setScreen(new PlayScreen());
    }

}
