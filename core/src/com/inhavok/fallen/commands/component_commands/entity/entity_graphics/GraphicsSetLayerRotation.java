package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

public class GraphicsSetLayerRotation extends Command<EntityGraphics> {
	private final Enum layer;
	private final float angle;
	public GraphicsSetLayerRotation(final Enum layer, final float angle) {
		super(EntityGraphics.class);
		this.layer = layer;
		this.angle = angle;
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.SET_LAYER_ROTATION;
	}
	public Enum getLayer() {
		return layer;
	}
	public float getRotation() {
		return angle;
	}
}