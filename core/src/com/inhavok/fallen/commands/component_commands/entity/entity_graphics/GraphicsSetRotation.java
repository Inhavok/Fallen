package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

public final class GraphicsSetRotation extends Command<EntityGraphics> {
	private final float angle;
	public GraphicsSetRotation(final float angle) {
		super(EntityGraphics.class);
		this.angle = angle;
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.SET_ROTATION;
	}
	public float getAngle() {
		return angle;
	}
}