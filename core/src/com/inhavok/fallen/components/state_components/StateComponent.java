package com.inhavok.fallen.components.state_components;

import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.commands.CommandListener;

public abstract class StateComponent implements CommandListener {
	@Override
	public CommandFilter getType() {
		return CommandFilter.STATE;
	}
}