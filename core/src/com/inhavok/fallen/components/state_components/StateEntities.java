package com.inhavok.fallen.components.state_components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetY;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesAdd;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesDraw;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesInterpolate;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.entities.Entity;
import com.inhavok.fallen.states.State;

import java.util.ArrayList;

public final class StateEntities extends StateComponent {
	private final ArrayList<Entity> previousState = new ArrayList<Entity>();
	private final ArrayList<Entity> currentState = new ArrayList<Entity>();
	private static final OrthographicCamera camera = new OrthographicCamera();
	public StateEntities(final State state) {
		super(state);
	}
	@Override
	public void handleCommand(Command command) {
		if (command.getMessage() == Message.UPDATE_STATE) {
			updateState();
		} else if (command.getMessage() == Message.INTERPOLATE) {
			interpolate(((EntitiesInterpolate) command).getAlpha());
		} else if (command.getMessage() == Message.DRAW) {
			draw(((EntitiesDraw) command).getSpriteBatch());
		} else if (command.getMessage() == Message.ADD) {
			currentState.add(((EntitiesAdd) command).getEntity());
		}
	}
	private void updateState() {
		previousState.clear();
		previousState.addAll(currentState);
		for (Entity entity : currentState) {
			if (entity.hasComponent(EntityPhysics.class) && entity.hasComponent(EntityGraphics.class)) {
				entity.execute(new GraphicsSetX(entity.requestData(new PhysicsGetX(), Float.class)));
				entity.execute(new GraphicsSetY(entity.requestData(new PhysicsGetY(), Float.class)));
				entity.execute(new GraphicsSetRotation(entity.requestData(new PhysicsGetRotation(), Float.class)));
			}
			entity.updateState();
		}
	}
	private void interpolate(final float alpha) {
		int currentEntityID = 0;
		for (Entity interpolatedEntity : previousState) {
			final Entity currentEntity = currentState.get(currentEntityID);
			if (interpolatedEntity.hasComponent(EntityGraphics.class) && currentEntity.hasComponent(EntityGraphics.class)) {
				interpolatedEntity.execute(new GraphicsSetX(interpolatedEntity.requestData(new GraphicsGetX(), Float.class) + (currentEntity.requestData(new GraphicsGetX(), Float.class) - interpolatedEntity.requestData(new GraphicsGetX(), Float.class)) * alpha));
				interpolatedEntity.execute(new GraphicsSetY(interpolatedEntity.requestData(new GraphicsGetY(), Float.class) + (currentEntity.requestData(new GraphicsGetY(), Float.class) - interpolatedEntity.requestData(new GraphicsGetY(), Float.class)) * alpha));
				interpolatedEntity.execute(new GraphicsSetRotation(interpolatedEntity.requestData(new GraphicsGetRotation(), Float.class) + (currentEntity.requestData(new GraphicsGetRotation(), Float.class) - interpolatedEntity.requestData(new GraphicsGetRotation(), Float.class)) * alpha));
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
	public static void lookAt(final float x, final float y) {
		camera.position.x = x;
		camera.position.y = y;
		camera.update();
	}
	public static void resize(final float width, final float height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}
	public static OrthographicCamera getCamera() {
		return camera;
	}
	public enum Message {
		UPDATE_STATE, INTERPOLATE, DRAW, ADD
	}
}