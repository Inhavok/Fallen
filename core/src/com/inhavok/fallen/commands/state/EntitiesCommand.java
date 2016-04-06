package com.inhavok.fallen.commands.state;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.state_components.StateEntities;

public abstract class EntitiesCommand extends Command<StateEntities> {
	protected EntitiesCommand() {
		super(StateEntities.class);
	}
}