package com.inhavok.fallen.commands.component_commands.entity;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.PlayerController;

public final class PlayerControllerUpdate extends Command<PlayerController> {
	public PlayerControllerUpdate() {
		super(PlayerController.class);
	}
	@Override
	public Enum getMessage() {
		return PlayerController.Message.UPDATE;
	}
}