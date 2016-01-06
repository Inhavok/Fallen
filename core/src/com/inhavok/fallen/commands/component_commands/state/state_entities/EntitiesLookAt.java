package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateEntities;

public class EntitiesLookAt extends Command<StateEntities> {
	private final Vector2 point = new Vector2();
	public EntitiesLookAt(final float x, final float y) {
		super(StateEntities.class);
		point.set(x, y);
	}
	@Override
	public Enum getMessage() {
		return StateEntities.Message.LOOK_AT;
	}
	public Vector2 getPoint() {
		return point;
	}
}