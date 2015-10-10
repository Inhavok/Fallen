package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

public final class GraphicsAnimate extends Command<EntityGraphics> {
	private final float delta;
	public GraphicsAnimate(final float delta) {
		super(EntityGraphics.class, CommandFilter.ENTITY);
		this.delta = delta;
	}
	@Override
	protected void execute(EntityGraphics listener) {
		listener.animate(delta);
	}
}