package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public class PhysicsGetRotation extends Command<EntityPhysics> {
	private float angle;
	public PhysicsGetRotation() {
		super(EntityPhysics.class, CommandFilter.ENTITY);
	}
	@Override
	protected void execute(EntityPhysics listener) {
		angle = listener.getRotation();
	}
	@Override
	protected Float getData() {
		return angle;
	}
}