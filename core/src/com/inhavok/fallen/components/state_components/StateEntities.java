package com.inhavok.fallen.components.state_components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.entity.AIThink;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesDraw;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesInterpolate;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesLookAt;
import com.inhavok.fallen.components.entity_components.ai.EntityAI;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.entities.Entity;
import com.inhavok.fallen.states.State;

import java.util.ArrayList;

public final class StateEntities extends StateComponent {
	private final ArrayList<Entity> previousState = new ArrayList<Entity>();
	private final ArrayList<Entity> currentState = new ArrayList<Entity>();
	private static final OrthographicCamera camera = new OrthographicCamera();
	private static final float cameraSpeed = 3;
	public StateEntities(final State state) {
		super(state);
	}
	@Override
	public void handleCommand(Command command) {
		if (command.getMessage() == Message.UPDATE) {
			update();
		} else if (command.getMessage() == Message.INTERPOLATE) {
			interpolate(((EntitiesInterpolate) command).getAlpha());
		} else if (command.getMessage() == Message.DRAW) {
			draw(((EntitiesDraw) command).getSpriteBatch());
		} else if (command.getMessage() == Message.ADD) {
			currentState.add(((EntitiesAdd) command).getEntity());
		} else if (command.getMessage() == Message.LOOK_AT) {
			lookAt(((EntitiesLookAt) command).getPoint());
		}
	}
	private void update() {
		previousState.clear();
		previousState.addAll(currentState);
		for (Entity entity : currentState) {
			entity.update();
			if (entity.hasComponent(EntityAI.class)) {
				entity.execute(new AIThink());
			}
			if (entity.hasComponent(EntityGraphics.class) && entity.hasComponent(EntityPhysics.class)) {
				entity.execute(new GraphicsSetX(entity.getX()));
				entity.execute(new GraphicsSetY(entity.getY()));
				entity.execute(new GraphicsAnimate(Application.SECONDS_PER_STEP));
			}
		}
	}
	private void interpolate(final float alpha) {
		int currentEntityID = 0;
		for (Entity interpolatedEntity : previousState) {
			if (interpolatedEntity.hasComponent(EntityGraphics.class) && interpolatedEntity.hasComponent(EntityPhysics.class)) {
				final Entity currentEntity = currentState.get(currentEntityID);
				interpolatedEntity.execute(new GraphicsSetX(interpolatedEntity.requestData(new GraphicsGetX(), Float.class) + (currentEntity.requestData(new GraphicsGetX(), Float.class) - interpolatedEntity.requestData(new GraphicsGetX(), Float.class)) * alpha));
				interpolatedEntity.execute(new GraphicsSetY(interpolatedEntity.requestData(new GraphicsGetY(), Float.class) + (currentEntity.requestData(new GraphicsGetY(), Float.class) - interpolatedEntity.requestData(new GraphicsGetY(), Float.class)) * alpha));
			}
			currentEntityID++;
		}
	}
	private void draw(final SpriteBatch spriteBatch) {
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (Entity entity : previousState) {
			entity.execute(new GraphicsDraw(spriteBatch));
		}
		spriteBatch.end();
	}
	private static void lookAt(final Vector2 point) {
		final Vector2 toPlayer = new Vector2(point.sub(camera.position.x, camera.position.y));
		if (toPlayer.len() > 0.5) {
			final Vector2 moveVelocity = new Vector2(toPlayer).setLength(cameraSpeed).scl(Application.SECONDS_PER_STEP).scl((float) Math.pow(toPlayer.len(), 5));
			camera.position.x += moveVelocity.x;
			camera.position.y += moveVelocity.y;
			camera.update();
		}
	}
	public static void resize(final float width, final float height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}
	public enum Message {
		UPDATE, INTERPOLATE, DRAW, ADD, LOOK_AT
	}
}