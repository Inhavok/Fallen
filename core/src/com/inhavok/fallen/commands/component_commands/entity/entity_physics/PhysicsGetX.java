package com.inhavok.fallen.commands.component_commands.entity.entity_physics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.entity_components.EntityPhysics;

public class PhysicsGetX extends Command<EntityPhysics> {
	private float x;
	public PhysicsGetX() {
		super(EntityPhysics.class, CommandFilter.ENTITY);
	}
	@Override
	protected void execute(EntityPhysics listener) {
		x = listener.getX();
	}
	@Override
	protected Float getData() {
		return x;
	}
}