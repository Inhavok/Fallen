package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.DataRequest;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public final class PhysicsGetX extends DataRequest<EntityPhysics> {
	private float x;
	public PhysicsGetX() {
		super(EntityPhysics.class);
	}
	@Override
	public Enum getMessage() {
		return EntityPhysics.Message.GET_X;
	}
	@Override
	public <S> S getData(Class<S> dataClass) {
		return dataClass.cast(x);
	}
	@Override
	public <S> void setData(S data) {
		x = (Float) data;
	}
}