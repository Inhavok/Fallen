package com.inhavok.fallen.components.entity_components.graphics;

import com.inhavok.fallen.components.entity_components.graphics.layers.Layer;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerTorsoLayer;

import java.util.ArrayList;

public class PlayerGraphics extends EntityGraphics {
	@Override
	ArrayList<Layer> addLayers() {
		final ArrayList<Layer> layers = new ArrayList<Layer>();
		layers.add(new PlayerLegsLayer());
		layers.add(new PlayerTorsoLayer());
		return layers;
	}
}