package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.commands.component_commands.state.StateComponentCommand;

public class Interpolate extends StateComponentCommand<StateEntities> {
	private final float alpha;
	public Interpolate(final float alpha) {
		super(StateEntities.class);
		this.alpha = alpha;
	}
	@Override
	public void execute(StateEntities component) {
		component.interpolate(alpha);
	}
}