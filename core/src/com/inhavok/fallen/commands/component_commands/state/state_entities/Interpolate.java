package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.state_components.StateEntities;

public final class Interpolate extends Command<StateEntities> {
	private final float alpha;
	public Interpolate(final float alpha) {
		super(StateEntities.class, CommandFilter.STATE);
		this.alpha = alpha;
	}
	@Override
	public void execute(StateEntities listener) {
		listener.interpolate(alpha);
	}
}