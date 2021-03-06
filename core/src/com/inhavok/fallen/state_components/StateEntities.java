package com.inhavok.fallen.state_components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.utility.Data;
import com.inhavok.fallen.commands.entity.AICommand;
import com.inhavok.fallen.commands.entity.GraphicsCommand;
import com.inhavok.fallen.entity_components.ai.EntityAI;
import com.inhavok.fallen.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.entity_components.EntityPhysics;
import com.inhavok.fallen.entities.Entity;
import com.inhavok.fallen.states.State;

import java.util.ArrayList;

public final class StateEntities extends StateComponent {
	private final ArrayList<Entity> previousState = new ArrayList<Entity>();
	private final ArrayList<Entity> currentState = new ArrayList<Entity>();
	private final OrthographicCamera camera = new OrthographicCamera();
	public StateEntities(final State state) {
		super(state);
	}
	@Override
	public void handleCommand(Command command) {
		command.execute(this);
	}
	public void update() {
		previousState.clear();
		previousState.addAll(currentState);
		for (final Entity entity : currentState) {
			entity.update();
			if (entity.hasComponent(EntityAI.class)) {
				entity.execute(new AICommand() {
					@Override
					public void execute(EntityAI listener) {
						listener.think();
					}
				});
			}
			if (entity.hasComponent(EntityGraphics.class) && entity.hasComponent(EntityPhysics.class)) {
				entity.execute(new GraphicsCommand() {
					@Override
					public void execute(EntityGraphics listener) {
						listener.setX(entity.getX());
						listener.setY(entity.getY());
						listener.animate(Application.SECONDS_PER_STEP);
					}
				});
			}
		}
	}
	public void interpolate(final float alpha) {
		int currentEntityID = 0;
		for (final Entity interpolatedEntity : previousState) {
			if (interpolatedEntity.hasComponent(EntityGraphics.class) && interpolatedEntity.hasComponent(EntityPhysics.class)) {
				final Entity currentEntity = currentState.get(currentEntityID);
				interpolatedEntity.execute(new GraphicsCommand() {
					@Override
					public void execute(EntityGraphics listener) {
						final Data<Float> previousXData = new Data<Float>();
						final Data<Float> previousYData = new Data<Float>();
						final Data<ArrayList<Float>> previousGraphicsRotationsData = new Data<ArrayList<Float>>();
						final Data<Float> nextXData = new Data<Float>();
						final Data<Float> nextYData = new Data<Float>();
						final Data<ArrayList<Float>> nextGraphicsRotationsData = new Data<ArrayList<Float>>();
						interpolatedEntity.execute(new GraphicsCommand() {
							@Override
							public void execute(EntityGraphics listener) {
								previousXData.a = listener.getX();
								previousYData.a = listener.getY();
								previousGraphicsRotationsData.a = listener.getRotations();
							}
						});
						currentEntity.execute(new GraphicsCommand() {
							@Override
							public void execute(EntityGraphics listener) {
								nextXData.a = listener.getX();
								nextYData.a = listener.getY();
								nextGraphicsRotationsData.a = listener.getRotations();
							}
						});
						listener.setX(previousXData.a + (nextXData.a - previousXData.a) * alpha);
						listener.setY(previousYData.a + (nextYData.a - previousYData.a) * alpha);
						final ArrayList<Float> interpolatedGraphicsRotations = new ArrayList<Float>();
						int i = 0;
						for (float angle : previousGraphicsRotationsData.a) {
							interpolatedGraphicsRotations.add(angle + ((nextGraphicsRotationsData.a.get(i) - angle) * alpha));
							i++;
						}
						listener.setRotations(interpolatedGraphicsRotations);
					}
				});
			}
			currentEntityID++;
		}
	}
	public void draw(final SpriteBatch spriteBatch) {
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (Entity entity : previousState) {
			entity.execute(new GraphicsCommand() {
				@Override
				public void execute(EntityGraphics listener) {
					listener.draw(spriteBatch);
				}
			});
		}
		spriteBatch.end();
	}
	public void lookAt(final float x, final float y) {
		camera.position.x = x;
		camera.position.y = y;
		camera.update();
	}
	public void resize(final float width, final float height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
	}
	public void add(final Entity entity) {
		currentState.add(entity);
	}
	public OrthographicCamera getCamera() {
		return camera;
	}
}