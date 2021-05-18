package io.github.arkobat.kolorkarl.assetmanager.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ExtendedGameData {

    private final float delta;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    public ExtendedGameData(float delta, OrthogonalTiledMapRenderer renderer, OrthographicCamera camera, Viewport viewport) {
        this.delta = Math.min(delta, 0.001F);
        this.renderer = renderer;
        this.camera = camera;
        this.viewport = viewport;
    }

    public float getDelta() {
        return delta;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

}
