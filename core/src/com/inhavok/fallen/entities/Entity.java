package com.inhavok.fallen.entities;

import com.badlogic.gdx.Gdx;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

import java.util.ArrayList;

public abstract class Entity {
	private final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
	Entity(final float x, final float y) {
		this(x, y, 0);
	}
	Entity(final float x, final float y, final float angle) {
		components.addAll(addComponents());
		if (hasComponent(EntityGraphics.class)) {
			final EntityGraphics entityGraphics = getComponent(EntityGraphics.class);
			entityGraphics.setX(x);
			entityGraphics.setY(y);
			entityGraphics.setRotation(angle);
		}
		if (hasComponent(EntityPhysics.class)) {
			final EntityPhysics entityPhysics = getComponent(EntityPhysics.class);
			entityPhysics.setX(x);
			entityPhysics.setY(y);
			entityPhysics.setRotation(angle);
		}
	}
	abstract ArrayList<EntityComponent> addComponents();
	public final void update() {
		updateState();
		if (hasComponent(EntityGraphics.class)) {
			getComponent(EntityGraphics.class).animate(Gdx.graphics.getDeltaTime());
		}
	}
	private void updateState() {
	}
	public final <T extends EntityComponent> boolean hasComponent(final Class<T> type) {
		return getComponent(type) != null;
	}
	public final <T extends EntityComponent> T getComponent(final Class<T> type) {
		for (EntityComponent component : components) {
			if (component.getClass() == type) {
				return type.cast(component);
			}
		}
		return null;
	}
}