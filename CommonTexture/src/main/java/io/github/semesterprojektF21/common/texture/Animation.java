package io.github.semesterprojektF21.common.texture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.game.GameData;

public class Animation {

    Array<TextureRegion> frames;
    float maxFrameTime;
    float currentFrameTime;
    int frameCount;
    int frame;


    public Animation(String fileName, int frameCount, float cycleTime) {
        frames = new Array<TextureRegion>();
        Texture texture = new Texture(fileName);
        TextureRegion region = new TextureRegion(texture);
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            temp = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
            frames.add(temp);
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt) {
        currentFrameTime += dt;
        if (currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }
        if (frame >= frameCount)
            frame = 0;

    }

    public void flip() {
        for (TextureRegion region : frames)
            region.flip(true, false);
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    public void process(GameData gameData, Entity entity) {
        update(gameData.getDelta());
    }
}
