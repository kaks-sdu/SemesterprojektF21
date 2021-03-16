package io.github.semesterprojektF21.common.data.entityparts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.github.semesterprojektF21.common.data.Entity;
import io.github.semesterprojektF21.common.data.GameData;

public class AnimationPart implements EntityPart {

    Array<TextureRegion> frames;
    float maxFrameTime;
    float currentFrameTime;
    int frameCount;
    int frame;


    public AnimationPart(String fileName, int frameCount, float cycleTime){
        frames = new Array<>();
        TextureRegion region = new TextureRegion(new Texture(fileName));
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++){
            temp = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
            frames.add(temp);
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
        System.out.println("AnimationPart Called!");
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
        System.out.println("Flip called!");
        for(TextureRegion region : frames)
            region.flip(true, false);
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
    @Override
    public void process(GameData gameData, Entity entity) {
        update(gameData.getDelta());
    }
}
