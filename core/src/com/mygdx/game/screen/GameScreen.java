package com.mygdx.game.screen;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.AssetPaths;
import com.mygdx.game.common.GameManager;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.system.BoundsSystem;
import com.mygdx.game.ecs.system.CameraMovementSystem;
import com.mygdx.game.ecs.system.CollisionSystem;
import com.mygdx.game.ecs.system.HudRenderSystem;
import com.mygdx.game.ecs.system.MovementSystem;
import com.mygdx.game.ecs.system.CarInputSystem;
import com.mygdx.game.ecs.system.CarMusicSystem;
import com.mygdx.game.ecs.system.RenderSystem;
import com.mygdx.game.ecs.system.WorldWrapSystem;
import com.mygdx.game.ecs.system.debug.DebugCameraSystem;
import com.mygdx.game.ecs.system.debug.DebugGridRenderSystem;
import com.mygdx.game.ecs.system.debug.DebugInputSystem;
import com.mygdx.game.ecs.system.debug.DebugRenderSystem;
import com.mygdx.game.ecs.system.passive.EntityFactorySystem;
import com.mygdx.game.ecs.system.passive.SoundSystem;
import com.mygdx.game.ecs.system.passive.StartUpSystem;
import com.mygdx.game.ecs.system.passive.TiledSystem;

/**
 * Artwork from https://goodstuffnononsense.com/about/
 * https://goodstuffnononsense.com/hand-drawn-icons/space-icons/
 */

public class GameScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private BitmapFont font;
    private PooledEngine engine;

    // OrthoCachedTiledMapRenderer mapRenderer;

    public GameScreen(MyGdxGame game) {
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    @Override
    public void show() {
        TiledMap map = assetManager.get(AssetPaths.TILES);   // Rethink add with manager?

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
        hudViewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT);
        renderer = new ShapeRenderer();
        font = assetManager.get(AssetDescriptors.FONT32);
        engine = new PooledEngine();

        // passive systems
        engine.addSystem(new EntityFactorySystem(assetManager));
        engine.addSystem(new SoundSystem(assetManager));
        engine.addSystem(new TiledSystem(map));
        engine.addSystem(new StartUpSystem());

        // active systems
        engine.addSystem(new CarInputSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new WorldWrapSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new CameraMovementSystem());
        engine.addSystem(new CarMusicSystem(assetManager));
        engine.addSystem(new RenderSystem(batch, viewport));
        engine.addSystem(new HudRenderSystem(batch, hudViewport, font));

        // debug systems
        if (GameConfig.debug) {
            engine.addSystem(new DebugRenderSystem(viewport, renderer));
            engine.addSystem(new DebugCameraSystem(
                    GameConfig.WIDTH / 2, GameConfig.HEIGHT / 2,    // center
                    camera
            ));
            engine.addSystem(new DebugGridRenderSystem(viewport, renderer));
            engine.addSystem(new DebugInputSystem());
        }
        GameManager.INSTANCE.resetResult();

        // mapRenderer = new OrthoCachedTiledMapRenderer(map);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) GameManager.INSTANCE.resetResult();

        engine.update(delta);

        // if (GameManager.INSTANCE.isGameOver()) {
        // Gdx.app.exit();
        // game.setScreen(new MenuScreen(game));
        // }
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        engine.removeAllEntities();
    }
}
