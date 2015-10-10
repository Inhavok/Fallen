package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

public final class GraphicsSetY extends Command<EntityGraphics> {
	private final float y;
	public GraphicsSetY(final float y) {
		super(EntityGraphics.class, CommandFilter.ENTITY);
		this.y = y;
	}
	@Override
	protected void execute(EntityGraphics listener) {
		listener.setY(y);
	}
}