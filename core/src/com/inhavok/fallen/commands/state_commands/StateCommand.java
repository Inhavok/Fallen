package com.inhavok.fallen.commands.state_commands;

import com.inhavok.fallen.states.State;

abstract class StateCommand<T extends State> {
	private final Class<T> handler;
	StateCommand(final Class<T> handler) {
		this.handler = handler;
	}
	public abstract void execute(T state);
	public Class<T> getHandler() {
		return handler;
	}
}