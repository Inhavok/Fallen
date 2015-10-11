package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateEntities;

public final class EntitiesDraw extends Command<StateEntities> {
	private final SpriteBatch spriteBatch;
	public EntitiesDraw(final SpriteBatch spriteBatch) {
		super(StateEntities.class);
		this.spriteBatch = spriteBatch;
	}
	@Override
	public Enum getMessage() {
		return StateEntities.Message.DRAW;
	}
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
}