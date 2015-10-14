package com.inhavok.fallen.commands.state_commands;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.states.State;

public final class HandleKeyRelease extends Command<State> {
	private final int keycode;
	public HandleKeyRelease(final int keycode) {
		super(State.class);
		this.keycode = keycode;
	}
	@Override
	public Enum getMessage() {
		return State.Message.HANDLE_KEY_RELEASE;
	}
	public int getKeycode() {
		return keycode;
	}
}