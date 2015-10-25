package com.inhavok.fallen.commands.component_commands.entity;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.ai.AIComponent;

public class AIThink extends Command<AIComponent> {
	public AIThink() {
		super(AIComponent.class);
	}
	@Override
	public Enum getMessage() {
		return AIComponent.Message.THINK;
	}
}