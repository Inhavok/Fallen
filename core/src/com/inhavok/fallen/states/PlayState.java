package com.inhavok.fallen.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetAnimation;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.components.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.components.state_components.PlayStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.entities.Player;

public final class PlayState extends State {
	private final Player player;
	public PlayState() {
		player = new Player(0, 0, 0);
		execute(new EntitiesAdd(player));
	}
	@Override
	ArrayList<StateComponent> addComponents() {
		final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
		components.add(new PlayStateUI(this));
		components.add(new StateEntities(this));
		return components;
	}
	@Override
	public void updateState() {
		if (StateUI.getKeysDown().contains(Keys.W)) {
			player.execute(new GraphicsSetAnimation(PlayerGraphics.Layers.LEGS, PlayerLegsLayer.Animation.WALKING));
		} else {
			player.execute(new GraphicsSetAnimation(PlayerGraphics.Layers.LEGS, PlayerLegsLayer.Animation.IDLE));
		}
	}
	@Override
	public void handleKeyPress(final int keycode) {
		if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
		} else if (StateUI.getKeysDown().contains(Keys.F) && StateUI.getKeysDown().contains(Keys.SHIFT_LEFT)) {
			Application.toggleFullscreen();
		}
	}
}