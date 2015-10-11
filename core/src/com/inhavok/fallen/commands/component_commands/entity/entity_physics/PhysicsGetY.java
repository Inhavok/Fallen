package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.DataRequest;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public final class PhysicsGetY extends DataRequest<EntityPhysics> {
	private float y;
	public PhysicsGetY() {
		super(EntityPhysics.class);
	}
	@Override
	public Enum getMessage() {
		return EntityPhysics.Message.GET_Y;
	}
	@Override
	public <S> S getData(Class<S> dataClass) {
		return dataClass.cast(y);
	}
	@Override
	public <S> void setData(S data) {
		y = (Float) data;
	}
}