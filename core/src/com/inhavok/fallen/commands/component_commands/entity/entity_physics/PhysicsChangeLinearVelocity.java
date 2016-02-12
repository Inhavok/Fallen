package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public class PhysicsChangeLinearVelocity extends Command<EntityPhysics> {
	private final Vector2 newVelocity = new Vector2();
	public PhysicsChangeLinearVelocity(final float x, final float y) {
		super(EntityPhysics.class);
		newVelocity.set(x, y);
	}
	@Override
	public Enum getMessage() {
		return EntityPhysics.Message.CHANGE_LINEAR_VELOCITY;
	}
	public Vector2 getNewVelocity() {
		return newVelocity;
	}
}