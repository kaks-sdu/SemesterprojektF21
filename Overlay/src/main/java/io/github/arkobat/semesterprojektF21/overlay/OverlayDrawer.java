package io.github.arkobat.semesterprojektF21.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.assetmanager.AssetLoader;
import io.github.arkobat.semesterprojektF21.assetmanager.TextureRenderService;
import io.github.arkobat.semesterprojektF21.common.Hitbox;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OverlayDrawer implements GamePluginService, TextureRenderService {

    static final AssetLoader ASSET_LOADER = AssetLoader.getInstance("Overlay");
    private static final List<ColorOverlay> overlayList = new ArrayList<>(3);
    private static final List<TextOverlay> textOverlayList = new ArrayList<>();
    private static long startTime = 0;

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        final float width = 10f;
        overlayList.add(new ColorOverlay(new Location(Gdx.graphics.getWidth() / 2f - 25 - width, Gdx.graphics.getHeight() - 5f), new Hitbox(25f, 25f)));
        overlayList.add(new ColorOverlay(new Location(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 5f), new Hitbox(45f, 45)));
        overlayList.add(new ColorOverlay(new Location(Gdx.graphics.getWidth() / 2f + 45 + width, Gdx.graphics.getHeight() - 5f), new Hitbox(25, 25)));

        textOverlayList.add(new TextOverlay(new Location(50, 50), new Hitbox(15, 5)));
     //   textOverlayList.get(0).setAlignment(Alignment.TOP_RIGHT);

        startTime = System.currentTimeMillis();
        for (ColorOverlay overlay : overlayList) {
            overlay.setAlignment(Alignment.TOP);
        }

    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        overlayList.clear();
        textOverlayList.clear();
    }

    @Override
    public void render(ExtendedGameData gameData, World world, SpriteBatch sb) {
        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;
            overlayList.get(0).draw(player.getPreviousColor());
            overlayList.get(1).draw(player.getColor());
            overlayList.get(2).draw(player.getNextColor());
            sb.end();
            sb.begin();

            long millis = System.currentTimeMillis() - startTime;
            long minutes = (millis / 1000)  / 60;
            int seconds = (int) ((millis / 1000) % 60);

            textOverlayList.get(0).draw(sb, String.format("%2d:%02d:%02d", minutes, seconds, millis % 1000));
            break;
        }
    }

}
