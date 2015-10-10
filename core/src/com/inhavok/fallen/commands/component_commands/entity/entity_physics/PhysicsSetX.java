package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public final class PhysicsSetX extends Command<EntityPhysics> {
	private final float x;
	public PhysicsSetX(final float x) {
		super(EntityPhysics.class, CommandFilter.ENTITY);
		this.x = x;
	}
	@Override
	protected void execute(EntityPhysics listener) {
		listener.setX(x);
	}
}