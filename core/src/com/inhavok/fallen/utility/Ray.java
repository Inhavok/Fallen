package com.inhavok.fallen.utility;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public abstract class Ray {
	private final Vector2 startPoint;
	private final float angle;
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
	public Ray(final Vector2 startPoint, final float angle) {
		this.startPoint = startPoint;
		this.angle = angle;
	}
	public abstract void collision();
	public Vector2 getStartPoint() {
		return startPoint;
	}
	public Vector2 getEndPoint() {
		return new Vector2(startPoint).add((float) (Integer.MAX_VALUE * Math.cos(angle)), (float) (Integer.MAX_VALUE * Math.sin(angle)));
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