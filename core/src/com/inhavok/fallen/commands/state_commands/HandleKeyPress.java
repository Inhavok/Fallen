package com.inhavok.fallen.commands.state_commands;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.states.State;

public final class HandleKeyPress extends Command<State> {
	private final int keycode;
	public HandleKeyPress(final int keycode) {
		super(State.class, CommandFilter.STATE);
		this.keycode = keycode;
	}
	@Override
	public void execute(final State listener) {
		listener.handleKeyPress(keycode);
	}
}