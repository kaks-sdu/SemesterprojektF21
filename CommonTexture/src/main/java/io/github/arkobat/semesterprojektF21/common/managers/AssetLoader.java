package io.github.arkobat.semesterprojektF21.common.managers;

import com.badlogic.gdx.assets.AssetManager;

public class AssetLoader {
    private static AssetManager instance = null;

    public static AssetManager getInstance(){
        if(instance == null){
            AssetsJarFileResolver jfhr = new AssetsJarFileResolver();
            instance = new AssetManager(jfhr);
        }
        return instance;
    }
}
