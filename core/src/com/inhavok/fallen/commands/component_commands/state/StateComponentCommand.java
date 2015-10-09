package com.inhavok.fallen.commands.component_commands.state;

import com.inhavok.fallen.components.state_components.StateComponent;

public abstract class StateComponentCommand<T extends StateComponent> {
	private final Class<T> handler;
	protected StateComponentCommand(final Class<T> handler) {
		this.handler = handler;
	}
	public abstract void execute(T component);
	public Class<T> getHandler() {
		return handler;
	}
}