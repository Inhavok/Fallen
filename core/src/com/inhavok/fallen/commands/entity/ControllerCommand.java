package com.inhavok.fallen.commands.entity;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.entity_components.controllers.EntityController;

public abstract class ControllerCommand extends Command<EntityController> {
	public ControllerCommand() {
		super(EntityController.class);
	}
}