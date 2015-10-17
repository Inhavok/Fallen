package com.inhavok.fallen.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetAnimation;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetLayerRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsApplyLinearImpulse;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;

import java.util.ArrayList;

public final class Player extends Entity {
	public Player(float x, float y, float angle) {
		super(x, y, angle);
	}
	@Override
	ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		final EntityGraphics graphics = new PlayerGraphics();
		final EntityPhysics physics = new EntityPhysics(graphics.getWidth() - 0.2f, graphics.getHeight() - 0.2f, BodyDef.BodyType.DynamicBody, 50, 0);
		components.add(graphics);
		components.add(physics);
		return components;
	}
	public void faceCursor() {
		execute(new GraphicsSetLayerRotation(PlayerGraphics.Layer.TORSO, MathUtils.atan2((Gdx.graphics.getHeight() - Gdx.input.getY() - Gdx.graphics.getHeight() / 2) / (float) Application.PIXELS_PER_METER, (Gdx.input.getX() - Gdx.graphics.getWidth() / 2) / (float) Application.PIXELS_PER_METER) * MathUtils.radiansToDegrees - 90));
		execute(new GraphicsSetLayerRotation(PlayerGraphics.Layer.LEGS, MathUtils.atan2((Gdx.graphics.getHeight() - Gdx.input.getY() - Gdx.graphics.getHeight() / 2) / (float) Application.PIXELS_PER_METER, (Gdx.input.getX() - Gdx.graphics.getWidth() / 2) / (float) Application.PIXELS_PER_METER) * MathUtils.radiansToDegrees - 90));
	}
	public void move(final Direction direction, final float impulse) {
		switch (direction) {
			case UP:
				execute(new PhysicsApplyLinearImpulse(0, impulse));
				//execute(new GraphicsSetLayerRotation(PlayerGraphics.Layer.LEGS, 0));
				break;
			case DOWN:
				execute(new PhysicsApplyLinearImpulse(0, -impulse));
				//execute(new GraphicsSetLayerRotation(PlayerGraphics.Layer.LEGS, 180));
				break;
			case LEFT:
				execute(new PhysicsApplyLinearImpulse(-impulse, 0));
				//execute(new GraphicsSetLayerRotation(PlayerGraphics.Layer.LEGS, 90));
				break;
			case RIGHT:
				execute(new PhysicsApplyLinearImpulse(impulse, 0));
				//execute(new GraphicsSetLayerRotation(PlayerGraphics.Layer.LEGS, 270));
				break;

		}
		execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.WALKING));
	}
	public void stopWalking() {
		execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.IDLE));
	}
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
}