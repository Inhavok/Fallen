package com.inhavok.fallen.components.state_components;

import com.inhavok.fallen.commands.component_commands.state.StateComponentCommand;

public abstract class StateComponent {
	public void execute(StateComponentCommand command) {
		if (command.getHandler() == getClass()) {
			command.execute(this);
		}
	}
}