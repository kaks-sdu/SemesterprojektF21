package io.github.arkobat.semesterprojektF21.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.arkobat.semesterprojektF21.assetmanager.TextureRenderService;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.World;

public class TimeHud implements TextureRenderService {

    private final long startTime = System.currentTimeMillis();
    private final Label timeLabel;

    public TimeHud() {
        timeLabel = new Label("00:00:00", new Label.LabelStyle(new BitmapFont(OverlayDrawer.ASSET_LOADER.loadFile("fonts/Minecraft.fnt")), Color.WHITE));
        timeLabel.setPosition(5, Gdx.graphics.getHeight() - 50);
        timeLabel.setScale(3F);
    }

    @Override
    public void render(ExtendedGameData gameData, World world, SpriteBatch sb) {
        long millis = System.currentTimeMillis() - startTime;
        long minutes = (millis / 1000) / 60;
        int seconds = (int) ((millis / 1000) % 60);

        timeLabel.setText(String.format("%2d:%02d:%02d", minutes, seconds, millis % 1000));
        timeLabel.draw(sb, 1);
    }

}
