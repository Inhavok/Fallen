package com.inhavok.fallen.commands.state_commands;

import com.inhavok.fallen.states.PlayState;

public class HandleKeyPress extends StateCommand<PlayState> {
	private final int keycode;
	protected HandleKeyPress(final int keycode) {
		super(PlayState.class);
		this.keycode = keycode;
	}
	@Override
	public void execute(PlayState state) {
		state.handleKeyPress(keycode);
	}
}