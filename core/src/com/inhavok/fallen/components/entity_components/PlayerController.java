package com.inhavok.fallen.components.entity_components;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.entities.Player;

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
		player.faceCursor();
		final Vector2 impulse = new Vector2();
		if (StateUI.getKeysDown().contains(Input.Keys.W)) {
			impulse.add(0, 2);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.S)) {
			impulse.add(0, -2);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.A)) {
			impulse.add(-2, 0);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.D)) {
			impulse.add(2, 0);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.SHIFT_LEFT)) {
			impulse.scl(1.75f);
		}
		if (StateUI.getKeysDown().contains(Input.Keys.CONTROL_LEFT)) {
			impulse.scl(0.25f);
		}
		player.move(impulse);
	}
	public enum Message {
		UPDATE
	}
}