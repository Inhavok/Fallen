package com.inhavok.fallen.utility;

import com.badlogic.gdx.math.Vector2;

public final class GameMath {
	private GameMath() {
	}
	public static float calEffectiveAngle(final float angleInDegrees) {
		return angleInDegrees - (int) Math.floor(angleInDegrees / 360) * 360;
	}
	public static float calRotationDirection(float currentRotation, float desiredRotation) {
		currentRotation = calEffectiveAngle(currentRotation);
		desiredRotation = calEffectiveAngle(desiredRotation);
		int direction = 1;
		if (currentRotation > 180) {
			currentRotation -= 180;
			direction *= -1;
		}
		if (desiredRotation > 180) {
			desiredRotation -= 180;
			direction *= -1;
		}
		return direction * GameMath.calDifferencePolarity(desiredRotation, currentRotation);
	}
	public static boolean closeTo(final float x1, final float y1, final float x2, final float y2, final float tolerance) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) <= tolerance;
	}
	public static boolean closeTo(final float angleInDegrees1, final float angleInDegrees2, final float tolerance) {
		return calSmallestAngleDifference(angleInDegrees1, angleInDegrees2) <= tolerance;
	}
	public static float calSmallestAngleDifference(float angleInDegrees1, float angleInDegrees2) {
		angleInDegrees1 = calEffectiveAngle(angleInDegrees1);
		angleInDegrees2 = calEffectiveAngle(angleInDegrees2);
		return Math.min(Math.abs(angleInDegrees2 - angleInDegrees1), Math.abs(angleInDegrees1 - angleInDegrees2));
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