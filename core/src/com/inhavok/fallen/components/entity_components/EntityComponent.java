package com.inhavok.fallen.components.entity_components;

import com.inhavok.fallen.commands.CommandFilter;
import com.inhavok.fallen.commands.CommandListener;

public abstract class EntityComponent implements CommandListener {
	@Override
	public CommandFilter getType() {
		return CommandFilter.ENTITY;
	}
}