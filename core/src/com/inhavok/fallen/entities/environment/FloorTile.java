package com.inhavok.fallen.entities.environment;

import com.inhavok.fallen.entity_components.EntityComponent;
import com.inhavok.fallen.entity_components.graphics.FloorTileGraphics;
import com.inhavok.fallen.entities.Entity;

import java.util.ArrayList;

public final class FloorTile extends Entity {
	public FloorTile(final float x, final float y) {
		super(x, y, 0);
	}
	@Override
	protected ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		components.add(new FloorTileGraphics());
		return components;
	}
}