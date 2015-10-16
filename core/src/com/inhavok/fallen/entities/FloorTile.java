package com.inhavok.fallen.entities;

import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.graphics.FloorTileGraphics;

import java.util.ArrayList;

public class FloorTile extends Entity {
	public FloorTile(final float x, final float y) {
		super(x, y, 0);
	}
	@Override
	ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		components.add(new FloorTileGraphics());
		return components;
	}
}