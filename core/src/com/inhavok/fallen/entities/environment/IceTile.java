package com.inhavok.fallen.entities.environment;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.graphics.IceTileGraphics;
import com.inhavok.fallen.entities.Entity;

import java.util.ArrayList;

public final class IceTile extends Entity {
	public IceTile(final float x, final float y) {
		super(x, y, 0);
	}
	@Override
	protected ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		final EntityGraphics graphics = new IceTileGraphics();
		components.add(graphics);
		components.add(new EntityPhysics(graphics.getWidth(), graphics.getHeight(), BodyDef.BodyType.StaticBody));
		return components;
	}
}