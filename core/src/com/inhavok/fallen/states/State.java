package com.inhavok.fallen.states;

import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.state.StateComponentCommand;
import com.inhavok.fallen.components.state_components.StateComponent;

import java.util.ArrayList;

public abstract class State {
	@SuppressWarnings("unused")
	private static Application application;
	private final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
	State() {
		components.addAll(addComponents());
	}
	abstract ArrayList<StateComponent> addComponents();
	public static final void initialise(final Application application) {
		State.application = application;
	}
	public void update() {
	}
	public abstract void handleKeyPress(int keycode);
	final <T extends StateComponent> T getComponent(final Class<T> type) {
		for (StateComponent component : components) {
			if (component.getClass() == type || component.getClass().getSuperclass() == type) {
				return type.cast(component);
			}
		}
		return null;
	}
	public final void execute(StateComponentCommand command) {
		for (StateComponent stateComponent : components) {
			stateComponent.execute(command);
		}
	}
}