package io.github.arkobat.semesterprojektF21.common.texture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.github.arkobat.semesterprojektF21.common.game.GameData;

import java.io.File;

public class Animation {

    Array<TextureRegion> frames;
    float maxFrameTime;
    float currentFrameTime;
    int frameCount;
    int frame;


    public Animation(String jarName, String moduleName, String fileName, int frameCount, float cycleTime){
        frames = new Array<>();

        // Get texture from relative path of jar name
        String jarUrl =   java.nio.file.Paths.get(new File("").getAbsolutePath(), "target", jarName, fileName).toString();
        jarUrl = jarUrl.replace("runner", "" + moduleName).replace('\\', '/');

        //TODO: Use AssetLoader loadTexture method instead

        // Load the texture
        AssetLoader.getInstance().getAssetManager().load(jarUrl, Texture.class);
        AssetLoader.getInstance().getAssetManager().finishLoading();

        Texture texture = AssetLoader.getInstance().getAssetManager().get(jarUrl, Texture.class);

        TextureRegion region = new TextureRegion(texture);
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++){
            temp = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
            frames.add(temp);
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame++;
            currentFrameTime = 0;
        }
        if(frame >= frameCount)
            frame = 0;

    }

    public void flip(){
        for(TextureRegion region : frames)
            region.flip(true, false);
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    public void process(GameData gameData) {
        update(gameData.getDelta());
    }
}
