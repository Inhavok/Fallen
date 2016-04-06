package com.inhavok.fallen.entities.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.utility.Data;
import com.inhavok.fallen.commands.entity.GraphicsCommand;
import com.inhavok.fallen.entity_components.EntityComponent;
import com.inhavok.fallen.entity_components.controllers.PlayerController;
import com.inhavok.fallen.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.entity_components.EntityPhysics;
import com.inhavok.fallen.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.utility.GameMath;

import java.util.ArrayList;

public final class Player extends BipedalEntity {
	private float desiredLegsRotation;
	public Player(float x, float y, float angle) {
		super(x, y, angle, 4);
	}
	@Override
	protected ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		final EntityGraphics graphics = new PlayerGraphics();
		final EntityPhysics physics = new EntityPhysics(graphics.getWidth() - 0.3f, graphics.getHeight() - 0.3f, BodyDef.BodyType.DynamicBody, "");
		components.add(graphics);
		components.add(physics);
		components.add(new PlayerController(this));
		return components;
	}
	@Override
	public void update() {
		faceCursor();
		rotateLegs();
	}
	private void faceCursor() {
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				listener.setLayerRotation(PlayerGraphics.Layer.TORSO, MathUtils.atan2((Gdx.graphics.getHeight() - Gdx.input.getY() - Gdx.graphics.getHeight() / 2) / (float) Application.PIXELS_PER_METER, (Gdx.input.getX() - Gdx.graphics.getWidth() / 2) / (float) Application.PIXELS_PER_METER) * MathUtils.radiansToDegrees - 90);
			}
		});
	}
	private void rotateLegs() {
		final Data<Float> data = new Data<Float>();
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				data.a = listener.getLayerRotation(PlayerGraphics.Layer.LEGS);
			}
		});
		final float currentLegsRotation = data.a;
		if (Math.abs(desiredLegsRotation - currentLegsRotation) > 10) {
			execute(new GraphicsCommand() {
				@Override
				public void execute(EntityGraphics listener) {
					listener.setLayerRotation(PlayerGraphics.Layer.LEGS, currentLegsRotation + 720 * Application.SECONDS_PER_STEP * GameMath.calRotationDirection(currentLegsRotation, desiredLegsRotation));
				}
			});
		}
	}
	@Override
	void walkEvent(final float angle, final float greatestComponentLength) {
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				listener.setAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING);
				listener.setAnimationFrameDuration(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING, calculateFrameDuration(greatestComponentLength));
			}
		});
		desiredLegsRotation = angle - 90;
	}
	@Override
	void stopWalkEvent() {
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				listener.setAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.IDLE);
			}
		});
	}
}