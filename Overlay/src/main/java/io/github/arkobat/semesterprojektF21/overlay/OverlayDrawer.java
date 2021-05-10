package io.github.arkobat.semesterprojektF21.overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.arkobat.semesterprojektF21.assetmanager.AssetLoader;
import io.github.arkobat.semesterprojektF21.assetmanager.TextureRenderService;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.entity.Player;
import io.github.arkobat.semesterprojektF21.common.game.GameData;
import io.github.arkobat.semesterprojektF21.common.game.GamePluginService;
import org.jetbrains.annotations.NotNull;

public class OverlayDrawer implements GamePluginService, TextureRenderService {

    private static Stage stage;
    private static Viewport viewport;
    private static SpriteBatch hudBatch;

    static final AssetLoader ASSET_LOADER = AssetLoader.getInstance("Overlay");
    private static TimeHud timeHud;
    private static ColorHud colorHud;

    private static boolean started;

    @Override
    public void start(@NotNull GameData gameData, @NotNull World world) {
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        hudBatch = new SpriteBatch();
        stage = new Stage(viewport, hudBatch);

        timeHud = new TimeHud();
        colorHud = new ColorHud();

        started = true;

    }

    @Override
    public void stop(@NotNull GameData gameData, @NotNull World world) {
        started = false;
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

}
