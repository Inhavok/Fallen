package com.inhavok.fallen.states;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandListener;
import com.inhavok.fallen.commands.state.UICommand;
import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.components.state_components.StateUI;

import java.util.ArrayList;

public abstract class State implements CommandListener {
	private final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
	State() {
		components.addAll(addComponents());
	}
	abstract ArrayList<StateComponent> addComponents();
	public final void activate() {
		execute(new UICommand() {
			@Override
			public void execute(StateUI listener) {
				listener.show();
			}
		});
	}
	public <T extends StateComponent> void execute(Command<T> command) {
		if (hasComponent(command.getListeningClass())) {
			for (StateComponent component : components) {
				if (command.getListeningClass().isInstance(component)) {
					component.handleCommand(command);
				}
			}
		}
	}
	public void update() {
	}
	@Override
	public void handleCommand(Command command) {
		command.execute(this);
	}
	public void handleKeyPress(final int keycode) {
	}
	public void handleKeyRelease(final int keycode) {
	}
	private <T extends StateComponent> boolean hasComponent(Class<T> componentClass) {
		return getComponent(componentClass) != null;
	}
	private <T extends StateComponent> T getComponent(Class<T> componentClass) {
		for (StateComponent component : components) {
			if (componentClass.isInstance(component)) {
				return componentClass.cast(component);
			}
		}
		return null;
	}
	public final ArrayList<StateComponent> getComponents() {
		return components;
	}
}