package com.inhavok.fallen.commands.state;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateUI;

public abstract class UICommand extends Command<StateUI> {
	protected UICommand() {
		super(StateUI.class);
	}
}