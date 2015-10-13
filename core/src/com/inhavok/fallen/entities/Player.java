package com.inhavok.fallen.entities;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.entity_components.graphics.PlayerGraphics;

import java.util.ArrayList;

public final class Player extends Entity {
	public Player(float x, float y, float angle) {
		super(x, y, angle);
	}
	@Override
	ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		final EntityGraphics graphics = new PlayerGraphics();
		final EntityPhysics physics = new EntityPhysics(graphics.getWidth(), graphics.getHeight(), BodyDef.BodyType.DynamicBody, 0, 0);
		components.add(graphics);
		components.add(physics);
		return components;
	}
}