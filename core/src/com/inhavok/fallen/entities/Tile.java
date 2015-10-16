package com.inhavok.fallen.entities;

import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.graphics.TileGraphics;

import java.util.ArrayList;

public class Tile extends Entity {
	public Tile(final float x, final float y) {
		super(x, y, 0);
	}
	@Override
	ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		components.add(new TileGraphics());
		return components;
	}
}