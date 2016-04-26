package com.inhavok.fallen.utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public abstract class Ray {
	private final Vector2 startPoint;
	private final float angleInRadians;
	private final RayCastCallback callback = new RayCastCallback() {
		@Override
		public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
			if (startPoint.dst(point) < startPoint.dst(actualEndPoint)) {
				actualEndPoint.set(point);
				hitFixture = fixture;
			}
			return 1;
		}
	};
	private final Vector2 actualEndPoint = new Vector2(Integer.MAX_VALUE, Integer.MAX_VALUE);
	private Fixture hitFixture;
	public Ray(final Vector2 startPoint, final float angleInRadians) {
		this.startPoint = startPoint;
		this.angleInRadians = angleInRadians;
	}
	public abstract void collision();
	public Vector2 getStartPoint() {
		return startPoint;
	}
	public Vector2 getEndPoint() {
		return new Vector2(startPoint).add((float) (Integer.MAX_VALUE * Math.cos(angleInRadians)), (float) (Integer.MAX_VALUE * Math.sin(angleInRadians)));
	}
	public RayCastCallback getCallback() {
		return callback;
	}
	public Vector2 getActualEndPoint() {
		return actualEndPoint;
	}
	public Fixture getHitFixture() {
		return hitFixture;
	}
}