package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.state_components.StateEntities;

public final class UpdateStateEntities extends Command<StateEntities> {
	public UpdateStateEntities() {
		super(StateEntities.class, CommandFilter.STATE);
	}
	@Override
	public void execute(StateEntities listener) {
		listener.updateState();
	}
}