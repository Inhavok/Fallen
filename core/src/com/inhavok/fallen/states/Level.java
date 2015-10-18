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

import java.util.ArrayList;

final class Level {
	private static Player player;
	private static int levelHeight;
	private static int tileSize;
	private Level() {
	}
	public static void load(final PlayState playState) {
		final JsonValue level = (new JsonReader()).parse(Gdx.files.internal("levels/TestLevel.json"));
		levelHeight = level.get("height").asInt();
		tileSize = level.get("tileWidth").asInt();
		final JsonValue layers = level.get("layers");
		for (JsonValue layer : layers) {
			if (layer.get("name").asString().equals("StaticGraphics") || layer.get("name").asString().equals("StaticPhysics")) {
				loadTiles(layer, playState);
			} else if (layer.get("name").asString().equals("Interactions")) {
				loadInteractions(layer.get("objects"), playState);
			} else if (layer.get("name").asString().equals("AI")) {
				loadAI(layer.get("objects"), playState);
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
	private static void loadInteractions(final JsonValue interactions, final PlayState playState) {
		for (JsonValue interaction : interactions) {
			if (interaction.get("name").asString().equals("spawnPoint")) {
				final Vector2 spawnPoint = levelToPhysicsCoordinates(interaction.get("x").asInt(), interaction.get("y").asInt());
				player = new Player(spawnPoint.x, spawnPoint.y, 0);
				playState.execute(new EntitiesAdd(player));
			}
		}
	}
	private static void loadAI(final JsonValue patrolPoints, final PlayState playState) {
		int currentEnemy = 1;
		final ArrayList<PatrolPoint> points = new ArrayList<PatrolPoint>();
		for (JsonValue patrolPoint : patrolPoints) {
			if (!loadPatrolPoint(patrolPoint, currentEnemy, points)) {
				loadEnemy(points, playState);
				currentEnemy++;
				loadPatrolPoint(patrolPoint, currentEnemy, points);
			}
		}
		if (points.size() > 0) {
			loadEnemy(points, playState);
		}
	}
	private static boolean loadPatrolPoint(final JsonValue patrolPoint, final int currentEnemy, final ArrayList<PatrolPoint> points) {
		if (patrolPoint.get("name").asString().matches("patrol" + currentEnemy + ".*")) {
			points.add(new PatrolPoint(levelToPhysicsCoordinates(patrolPoint.get("x").asInt(), patrolPoint.get("y").asInt()), Integer.parseInt(patrolPoint.get("properties").get("rotation").asString())));
			return true;
		}
		return false;
	}
	private static void loadEnemy(final ArrayList<PatrolPoint> patrolPoints, final PlayState playState) {
		for (PatrolPoint patrolPoint : patrolPoints) {
			playState.execute(new EntitiesAdd(new Player(patrolPoint.getPoint().x, patrolPoint.getPoint().y, patrolPoint.getRotation())));
		}
		patrolPoints.clear();
	}
	private static Vector2 levelToPhysicsCoordinates(float x, float y) {
		x /= Application.PIXELS_PER_METER;
		y /= -Application.PIXELS_PER_METER;
		y += (levelHeight * tileSize) / Application.PIXELS_PER_METER;
		return new Vector2(x, y);
	}
	public static Player getPlayer() {
		return player;
	}
	private static class PatrolPoint {
		private final Vector2 point;
		private final int angle;
		PatrolPoint(final Vector2 point, final int angle) {
			this.point = point;
			this.angle = angle;
		}
		public Vector2 getPoint() {
			return point;
		}
		public int getRotation() {
			return angle;
		}
	}
}