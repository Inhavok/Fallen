package com.inhavok.fallen.commands.component_commands.state.state_ui;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.components.state_components.StateUI;

public final class UpdateStateUI extends Command<StateUI> {
	public UpdateStateUI() {
		super(StateUI.class, CommandFilter.STATE);
	}
	@Override
	public void execute(final StateUI listener) {
		listener.updateState();
	}
}