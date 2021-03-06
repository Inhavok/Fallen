package com.inhavok.fallen.entities.characters;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.entity.PhysicsCommand;
import com.inhavok.fallen.entity_components.EntityPhysics;
import com.inhavok.fallen.entities.Entity;
import com.inhavok.fallen.utility.GameMath;

public abstract class BipedalEntity extends Entity {
	private final float baseSpeed;
	private boolean walking;
	BipedalEntity(final float x, final float y, final float rotation, final float baseSpeed) {
		super(x, y, rotation);
		this.baseSpeed = baseSpeed;
	}
	public final void walk(final Vector2 walkVelocity) {
		execute(new PhysicsCommand() {
			@Override
			public void execute(EntityPhysics listener) {
				listener.changeLinearVelocity(walkVelocity);
			}
		});
		final float greatestComponentLength = GameMath.calGreatestComponentLength(walkVelocity);
		if (greatestComponentLength > 0) {
			walkEvent(walkVelocity.angle(), greatestComponentLength);
			walking = true;
		} else if (walking) {
			stopWalkEvent();
			walking = false;
		}
	}
	void walkEvent(final float angleInDegrees, final float greatestComponentLength) {
	}
	void stopWalkEvent() {
	}
	final float calculateFrameDuration(final float greatestComponentLength) {
		return 0.45f - (0.045f * greatestComponentLength);
	}
	public final float getBaseSpeed() {
		return baseSpeed;
	}
}