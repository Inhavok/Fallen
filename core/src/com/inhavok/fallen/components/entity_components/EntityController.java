package com.inhavok.fallen.components.entity_components;

import com.inhavok.fallen.commands.Command;

public abstract class EntityController extends EntityComponent {
	@Override
	public final void handleCommand(Command command) {
		command.execute(this);
	}
	public abstract void update();
}