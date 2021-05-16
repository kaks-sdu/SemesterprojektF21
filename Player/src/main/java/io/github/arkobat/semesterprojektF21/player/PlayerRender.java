package io.github.arkobat.semesterprojektF21.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.arkobat.semesterprojektF21.assetmanager.TextureRenderService;
import io.github.arkobat.semesterprojektF21.assetmanager.model.ExtendedGameData;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.World;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;

public class PlayerRender implements TextureRenderService {

    @Override
    public void render(ExtendedGameData gameData, World world, SpriteBatch sb) {
        for (Entity entity : world.getEntities(PlayerImpl.class)) {
            PlayerImpl player = (PlayerImpl) entity;
            Location loc = player.getLocation();

            // Draw animation
            sb.draw(player.getCurrentAnimation().getFrame(), loc.getX() + player.getHitbox().getOffsetX(), loc.getY() + player.getHitbox().getOffsetY());
            return;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

}
