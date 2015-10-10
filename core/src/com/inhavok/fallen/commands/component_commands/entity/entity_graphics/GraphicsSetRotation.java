package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

public final class GraphicsSetRotation extends Command<EntityGraphics> {
	private final float angle;
	public GraphicsSetRotation(final float angle) {
		super(EntityGraphics.class, CommandFilter.ENTITY);
		this.angle = angle;
	}
	@Override
	protected void execute(EntityGraphics listener) {
		listener.setRotation(angle);
	}
}