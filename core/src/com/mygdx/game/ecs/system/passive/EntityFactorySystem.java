package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.DimensionComponent;
import com.mygdx.game.ecs.component.MovementComponent;
import com.mygdx.game.ecs.component.CarComponent;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.TextureComponent;
import com.mygdx.game.ecs.component.WorldWrapComponent;
import com.mygdx.game.ecs.component.ZOrderComponent;

public class EntityFactorySystem extends EntitySystem {

    private static final int MOWER_Z_ORDER = 1;

    private final AssetManager assetManager;

    private PooledEngine engine;
    private TextureAtlas gamePlayAtlas;

    public EntityFactorySystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false);   // passive
        init();
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine) engine;
    }

    private void init() {
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    public void createMower() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = (GameConfig.W_WIDTH - GameConfig.MOWER_HEIGHT) / 2;
        position.r = 90;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.MOWER_WIDTH;
        dimension.height = GameConfig.MOWER_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        MovementComponent movement = engine.createComponent(MovementComponent.class);

        CarComponent carComponent = engine.createComponent(CarComponent.class);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.MOWER);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = MOWER_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        entity.add(carComponent);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(zOrder);

        engine.addEntity(entity);
    }
}
