package com.inhavok.fallen.commands.entity;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.EntityController;

public abstract class ControllerCommand extends Command<EntityController> {
	public ControllerCommand() {
		super(EntityController.class);
	}
}