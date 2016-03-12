package com.inhavok.fallen.commands.state;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.states.State;

public abstract class StateCommand extends Command<State> {
	protected StateCommand() {
		super(State.class);
	}
}