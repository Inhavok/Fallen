package com.inhavok.fallen.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesLookAt;
import com.inhavok.fallen.components.state_components.PlayStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.entities.IceTile;
import com.inhavok.fallen.entities.Player;
import com.inhavok.fallen.entities.FloorTile;

public final class PlayState extends State {
	private final Player player;
	public PlayState() {
		final JsonValue testLevel = (new JsonReader()).parse(Gdx.files.internal("levels/TestLevel.json")).get("layers");
		generateMapLayer(testLevel.get(0));
		generateMapLayer(testLevel.get(1));
		player = new Player(testLevel.get(2).get("objects").get(0).get("x").asInt() / Application.PIXELS_PER_METER, 20 - testLevel.get(2).get("objects").get(0).get("y").asInt() / Application.PIXELS_PER_METER, 0);
		execute(new EntitiesAdd(player));
	}
	private void generateMapLayer(final JsonValue mapLayer) {
		int currentValue = 0;
		for (int i = mapLayer.get("height").asInt(); i > 0; i--) {
			for (int j = 0; j < mapLayer.get("width").asInt(); j++) {
				if (mapLayer.get("data").get(currentValue).asInt() == 1) {
					execute(new EntitiesAdd(new FloorTile(j, i)));
				} else if (mapLayer.get("data").get(currentValue).asInt() == 14) {
					execute(new EntitiesAdd(new IceTile(j, i)));
				}
				currentValue++;
			}
		}
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
		float playerImpulse = 2;
		if (StateUI.getKeysDown().contains(Keys.SHIFT_LEFT)) {
			playerImpulse += 1.5f;
		}
		if (StateUI.getKeysDown().contains(Keys.CONTROL_LEFT)) {
			playerImpulse -= 1.5f;
		}
		if (StateUI.getKeysDown().contains(Keys.W)) {
			player.move(Player.Direction.UP, playerImpulse);
		}
		if (StateUI.getKeysDown().contains(Keys.S)) {
			player.move(Player.Direction.DOWN, playerImpulse);
		}
		if (StateUI.getKeysDown().contains(Keys.A)) {
			player.move(Player.Direction.LEFT, playerImpulse);
		}
		if (StateUI.getKeysDown().contains(Keys.D)) {
			player.move(Player.Direction.RIGHT, playerImpulse);
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