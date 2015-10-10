package com.inhavok.fallen.states;

import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.commands.CommandListener;
import com.inhavok.fallen.commands.CommandManager;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesUpdateState;
import com.inhavok.fallen.commands.component_commands.state.state_ui.UIUpdateState;
import com.inhavok.fallen.components.state_components.StateComponent;

import java.util.ArrayList;

public abstract class State implements CommandListener {
	private final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
	State() {
		components.addAll(addComponents());
	}
	abstract ArrayList<StateComponent> addComponents();
	public void activate() {
		CommandManager.add(this);
		for (StateComponent component : components) {
			CommandManager.add(component);
		}
	}
	public final void update() {
		CommandManager.add(new EntitiesUpdateState());
		CommandManager.add(new UIUpdateState());
	}
	public abstract void handleKeyPress(int keycode);
	@Override
	public CommandFilter getType() {
		return CommandFilter.STATE;
	}
	public ArrayList<StateComponent> getComponents() {
		return components;
	}
}