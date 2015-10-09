package com.inhavok.fallen.commands.component_commands.state.state_ui;

import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.commands.component_commands.state.StateComponentCommand;

public final class UpdateState extends StateComponentCommand<StateUI> {
	public UpdateState() {
		super(StateUI.class);
	}
	@Override
	public void execute(final StateUI component) {
		component.updateState();
	}
}