package io.github.arkobat.semesterprojektf21;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

    public static float ratio;

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Drop";
        config.width = 480;
        config.height = 270;

        ratio = config.width / 480F;

        config.useGL30 = false;
        config.useHDPI = false;
        config.forceExit = true;
        config.vSyncEnabled = false;

        config.backgroundFPS = -1;
        config.foregroundFPS = -1;

        new LwjglApplication(new App(), config);
    }

}
