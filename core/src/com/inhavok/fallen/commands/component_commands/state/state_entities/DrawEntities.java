package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.commands.component_commands.state.StateComponentCommand;

public class DrawEntities extends StateComponentCommand<StateEntities> {
	private final SpriteBatch spriteBatch;
	public DrawEntities(final SpriteBatch spriteBatch) {
		super(StateEntities.class);
		this.spriteBatch = spriteBatch;
	}
	@Override
	public void execute(StateEntities component) {
		component.drawEntities(spriteBatch);
	}
}