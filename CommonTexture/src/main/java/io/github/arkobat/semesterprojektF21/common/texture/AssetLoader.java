package io.github.arkobat.semesterprojektF21.common.texture;

import com.badlogic.gdx.assets.AssetManager;

//TODO: Move to managers folder without breaking
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
