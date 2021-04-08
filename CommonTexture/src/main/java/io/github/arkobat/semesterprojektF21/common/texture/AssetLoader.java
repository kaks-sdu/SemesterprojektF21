package io.github.arkobat.semesterprojektF21.common.texture;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;

//TODO: Move to managers folder without breaking
/*
    Still need to change many things here. AssetLoader responsibility should be everything related to loading textures, maps, music etc.
    This means getRawFilePath will be changed, as it is bad practice!!
 */
public class AssetLoader {

    private static AssetLoader instance;
    private AssetManager assetManager;
    private TmxMapLoader mapLoader;

    public AssetLoader(){
        AssetsJarFileResolver jfhr = new AssetsJarFileResolver();
        assetManager = new AssetManager(jfhr);
        mapLoader = new TmxMapLoader(jfhr);
    }

    public static AssetLoader getInstance() {
        if(instance == null){
            instance = new AssetLoader();
        }
        return instance;
    }

    @Deprecated
    public AssetManager getAssetManager(){
        return assetManager;
    }

    private String getJarUrl(String moduleName, String fileName){
        String jarName = moduleName + "-1.0-SNAPSHOT.jar!";
        // Get texture from relative path of jar name
        String jarUrl =   java.nio.file.Paths.get(new File("").getAbsolutePath(), "target", jarName, fileName).toString();
        jarUrl = jarUrl.replace("runner", "" + moduleName).replace('\\', '/');
        return jarUrl;
    }

    public Texture loadTexture(String moduleName, String fileName){
        String jarUrl = getJarUrl(moduleName, fileName);
        System.out.println("Jar URL: " + jarUrl);
        // Load the texture
        assetManager.load(jarUrl, com.badlogic.gdx.graphics.Texture.class);
        assetManager.finishLoading();

        return assetManager.get(jarUrl, com.badlogic.gdx.graphics.Texture.class);
    }

    public TiledMap loadMap(String moduleName, String fileName){
        String jarUrl = getJarUrl(moduleName, fileName);
        return mapLoader.load(jarUrl);
    }

    public String getRawFilePath(String moduleName, String fileName){
        //TODO: Fix to open jar file instead
        //String jarName = moduleName + "-1.0-SNAPSHOT.jar!";
        // Get texture from relative path of jar name
        String jarUrl =   java.nio.file.Paths.get(new File("").getAbsolutePath(), "target", "classes", fileName).toString();
        jarUrl = jarUrl.replace("runner", "" + moduleName).replace('\\', '/');
        return jarUrl;

    }
}
