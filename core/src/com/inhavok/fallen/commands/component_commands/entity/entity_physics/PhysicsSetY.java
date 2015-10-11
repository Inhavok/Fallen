package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public final class PhysicsSetY extends Command<EntityPhysics> {
	private final float y;
	public PhysicsSetY(final float y) {
		super(EntityPhysics.class);
		this.y = y;
	}
	@Override
	public Enum getMessage() {
		return EntityPhysics.Message.SET_Y;
	}
	public float getY() {
		return y;
	}
}