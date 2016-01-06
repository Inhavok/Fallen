package com.inhavok.fallen.commands.component_commands.state.state_entities;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.entities.Entity;

public class EntitiesAdd extends Command<StateEntities> {
	private final Entity entity;
	public EntitiesAdd(final Entity entity) {
		super(StateEntities.class);
		this.entity = entity;
	}
	@Override
	public Enum getMessage() {
		return StateEntities.Message.ADD;
	}
	public Entity getEntity() {
		return entity;
	}
}