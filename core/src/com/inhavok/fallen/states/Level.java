package com.inhavok.fallen.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.entities.FloorTile;
import com.inhavok.fallen.entities.IceTile;
import com.inhavok.fallen.entities.Player;

public final class Level {
	private static Player player;
	private Level() {
	}
	public static void load(final PlayState playState) {
		final JsonValue testLevel = (new JsonReader()).parse(Gdx.files.internal("levels/TestLevel.json")).get("layers");
		loadTiles(testLevel.get(0), playState);
		loadTiles(testLevel.get(1), playState);
		player = new Player(testLevel.get(2).get("objects").get(0).get("x").asInt() / Application.PIXELS_PER_METER, 20 - testLevel.get(2).get("objects").get(0).get("y").asInt() / Application.PIXELS_PER_METER, 0);
		playState.execute(new EntitiesAdd(player));
	}
	private static void loadTiles(final JsonValue tileLayer, final PlayState playState) {
		int currentValue = 0;
		for (int i = tileLayer.get("height").asInt(); i > 0; i--) {
			for (int j = 0; j < tileLayer.get("width").asInt(); j++) {
				if (tileLayer.get("data").get(currentValue).asInt() == 1) {
					playState.execute(new EntitiesAdd(new FloorTile(j, i)));
				} else if (tileLayer.get("data").get(currentValue).asInt() == 14) {
					playState.execute(new EntitiesAdd(new IceTile(j, i)));
				}
				currentValue++;
			}
		}
	}
	public static Player getPlayer() {
		return player;
	}
}