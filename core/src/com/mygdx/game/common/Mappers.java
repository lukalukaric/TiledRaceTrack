package com.mygdx.game.common;

import com.badlogic.ashley.core.ComponentMapper;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.DimensionComponent;
import com.mygdx.game.ecs.component.MovementComponent;
import com.mygdx.game.ecs.component.ObstacleComponent;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.TextureComponent;
import com.mygdx.game.ecs.component.ZOrderComponent;


public final class Mappers {

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<MovementComponent> MOVEMENT =
            ComponentMapper.getFor(MovementComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    public static final ComponentMapper<ZOrderComponent> Z_ORDER =
            ComponentMapper.getFor(ZOrderComponent.class);

    public static final ComponentMapper<ObstacleComponent> OBSTACLE =
            ComponentMapper.getFor(ObstacleComponent.class);

    private Mappers() {
    }
}
