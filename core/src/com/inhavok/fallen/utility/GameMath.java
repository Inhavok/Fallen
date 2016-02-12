package com.inhavok.fallen.utility;

import com.badlogic.gdx.math.Vector2;

public final class GameMath {
	private GameMath() {
	}
	public static boolean closeTo(final float x1, final float y1, final float x2, final float y2, final float tolerance) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) <= tolerance;
	}
	public static float calDifferencePolarity(final float a, final float b) {
		if (a - b == 0) {
			return 0;
		}
		return (a - b) / Math.abs(a - b);
	}
	public static float calGreatestComponentLength(final Vector2 vector) {
		return Math.max(Math.abs(vector.x), Math.abs(vector.y));
	}
}