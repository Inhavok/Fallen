package com.inhavok.fallen.commands.state_commands;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.states.State;

public final class HandleKeyPress extends Command<State> {
	private final int keycode;
	public HandleKeyPress(final int keycode) {
		super(State.class);
		this.keycode = keycode;
	}
	@Override
	public Enum getMessage() {
		return State.Message.HANDLE_KEY_PRESS;
	}
	public int getKeycode() {
		return keycode;
	}
}