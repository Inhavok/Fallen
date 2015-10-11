package com.inhavok.fallen.commands.component_commands.state.state_ui;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateUI;

public final class UIUpdateState extends Command<StateUI> {
	public UIUpdateState() {
		super(StateUI.class);
	}
	@Override
	public Enum getMessage() {
		return StateUI.Message.UPDATE_STATE;
	}
}