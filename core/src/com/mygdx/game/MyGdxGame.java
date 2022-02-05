package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.ecs.system.debug.support.ViewportUtils;
import com.mygdx.game.screen.GameScreen;

public class MyGdxGame extends Game {

	private AssetManager assetManager;
	private SpriteBatch batch;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		ViewportUtils.DEFAULT_CELL_SIZE = 32;

		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.getLogger().setLevel(Logger.DEBUG);

		batch = new SpriteBatch();
		assetManager.load(AssetDescriptors.FONT32);
		assetManager.load(AssetDescriptors.GAME_PLAY);
		assetManager.load(AssetDescriptors.PICK_SOUND);
		assetManager.load(AssetDescriptors.OBSTACLE_SOUND);
		assetManager.load(AssetDescriptors.CAR_START);
		assetManager.load(AssetDescriptors.CAR_STOP);
		assetManager.load(AssetDescriptors.CAR_LOOP);

		// assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		// assetManager.load("level1.tmx", TiledMap.class);
		assetManager.load(AssetDescriptors.TILES);
		assetManager.finishLoading();
		setScreen(new GameScreen(this));
	}


	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
}
