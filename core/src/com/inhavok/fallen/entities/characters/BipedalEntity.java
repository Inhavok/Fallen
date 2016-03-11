package com.inhavok.fallen.entities.characters;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsChangeLinearVelocity;
import com.inhavok.fallen.entities.Entity;
import com.inhavok.fallen.utility.GameMath;

public abstract class BipedalEntity extends Entity {
	private final float baseSpeed;
	BipedalEntity(final float x, final float y, final float angle, final float baseSpeed) {
		super(x, y, angle);
		this.baseSpeed = baseSpeed;
	}
	public final void walk(final Vector2 walkVelocity) {
		execute(new PhysicsChangeLinearVelocity(walkVelocity.x, walkVelocity.y));
		final float greatestComponentLength = GameMath.calGreatestComponentLength(walkVelocity);
		if (greatestComponentLength > 0) {
			beginWalkEvent(walkVelocity.angle(), greatestComponentLength);
		} else {
			stopWalkEvent();
		}
	}
	void beginWalkEvent(final float angle, final float greatestComponentLength) {
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