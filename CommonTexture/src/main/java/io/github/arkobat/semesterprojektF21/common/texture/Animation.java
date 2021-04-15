package io.github.arkobat.semesterprojektF21.common.texture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.github.arkobat.semesterprojektF21.common.game.GameData;

public class Animation {

    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
    private boolean flipped;
    private int xoffset;
    private int yoffset;
    private int frameWidth;


    public Animation(String moduleName, String fileName, int frameCount, float cycleTime, int xoffset, int yoffset){
        frames = new Array<>();

        Texture texture = AssetLoader.getInstance().loadTexture(moduleName, fileName);

        TextureRegion region = new TextureRegion(texture);
        TextureRegion temp;
        frameWidth = region.getRegionWidth() / frameCount;
        for(int i = 0; i < frameCount; i++){ // i * framewidth + 4  --> højre  | i * framewidth - 4 --> venstre | getregionx -->
            temp = new TextureRegion(region, i * frameWidth - xoffset, yoffset, frameWidth, region.getRegionHeight());
            frames.add(temp);
            System.out.println("Original region x: " + temp.getRegionX());
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
        this.xoffset = xoffset;
        this.yoffset = yoffset;
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
        flipped = !flipped;
        /*for(int i = 0; i < frameCount; i++){
            TextureRegion region = frames.get(i);
            if(flipped){
                region.setRegionX(i * frameWidth - xoffset);
            }else{
                region.setRegionX(i * frameWidth + xoffset);
            }
            System.out.println("Region x " + region.getRegionX());
            region.flip(true, false);
        }*/
        for(TextureRegion region : frames){
            /*if(flipped){
                region.setRegionX(region.getRegionX() - xoffset);
            }else{
                region.setRegionX(region.getRegionX() + xoffset);
            }*/
            region.flip(true, false);

            // i*framewidth - (-4) --> i * framewidth + 4 --> højre  | i * framewidth - 4 --> venstre | getregionx --> i * framewidth + 4
            if (flipped){
                region.setRegionX(region.getRegionX() - 8);
                System.out.println("Region x: " + region.getRegionX());
            }else{
                region.setRegionX(region.getRegionX() + 8);
            }
            System.out.println("x offset: " + xoffset);
        }
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

    public void process(GameData gameData) {
        update(gameData.getDelta());
    }
}
