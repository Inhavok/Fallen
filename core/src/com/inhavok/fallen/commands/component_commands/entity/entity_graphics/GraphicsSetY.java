package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

public final class GraphicsSetY extends Command<EntityGraphics> {
	private final float y;
	public GraphicsSetY(final float y) {
		super(EntityGraphics.class);
		this.y = y;
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.SET_Y;
	}
	public float getY() {
		return y;
	}
}