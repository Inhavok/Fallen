package com.inhavok.fallen.components.entity_components.graphics;

import com.inhavok.fallen.components.entity_components.graphics.layers.Layer;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerTorsoLayer;

import java.util.LinkedHashMap;

public class PlayerGraphics extends EntityGraphics {
	@Override
	LinkedHashMap<Enum, Layer> addLayers() {
		final LinkedHashMap<Enum, Layer> layers = new LinkedHashMap<Enum, Layer>();
		layers.put(Layers.LEGS, new PlayerLegsLayer());
		layers.put(Layers.TORSO, new PlayerTorsoLayer());
		return layers;
	}
	public enum Layers {
		LEGS, TORSO
	}
}