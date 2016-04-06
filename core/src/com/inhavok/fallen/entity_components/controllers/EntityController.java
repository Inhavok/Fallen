package com.inhavok.fallen.entity_components.controllers;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.entity_components.EntityComponent;

public abstract class EntityController extends EntityComponent {
	@Override
	public final void handleCommand(Command command) {
		command.execute(this);
	}
	public abstract void update();
}