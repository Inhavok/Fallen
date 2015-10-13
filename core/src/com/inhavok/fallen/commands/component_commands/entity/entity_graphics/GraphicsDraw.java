package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

public final class GraphicsDraw extends Command<EntityGraphics> {
	private final SpriteBatch spriteBatch;
	public GraphicsDraw(final SpriteBatch spriteBatch) {
		super(EntityGraphics.class);
		this.spriteBatch = spriteBatch;
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.DRAW;
	}
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
}