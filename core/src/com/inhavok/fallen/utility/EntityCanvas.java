package com.inhavok.fallen.utility;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.gl;

public final class EntityCanvas {
	private static ShapeRenderer shapeRenderer = new ShapeRenderer();
	private static final ArrayList<Pair<Vector2, Vector2>> vectors = new ArrayList<Pair<Vector2, Vector2>>();
	private EntityCanvas() {
	}
	public static void initialise() {
		shapeRenderer = new ShapeRenderer();
	}
	public static void draw(final Matrix4 projection) {
		shapeRenderer.setProjectionMatrix(projection);
		gl.glEnable(GL20.GL_BLEND);
		gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setColor(1, 1, 1, 0.1f);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		for (Pair<Vector2, Vector2> vector : vectors) {
			shapeRenderer.line(vector.getKey(), vector.getValue());
		}
		shapeRenderer.end();
		gl.glDisable(GL20.GL_BLEND);
		vectors.clear();
	}
	public static void queueVector(final float x1, final float y1, final float x2, final float y2) {
		vectors.add(new Pair<Vector2, Vector2>(new Vector2(x1, y1), new Vector2(x2, y2)));
	}
}