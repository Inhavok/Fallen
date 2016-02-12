package com.inhavok.fallen.entities.characters;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsChangeLinearVelocity;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetLinearVelocity;
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
		final Vector2 velocity = requestData(new PhysicsGetLinearVelocity(), Vector2.class);
		final float greatestComponentLength = GameMath.calGreatestComponentLength(velocity);
		if (greatestComponentLength > 0) {
			walkEvent(velocity.angle(), greatestComponentLength);
		} else {
			stopEvent();
		}
	}
	void walkEvent(final float angle, final float greatestComponentLength) {
	}
	//TODO ensure stopEvent is called when the entity stops walking
	void stopEvent() {
	}
	protected final float calculateFrameDuration(final float greatestComponentLength) {
		return 0.45f - (0.045f * greatestComponentLength);
	}
	public final float getBaseSpeed() {
		return baseSpeed;
	}
}