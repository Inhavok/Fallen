package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public final class PhysicsSetRotation extends Command<EntityPhysics> {
	private final float angle;
	public PhysicsSetRotation(final float angle) {
		super(EntityPhysics.class);
		this.angle = angle;
	}
	@Override
	public Enum getMessage() {
		return EntityPhysics.Message.SET_ROTATION;
	}
	public float getAngle() {
		return angle;
	}
}