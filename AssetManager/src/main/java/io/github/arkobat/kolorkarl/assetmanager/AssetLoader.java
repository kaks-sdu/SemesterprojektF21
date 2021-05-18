package io.github.arkobat.kolorkarl.assetmanager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

//TODO: Move to managers folder without breaking
/*
    Still need to change many things here. AssetLoader responsibility should be everything related to loading textures, maps, music etc.
    This means getRawFilePath will be changed, as it is bad practice!!
 */
public class AssetLoader {

    public static float BACKGROUND_MUSIC = 0.2F;
    public static float EFFECT_SOUNDS = 0.3F;

    private static Map<String, AssetLoader> instances = new HashMap<>();
    private final String module;
    private AssetManager assetManager;
    private TmxMapLoader mapLoader;

    private AssetLoader(String module) {
        this.module = module;
        AssetsJarFileResolver jfhr = new AssetsJarFileResolver();
        assetManager = new AssetManager(jfhr);
        mapLoader = new TmxMapLoader(jfhr);
    }

    public static AssetLoader getInstance(String module) {
        AssetLoader instance = instances.get(module);
        if (instance == null) {
            instance = new AssetLoader(module);
            instances.put(module, instance);
        }
        return instance;
    }

    public FileHandle loadFile(String fileName) {
        return new FileHandle(getRawFilePath(fileName));
    }

    private String getJarUrl(String fileName) {
        String jarName = module + "-1.0-SNAPSHOT.jar!";
        // Get texture from relative path of jar name
        String jarUrl = java.nio.file.Paths.get(new File("").getAbsolutePath(), "target", jarName, fileName).toString();
        jarUrl = jarUrl.replace("runner", "" + module).replace('\\', '/');
        return jarUrl;
    }

    public Texture loadTexture(String fileName) {
        String jarUrl = getJarUrl(fileName);
        // Load the texture
        assetManager.load(jarUrl, com.badlogic.gdx.graphics.Texture.class);
        assetManager.finishLoading();

        return assetManager.get(jarUrl, com.badlogic.gdx.graphics.Texture.class);
    }

    public void playSound(String sound, String... folders) {
        playSound(String.join(File.separator, folders) + File.separator + sound);
    }

    public void playSound(String sound) {
        Sound s = Gdx.audio.newSound(new FileHandle(getRawFilePath(sound)));
        double random = ThreadLocalRandom.current().nextDouble(0.75, 1.25);
        s.play(EFFECT_SOUNDS, (float) random, 0F);
    }

    @Deprecated
    public String getRawFilePath(String fileName) {
        //TODO: Fix to open jar file instead
        //String jarName = moduleName + "-1.0-SNAPSHOT.jar!";
        // Get texture from relative path of jar name
        String jarUrl = java.nio.file.Paths.get(new File("").getAbsolutePath(), "target", "classes", fileName).toString();
        jarUrl = jarUrl.replace("runner", "" + module).replace('\\', '/');
        return jarUrl;

    }
}
