package com.inhavok.fallen.components.state_components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetY;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesDraw;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesInterpolate;
import com.inhavok.fallen.entities.Entity;
import com.inhavok.fallen.states.State;

import java.util.ArrayList;

public final class StateEntities extends StateComponent {
	private final ArrayList<Entity> previousState = new ArrayList<Entity>();
	private final ArrayList<Entity> currentState = new ArrayList<Entity>();
	private static final OrthographicCamera CAMERA = new OrthographicCamera();
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
		}
	}
	private void updateState() {
		previousState.clear();
		previousState.addAll(currentState);
		for (Entity entity : currentState) {
			entity.execute(new GraphicsSetX(entity.requestData(new PhysicsGetX(), Float.class)));
			entity.execute(new GraphicsSetY(entity.requestData(new PhysicsGetY(), Float.class)));
			entity.execute(new GraphicsSetRotation(entity.requestData(new PhysicsGetRotation(), Float.class)));
			entity.update();
		}
	}
	private void interpolate(final float alpha) {
		int currentEntityID = 0;
		for (Entity interpolatedEntity : previousState) {
			final Entity currentEntity = currentState.get(currentEntityID);
			interpolatedEntity.execute(new GraphicsSetX(interpolatedEntity.requestData(new GraphicsGetX(), Float.class) + (currentEntity.requestData(new GraphicsGetX(), Float.class) - interpolatedEntity.requestData(new GraphicsGetX(), Float.class)) * alpha));
			interpolatedEntity.execute(new GraphicsSetY(interpolatedEntity.requestData(new GraphicsGetY(), Float.class) + (currentEntity.requestData(new GraphicsGetY(), Float.class) - interpolatedEntity.requestData(new GraphicsGetY(), Float.class)) * alpha));
			interpolatedEntity.execute(new GraphicsSetRotation(interpolatedEntity.requestData(new GraphicsGetRotation(), Float.class) + (currentEntity.requestData(new GraphicsGetRotation(), Float.class) - interpolatedEntity.requestData(new GraphicsGetRotation(), Float.class)) * alpha));
			currentEntityID++;
		}
	}
	private void draw(final SpriteBatch spriteBatch) {
		CAMERA.update();
		spriteBatch.setProjectionMatrix(CAMERA.combined);
		spriteBatch.begin();
		for (Entity entity : previousState) {
			entity.execute(new GraphicsDraw(spriteBatch));
		}
		spriteBatch.end();
	}
	public static void lookAt(final float x, final float y) {
		CAMERA.position.x = x;
		CAMERA.position.y = y;
		CAMERA.update();
	}
	public static void resize(final float width, final float height) {
		CAMERA.viewportWidth = width;
		CAMERA.viewportHeight = height;
		CAMERA.update();
	}
	public enum Message {
		UPDATE_STATE, INTERPOLATE, DRAW
	}
}