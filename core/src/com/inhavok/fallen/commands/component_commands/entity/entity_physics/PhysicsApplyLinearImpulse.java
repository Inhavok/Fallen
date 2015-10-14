package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public class PhysicsApplyLinearImpulse extends Command<EntityPhysics> {
	private final Vector2 impulse = new Vector2();
	public PhysicsApplyLinearImpulse(final float x, final float y) {
		super(EntityPhysics.class);
		impulse.set(x, y);
	}
	@Override
	public Enum getMessage() {
		return EntityPhysics.Message.APPLY_LINEAR_IMPULSE;
	}
	public Vector2 getImpulse() {
		return impulse;
	}
}