package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

public final class GraphicsSetX extends Command<EntityGraphics> {
	private final float x;
	public GraphicsSetX(final float x) {
		super(EntityGraphics.class);
		this.x = x;
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.SET_X;
	}
	public float getX() {
		return x;
	}
}