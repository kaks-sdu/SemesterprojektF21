package io.github.arkobat.kolorkarl.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.kolorkarl.assetmanager.AssetLoader;
import io.github.arkobat.kolorkarl.assetmanager.TextureRenderService;
import io.github.arkobat.kolorkarl.assetmanager.model.ExtendedGameData;
import io.github.arkobat.kolorkarl.common.World;
import io.github.arkobat.kolorkarl.common.entity.Entity;
import io.github.arkobat.kolorkarl.common.entity.Player;
import io.github.arkobat.kolorkarl.common.event.EventManager;
import io.github.arkobat.kolorkarl.common.game.GameData;
import io.github.arkobat.kolorkarl.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

public class OverlayDrawer implements GamePluginService, TextureRenderService {

    private static SpriteBatch hudBatch;

    static final AssetLoader ASSET_LOADER = AssetLoader.getInstance("Overlay");
    private static TimeHud timeHud;
    private static ColorHud colorHud;

    private static boolean started;

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        hudBatch = new SpriteBatch();

        timeHud = new TimeHud();
        colorHud = new ColorHud();

        started = true;
        EventManager.registerListener(timeHud);
    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        started = false;
        EventManager.unregisterListener(timeHud);
    }

    @Override
    public void render(ExtendedGameData gameData, World world, SpriteBatch sb) {
        if (!started) return;

        hudBatch.begin();
        timeHud.render(gameData, world, hudBatch);
        hudBatch.end();

        for (Entity entity : world.getEntities(Player.class)) {
            Player player = (Player) entity;
            colorHud.draw(player);
            break;
        }

    }

    @Override
    public void resize(int width, int height) {
        hudBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        timeHud.resize(width, height);
        colorHud.resize(width, height);
    }

    @Override
    public void dispose() {
        hudBatch.dispose();
    }
}
