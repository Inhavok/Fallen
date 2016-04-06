package com.inhavok.fallen.commands.entity;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.entity_components.graphics.EntityGraphics;

public abstract class GraphicsCommand extends Command<EntityGraphics> {
	public GraphicsCommand() {
		super(EntityGraphics.class);
	}
}