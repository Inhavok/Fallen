package com.inhavok.fallen.components.entity_components;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.entities.characters.Player;

public final class PlayerController extends EntityComponent {
	private final Player player;
	public PlayerController(final Player player) {
		this.player = player;
	}
	@Override
	public void handleCommand(Command command) {
		if (command.getMessage() == Message.UPDATE) {
			update();
		}
	}
	private void update() {
		final float baseSpeed = player.getBaseSpeed();
		final Vector2 walkVelocity = new Vector2();
		if (StateUI.getKeysDown().contains(Input.Keys.W)) {
			walkVelocity.add(0, baseSpeed);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.S)) {
			walkVelocity.sub(0, baseSpeed);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.A)) {
			walkVelocity.sub(baseSpeed, 0);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.D)) {
			walkVelocity.add(baseSpeed, 0);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.SHIFT_LEFT)) {
			walkVelocity.scl(1.75f);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.CONTROL_LEFT)) {
			walkVelocity.scl(0.25f);
		}
		player.walk(walkVelocity);
	}
	public enum Message {
		UPDATE
	}
}