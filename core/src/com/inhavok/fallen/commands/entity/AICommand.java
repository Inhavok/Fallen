package com.inhavok.fallen.commands.entity;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.ai.EntityAI;

public abstract class AICommand extends Command<EntityAI> {
	public AICommand() {
		super(EntityAI.class);
	}
}