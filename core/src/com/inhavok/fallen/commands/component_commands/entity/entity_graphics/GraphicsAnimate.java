package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

public final class GraphicsAnimate extends Command<EntityGraphics> {
	private final float delta;
	public GraphicsAnimate(final float delta) {
		super(EntityGraphics.class);
		this.delta = delta;
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.ANIMATE;
	}
	public float getDelta() {
		return delta;
	}
}