package com.inhavok.fallen.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.inhavok.fallen.commands.component_commands.entity.PlayerControllerUpdate;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesLookAt;
import com.inhavok.fallen.components.state_components.PlayStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.entities.Bullet;
import com.inhavok.fallen.utility.Level;

public final class PlayState extends State {
	public PlayState() {
		Level.load(this);
		execute(new EntitiesAdd(new Bullet(0, 0, 0)));
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
		Level.getPlayer().execute(new PlayerControllerUpdate());
		execute(new EntitiesLookAt(Level.getPlayer().getX(), Level.getPlayer().getY()));
	}
	@Override
	public void handleKeyPress(final int keycode) {
		if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
		}
	}
}