package com.inhavok.fallen.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.inhavok.fallen.commands.entity.ControllerCommand;
import com.inhavok.fallen.components.entity_components.EntityController;
import com.inhavok.fallen.components.state_components.PlayStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.entities.characters.Player;
import com.inhavok.fallen.utility.Level;

public final class PlayState extends State {
	public PlayState() {
		Level.load(this);
	}
	@Override
	ArrayList<StateComponent> addComponents() {
		final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
		components.add(new PlayStateUI(this));
		components.add(new StateEntities(this));
		return components;
	}
	@Override
	public void update() {
		final Player player = Level.getPlayer();
		player.execute(new ControllerCommand() {
			@Override
			public void execute(EntityController listener) {
				listener.update();
			}
		});
		StateEntities.lookAt(player.getX(), player.getY());
	}
	@Override
	public void handleKeyPress(final int keycode) {
		if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
		}
	}
}