package com.inhavok.fallen.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inhavok.fallen.Application;

public final class EntityCanvas {
	private static SpriteBatch spriteBatch;
	private static Pixmap pixmap;
	private static Texture texture;
	private static boolean clearOnDraw;
	private EntityCanvas() {
	}
	public static void initialise() {
		spriteBatch = new SpriteBatch();
	}
	public static void draw() {
		spriteBatch.begin();
		//spriteBatch.draw(texture, 0, 0);
		spriteBatch.end();
		clearOnDraw = true;
	}
	public static void drawVector(final int x1, final int y1, final int x2, final int y2) {
		if (pixmap == null) {
			pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
		}
		pixmap.setColor(1, 1, 1, 1);
		pixmap.drawLine(x1 * Application.PIXELS_PER_METER, flipY(y1 * Application.PIXELS_PER_METER), x2 * Application.PIXELS_PER_METER, flipY(y2 * Application.PIXELS_PER_METER));
	}
	private static int flipY(final int y) {
		return pixmap.getHeight() - y;
	}
}