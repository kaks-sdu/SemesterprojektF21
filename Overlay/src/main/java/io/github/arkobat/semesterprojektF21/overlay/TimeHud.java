package io.github.arkobat.semesterprojektF21.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.arkobat.semesterprojektF21.assetmanager.TextureRenderService;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.event.EntityDeathEvent;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;

public class TimeHud extends EventListener implements TextureRenderService {

    private int deaths = 0;
    private final long startTime = System.currentTimeMillis();
    private final Label timeLabel;

    public TimeHud() {
        timeLabel = new Label("00:00:00", new Label.LabelStyle(new BitmapFont(OverlayDrawer.ASSET_LOADER.loadFile("fonts/Minecraft.fnt")), Color.WHITE));
        timeLabel.setScale(3F);
    }

    @Override
    public void render(ExtendedGameData gameData, World world, SpriteBatch sb) {
        long millis = System.currentTimeMillis() - startTime;
        long minutes = (millis / 1000) / 60;
        int seconds = (int) ((millis / 1000) % 60);

        String time = String.format("%d:%02d:%02d", minutes, seconds, millis % 1000);
        String deaths = "Deaths: " + this.deaths;
        timeLabel.setText(String.join("\n", time, deaths));
        timeLabel.draw(sb, 1);
    }

    @Override
    public void resize(int width, int height) {
        timeLabel.setPosition(15, Gdx.graphics.getHeight() - 65);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        deaths++;
    }
}
