package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

public final class GraphicsSetX extends Command<EntityGraphics> {
	private final float x;
	public GraphicsSetX(final float x) {
		super(EntityGraphics.class, CommandFilter.ENTITY);
		this.x = x;
	}
	@Override
	protected void execute(EntityGraphics listener) {
		listener.setX(x);
	}
}