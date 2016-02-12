package com.inhavok.fallen.entities;

import com.inhavok.fallen.components.entity_components.EntityComponent;

import java.util.ArrayList;

//TODO implement Bullet
public class Bullet extends Entity {
	protected Bullet(float x, float y, float angle) {
		super(x, y, angle);
	}
	@Override
	protected ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		return null;
	}
}