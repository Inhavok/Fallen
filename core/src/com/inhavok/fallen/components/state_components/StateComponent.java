package com.inhavok.fallen.components.state_components;

import com.inhavok.fallen.commands.CommandListener;
import com.inhavok.fallen.states.State;

public abstract class StateComponent implements CommandListener {
	private final State state;
	StateComponent(final State state) {
		this.state = state;
	}
	protected State getState() {
		return state;
	}
}