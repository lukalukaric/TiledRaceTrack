package com.mygdx.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.assets.AssetManager;

import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.ecs.component.MovementComponent;
import com.mygdx.game.ecs.component.CarComponent;

public class CarMusicSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            CarComponent.class,
            MovementComponent.class
    ).get();

    private final AssetManager assetManager;
    private int state = 0;

    public CarMusicSystem(AssetManager assetManager) {
        super(FAMILY);
        this.assetManager = assetManager;
        state = 0;
        assetManager.get(AssetDescriptors.CAR_LOOP).setLooping(true);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponent movement = Mappers.MOVEMENT.get(entity);
        switch (state) {
            case 0:
                if (movement.speed > 0.02f) {
                    assetManager.get(AssetDescriptors.CAR_START).play();
                    state = 1;
                }
                break;
            case 1:
                if (!assetManager.get(AssetDescriptors.CAR_START).isPlaying()) {
                    assetManager.get(AssetDescriptors.CAR_LOOP).play();
                    state = 2;
                }
                break;
            case 2:
                if (movement.speed < 0.001f) {
                    assetManager.get(AssetDescriptors.CAR_LOOP).stop();
                    state = 3;
                }
                break;
            case 3:
                if (movement.speed > 0.02f) {
                    if (assetManager.get(AssetDescriptors.CAR_STOP).isPlaying())
                        assetManager.get(AssetDescriptors.CAR_STOP).stop();
                    state = 0; //starts
                }
        }
    }
}
