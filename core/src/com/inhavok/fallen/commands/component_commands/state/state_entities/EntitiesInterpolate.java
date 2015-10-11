package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateEntities;

public final class EntitiesInterpolate extends Command<StateEntities> {
	private final float alpha;
	public EntitiesInterpolate(final float alpha) {
		super(StateEntities.class);
		this.alpha = alpha;
	}
	@Override
	public Enum getMessage() {
		return StateEntities.Message.INTERPOLATE;
	}
	public float getAlpha() {
		return alpha;
	}
}