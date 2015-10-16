package com.inhavok.fallen.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesLookAt;
import com.inhavok.fallen.components.entity_components.graphics.TileGraphics;
import com.inhavok.fallen.components.state_components.PlayStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.entities.Player;
import com.inhavok.fallen.entities.Tile;

public final class PlayState extends State {
	private final Player player;
	public PlayState() {
		final JsonValue testLevel = (new JsonReader()).parse(Gdx.files.internal("levels/TestLevel.json")).get("layers");
		final JsonValue baseLayer = testLevel.get(1);
		int currentValue = 0;
		for (int i = baseLayer.get("height").asInt(); i > 0; i--) {
			for (int j = 0; j < baseLayer.get("width").asInt(); j++) {
				if (baseLayer.get("data").get(currentValue).asInt() == 14) {
					execute(new EntitiesAdd(new Tile(j, i)));
				}
				currentValue++;
			}
		}
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
		player.faceCursor();
		if (StateUI.getKeysDown().contains(Keys.W)) {
			player.walk(Player.Direction.UP);
		}
		if (StateUI.getKeysDown().contains(Keys.S)) {
			player.walk(Player.Direction.DOWN);
		}
		if (StateUI.getKeysDown().contains(Keys.A)) {
			player.walk(Player.Direction.LEFT);
		}
		if (StateUI.getKeysDown().contains(Keys.D)) {
			player.walk(Player.Direction.RIGHT);
		}
		execute(new EntitiesLookAt(player.getX(), player.getY()));
	}
	@Override
	public void handleKeyPress(final int keycode) {
		if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
		} else if (StateUI.getKeysDown().contains(Keys.F) && StateUI.getKeysDown().contains(Keys.SHIFT_LEFT)) {
			Application.toggleFullscreen();
		}
	}
	@Override
	protected void handleKeyRelease(int keycode) {
		if (!(StateUI.getKeysDown().contains(Keys.W) && StateUI.getKeysDown().contains(Keys.S) && StateUI.getKeysDown().contains(Keys.A) && StateUI.getKeysDown().contains(Keys.D))) {
			player.stopWalking();
		}
	}
}