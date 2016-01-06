package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.DataRequest;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public class PhysicsGetLinearVelocity extends DataRequest<EntityPhysics> {
    private final Vector2 velocity = new Vector2();
    public PhysicsGetLinearVelocity() {
        super(EntityPhysics.class);
    }
    @Override
    public <S> S getData(Class<S> dataClass) {
        return dataClass.cast(velocity);
    }
    @Override
    public <S> void setData(S data) {
        velocity.set((Vector2) data);
    }
    @Override
    public Enum getMessage() {
        return EntityPhysics.Message.GET_LINEAR_VELOCITY;
    }
}