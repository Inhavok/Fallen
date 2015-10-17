package com.inhavok.fallen.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.entities.FloorTile;
import com.inhavok.fallen.entities.IceTile;
import com.inhavok.fallen.entities.Player;

final class Level {
	private static Player player;
	private Level() {
	}
	public static void load(final PlayState playState) {
		final JsonValue level = (new JsonReader()).parse(Gdx.files.internal("levels/TestLevel.json"));
		final JsonValue layers = level.get("layers");
		for (JsonValue layer : layers) {
			if (layer.get("name").asString().equals("StaticGraphics") || layer.get("name").asString().equals("StaticPhysics")) {
				loadTiles(layer, playState);
			} else if (layer.get("name").asString().equals("Interactions")) {
				loadInteractions(layer.get("objects"), playState, level.get("height").asInt(), level.get("tileWidth").asInt());
			}
		}
	}
	private static void loadTiles(final JsonValue tiles, final PlayState playState) {
		int currentValue = 0;
		for (int i = tiles.get("height").asInt(); i > 0; i--) {
			for (int j = 0; j < tiles.get("width").asInt(); j++) {
				if (tiles.get("data").get(currentValue).asInt() == 1) {
					playState.execute(new EntitiesAdd(new FloorTile(j, i)));
				} else if (tiles.get("data").get(currentValue).asInt() == 14) {
					playState.execute(new EntitiesAdd(new IceTile(j, i)));
				}
				currentValue++;
			}
		}
	}
	private static void loadInteractions(final JsonValue interactions, final PlayState playState, final int levelHeight, final int tileSize) {
		for (JsonValue interaction : interactions) {
			if (interaction.get("name").asString().equals("spawnPoint")) {
				final Vector2 spawnPoint = levelToPhysicsCoordinates(interaction.get("x").asInt(), interaction.get("y").asInt(), levelHeight, tileSize);
				player = new Player(spawnPoint.x, spawnPoint.y, 0);
				playState.execute(new EntitiesAdd(player));
			}
		}
	}
	private static Vector2 levelToPhysicsCoordinates(float x, float y, final int levelHeight, final int tileSize) {
		x /= Application.PIXELS_PER_METER;
		y /= -Application.PIXELS_PER_METER;
		y += (levelHeight * tileSize) / Application.PIXELS_PER_METER;
		return new Vector2(x, y);
	}
	public static Player getPlayer() {
		return player;
	}
}