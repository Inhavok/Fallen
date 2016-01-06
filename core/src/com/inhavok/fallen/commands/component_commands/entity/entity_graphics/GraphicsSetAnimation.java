package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

public class GraphicsSetAnimation extends Command<EntityGraphics> {
	private final Enum layer;
	private final Enum animation;
	public GraphicsSetAnimation(final Enum layer, final Enum animation) {
		super(EntityGraphics.class);
		this.layer = layer;
		this.animation = animation;
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.SET_ANIMATION;
	}
	public Enum getLayer() {
		return layer;
	}
	public Enum getAnimation() {
		return animation;
	}
}