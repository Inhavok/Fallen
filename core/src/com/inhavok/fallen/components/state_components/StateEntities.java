package com.inhavok.fallen.components.state_components;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.inhavok.fallen.commands.CommandManager;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetY;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetY;
import com.inhavok.fallen.components.entity_components.EntityGraphics;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.entities.Entity;

import java.util.ArrayList;

public final class StateEntities extends StateComponent {
	private final ArrayList<Entity> previousState = new ArrayList<Entity>();
	private final ArrayList<Entity> currentState = new ArrayList<Entity>();
	private static final OrthographicCamera CAMERA = new OrthographicCamera();
	public void updateState() {
		previousState.clear();
		previousState.addAll(currentState);
		for (Entity entity : currentState) {
			CommandManager.add(new GraphicsSetX((Float) CommandManager.requestData(new PhysicsGetX())));
			CommandManager.add(new GraphicsSetY((Float) CommandManager.requestData(new PhysicsGetY())));
			CommandManager.add(new GraphicsSetRotation((Float) CommandManager.requestData(new PhysicsGetRotation())));
			entity.update();
		}
	}
	public void interpolate(final float alpha) {
		int currentEntityID = 0;
		for (Entity interpolatedEntity : previousState) {
			final Entity currentEntity = currentState.get(currentEntityID);



			interpolatedEntityGraphics.setX(interpolatedEntityGraphics.getX() + (currentEntityGraphics.getX() - interpolatedEntityGraphics.getX()) * alpha);
			interpolatedEntityGraphics.setY(interpolatedEntityGraphics.getY() + (currentEntityGraphics.getY() - interpolatedEntityGraphics.getY()) * alpha);
			interpolatedEntityGraphics.setRotation(interpolatedEntityGraphics.getRotation() + (currentEntityGraphics.getRotation() - interpolatedEntityGraphics.getRotation()) * alpha);
			currentEntityID++;
		}
	}
	public void draw(final SpriteBatch spriteBatch) {
		CAMERA.update();
		spriteBatch.setProjectionMatrix(CAMERA.combined);
		spriteBatch.begin();
		for (Entity entity : previousState) {
			if (entity.hasComponent(EntityGraphics.class)) {
				final Sprite sprite = entity.getComponent(EntityGraphics.class).getSprite();
				spriteBatch.draw(new TextureRegion(sprite.getTexture(), sprite.getRegionX(), sprite.getRegionY(), sprite.getRegionWidth(), sprite.getRegionHeight()), sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
			}
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
}