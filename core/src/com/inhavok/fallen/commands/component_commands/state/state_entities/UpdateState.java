package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.commands.component_commands.state.StateComponentCommand;

public class UpdateState extends StateComponentCommand<StateEntities> {
	public UpdateState() {
		super(StateEntities.class);
	}
	@Override
	public void execute(StateEntities component) {
		component.updateState();
	}
}