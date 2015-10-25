package com.inhavok.fallen.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.entities.Enemy;
import com.inhavok.fallen.entities.FloorTile;
import com.inhavok.fallen.entities.IceTile;
import com.inhavok.fallen.entities.Player;
import com.inhavok.fallen.states.PlayState;

import java.util.ArrayList;

public final class Level {
	private static int width;
	private static int height;
	private static int[][] tiles;
	private static int tileSize;
	private static Player player;
	private static final ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private Level() {
	}
	public static void load(final PlayState playState) {
		final JsonValue level = (new JsonReader()).parse(Gdx.files.internal("levels/TestLevel.json"));
		width = level.get("width").asInt();
		height = level.get("height").asInt();
		tiles = new int[width][height];
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
	private static void loadTiles(final JsonValue tileLayer, final PlayState playState) {
		int currentValue = 0;
		for (int i = tileLayer.get("height").asInt() - 1; i >= 0; i--) {
			for (int j = 0; j < tileLayer.get("width").asInt(); j++) {
				if (tileLayer.get("data").get(currentValue).asInt() == 1) {
					playState.execute(new EntitiesAdd(new FloorTile(j, i)));
					tiles[j][i] = 1;
				} else if (tileLayer.get("data").get(currentValue).asInt() == 14) {
					playState.execute(new EntitiesAdd(new IceTile(j, i)));
					tiles[j][i] = 0;
				}
				currentValue++;
			}
		}
	}
	private static void loadInteractions(final JsonValue interactions, final PlayState playState) {
		for (JsonValue interaction : interactions) {
			if (interaction.get("name").asString().equals("spawnPoint")) {
				final Vector2 spawnPoint = levelToPhysicsPosition(interaction.get("x").asInt(), interaction.get("y").asInt());
				player = new Player(spawnPoint.x, spawnPoint.y, 0);
				playState.execute(new EntitiesAdd(player));
			}
		}
	}
	private static void loadAI(final JsonValue points, final PlayState playState) {
		enemies.clear();
		int currentEnemy = 1;
		final ArrayList<PatrolPoint> patrolPoints = new ArrayList<PatrolPoint>();
		for (JsonValue patrolPoint : points) {
			if (!loadPatrolPoint(patrolPoint, currentEnemy, patrolPoints)) {
				loadEnemy(patrolPoints, playState);
				currentEnemy++;
				loadPatrolPoint(patrolPoint, currentEnemy, patrolPoints);
			}
		}
		if (patrolPoints.size() > 0) {
			loadEnemy(patrolPoints, playState);
		}
	}
	private static boolean loadPatrolPoint(final JsonValue patrolPoint, final int currentEnemy, final ArrayList<PatrolPoint> points) {
		if (patrolPoint.get("name").asString().matches("patrol" + currentEnemy + ".*")) {
			points.add(new PatrolPoint(levelToPhysicsPosition(patrolPoint.get("x").asInt(), patrolPoint.get("y").asInt()), Integer.parseInt(patrolPoint.get("properties").get("rotation").asString())));
			return true;
		}
		return false;
	}
	private static void loadEnemy(final ArrayList<PatrolPoint> patrolPoints, final PlayState playState) {
		final Enemy enemy = new Enemy(patrolPoints);
		enemies.add(enemy);
		playState.execute(new EntitiesAdd(enemy));
		patrolPoints.clear();
	}
	private static Vector2 levelToPhysicsPosition(float x, float y) {
		x /= Application.PIXELS_PER_METER;
		y /= -Application.PIXELS_PER_METER;
		y += ((tiles.length - 1) * tileSize) / Application.PIXELS_PER_METER;
		return new Vector2(x, y);
	}
	public static int physicsToTileX(final float x) {
		return Math.round(x / (tileSize / Application.PIXELS_PER_METER));
	}
	public static int physicsToTileY(final float y) {
		return Math.round(y / (tileSize / Application.PIXELS_PER_METER));
	}
	public static Vector2 tileToPhysicsPosition(final int x, final int y) {
		return new Vector2(x * (tileSize / Application.PIXELS_PER_METER), y * (tileSize / Application.PIXELS_PER_METER));
	}
	public static int getWidth() {
		return width;
	}
	public static int getHeight() {
		return height;
	}
	public static int[][] getTiles() {
		return tiles;
	}
	public static Player getPlayer() {
		return player;
	}
	public static ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	public static final class PatrolPoint {
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