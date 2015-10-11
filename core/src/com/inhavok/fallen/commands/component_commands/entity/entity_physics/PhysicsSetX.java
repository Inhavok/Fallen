package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public final class PhysicsSetX extends Command<EntityPhysics> {
	private final float x;
	public PhysicsSetX(final float x) {
		super(EntityPhysics.class);
		this.x = x;
	}
	@Override
	public Enum getMessage() {
		return EntityPhysics.Message.SET_X;
	}
	public float getX() {
		return x;
	}
}