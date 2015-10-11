package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.DataRequest;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public final class PhysicsGetRotation extends DataRequest<EntityPhysics> {
	private float angle;
	public PhysicsGetRotation() {
		super(EntityPhysics.class);
	}
	@Override
	public Enum getMessage() {
		return EntityPhysics.Message.GET_ROTATION;
	}
	@Override
	public <S> S getData(Class<S> dataClass) {
		return dataClass.cast(angle);
	}
	@Override
	public <S> void setData(S data) {
		angle = (Float) data;
	}
}