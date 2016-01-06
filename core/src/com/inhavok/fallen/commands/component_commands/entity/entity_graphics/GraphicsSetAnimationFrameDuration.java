package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

public class GraphicsSetAnimationFrameDuration extends Command<EntityGraphics> {
	private final Enum layer;
	private final Enum animation;
	private final float frameDuration;
	public GraphicsSetAnimationFrameDuration(final Enum layer, final Enum animation, final float frameDuration) {
		super(EntityGraphics.class);
		this.layer = layer;
		this.animation = animation;
		this.frameDuration = frameDuration;
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.SET_ANIMATION_FRAME_DURATION;
	}
	public Enum getLayer() {
		return layer;
	}
	public Enum getAnimation() {
		return animation;
	}
	public float getFrameDuration() {
		return frameDuration;
	}
}