package com.inhavok.fallen.entities.characters;

import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.commands.CommandData;
import com.inhavok.fallen.commands.entity.PhysicsCommand;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.entities.Entity;
import com.inhavok.fallen.utility.GameMath;

public abstract class BipedalEntity extends Entity {
	private final float baseSpeed;
	private boolean walking;
	BipedalEntity(final float x, final float y, final float angle, final float baseSpeed) {
		super(x, y, angle);
		this.baseSpeed = baseSpeed;
	}
	public final void walk(final Vector2 walkVelocity) {
		final CommandData<Vector2> data = new CommandData<Vector2>();
		execute(new PhysicsCommand() {
			@Override
			public void execute(EntityPhysics listener) {
				listener.changeLinearVelocity(walkVelocity);
				data.setData(listener.getLinearVelocity());
			}
		});
		final float greatestComponentLength = GameMath.calGreatestComponentLength(data.getData());
		if (greatestComponentLength > 0) {
			walkEvent(data.getData().angle(), greatestComponentLength);
			walking = true;
		} else if (walking) {
			stopWalkEvent();
			walking = false;
		}
	}
	void walkEvent(final float angle, final float greatestComponentLength) {
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