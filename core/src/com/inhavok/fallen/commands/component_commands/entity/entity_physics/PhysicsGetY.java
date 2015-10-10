package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public class PhysicsGetY extends Command<EntityPhysics> {
	private float y;
	public PhysicsGetY() {
		super(EntityPhysics.class, CommandFilter.ENTITY);
	}
	@Override
	protected void execute(EntityPhysics listener) {
		y = listener.getY();
	}
	@Override
	protected Float getData() {
		return y;
	}
}