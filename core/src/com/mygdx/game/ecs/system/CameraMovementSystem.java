package com.mygdx.game.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import com.mygdx.game.common.Mappers;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.component.CarComponent;
import com.mygdx.game.ecs.component.PositionComponent;

public class CameraMovementSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            CarComponent.class
    ).get();

    // private TiledSystem tiledSystem;

    public CameraMovementSystem() {
        super(FAMILY);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        // tiledSystem = engine.getSystem(TiledSystem.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = Mappers.POSITION.get(entity);

        GameConfig.POSITION_X = position.x;
        GameConfig.POSITION_Y = position.y;
        // MathUtils.lerp(GameConfig.POSITION_Y, position.y, 0.1f); //movement.speed;
    }
}
