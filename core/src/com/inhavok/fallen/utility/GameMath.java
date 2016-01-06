package com.inhavok.fallen.utility;

import com.badlogic.gdx.math.Vector2;

public final class GameMath {
	private GameMath() {
	}
	public static boolean closeTo(final float x1, final float y1, final float x2, final float y2, final float tolerance) {
		return Vector2.dst(x1, y1, x2, y2) <= tolerance;
	}
}