package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateEntities;

public final class EntitiesUpdateState extends Command<StateEntities> {
	public EntitiesUpdateState() {
		super(StateEntities.class);
	}
	@Override
	public Enum getMessage() {
		return StateEntities.Message.UPDATE_STATE;
	}
}