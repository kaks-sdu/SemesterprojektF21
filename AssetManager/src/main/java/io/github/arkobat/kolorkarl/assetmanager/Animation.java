package io.github.arkobat.kolorkarl.assetmanager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.github.arkobat.kolorkarl.common.game.GameData;

public class Animation {

    private final Array<TextureRegion> frames;
    private final float maxFrameTime;
    private float currentFrameTime;
    private final int frameCount;
    private int frame;

    public Animation(String moduleName, String fileName, int frameCount, float cycleTime) {
        frames = new Array<>();

        Texture texture = AssetLoader.getInstance(moduleName).loadTexture(fileName);

        TextureRegion region = new TextureRegion(texture);
        TextureRegion temp;
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++) {
            temp = new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight());
            frames.add(temp);
        }
        this.frameCount = frameCount;
        this.maxFrameTime = cycleTime / frameCount;
        this.frame = 0;
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
        for (TextureRegion region : frames) {
            region.flip(true, false);
        }
    }

    public TextureRegion getFrame() {
        return frames.get(frame);
    }

    public void process(GameData gameData) {
        update(gameData.getDelta());
    }
}
