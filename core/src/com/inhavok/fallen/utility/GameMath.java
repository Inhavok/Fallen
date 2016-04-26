package com.inhavok.fallen.utility;
import com.badlogic.gdx.math.Vector2;

public final class GameMath {
	private GameMath() {
	}
	public static float calEffectiveAngle(final float angle) {
		return angle - (int) Math.floor(angle / 360) * 360;
	}
	public static float calRotationDirection(float currentRotation, float desiredRotation) {
		currentRotation = calEffectiveAngle(currentRotation);
		desiredRotation = calEffectiveAngle(desiredRotation);
		int direction = 1;
		if (currentRotation >= 180) {
			currentRotation -= 180;
			direction *= -1;
		}
		if (desiredRotation >= 180) {
			desiredRotation -= 180;
			direction *= -1;
		}
		return direction * GameMath.calDifferencePolarity(desiredRotation, currentRotation);
	}
	public static boolean closeTo(final float x1, final float y1, final float x2, final float y2, final float tolerance) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) <= tolerance;
	}
	public static boolean closeTo(final float angle1, final float angle2, final float tolerance) {
		return calSmallestAngleDifference(angle1, angle2) <= tolerance;
	}
	public static float calSmallestAngleDifference(float angle1, float angle2) {
		angle1 = calEffectiveAngle(angle1);
		angle2 = calEffectiveAngle(angle2);
		return Math.min(Math.abs(angle2 - angle1), Math.abs(angle1 - angle2));
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