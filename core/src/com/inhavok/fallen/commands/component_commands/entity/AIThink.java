package com.inhavok.fallen.commands.component_commands.entity;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.ai.EntityAI;

public class AIThink extends Command<EntityAI> {
	public AIThink() {
		super(EntityAI.class);
	}
	@Override
	public Enum getMessage() {
		return EntityAI.Message.THINK;
	}
}