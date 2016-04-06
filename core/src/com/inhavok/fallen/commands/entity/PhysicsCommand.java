package com.inhavok.fallen.commands.entity;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.entity_components.EntityPhysics;

public abstract class PhysicsCommand extends Command<EntityPhysics> {
	public PhysicsCommand() {
		super(EntityPhysics.class);
	}
}