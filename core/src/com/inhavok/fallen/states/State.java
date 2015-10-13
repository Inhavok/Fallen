package com.inhavok.fallen.states;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.CommandListener;
import com.inhavok.fallen.commands.state_commands.HandleKeyPress;
import com.inhavok.fallen.components.state_components.StateComponent;

import java.util.ArrayList;

public abstract class State implements CommandListener {
	private final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
	State() {
		components.addAll(addComponents());
	}
	abstract ArrayList<StateComponent> addComponents();
	public <T extends StateComponent> void execute(Command<T> command) {
		if (hasComponent(command.getListeningClass())) {
			for (StateComponent component : components) {
				if (command.getListeningClass().isInstance(component)) {
					component.handleCommand(command);
				}
			}
		}
	}
	public void updateState() {
	}
	@Override
	public void handleCommand(Command command) {
		if (command.getMessage() == Message.HANDLE_KEY_PRESS) {
			handleKeyPress(((HandleKeyPress) command).getKeycode());
		}
	}
	protected abstract void handleKeyPress(int keycode);
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
	public enum Message {
		HANDLE_KEY_PRESS
	}
}