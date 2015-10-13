package com.inhavok.fallen.components.entity_components.graphics;

import com.inhavok.fallen.components.entity_components.graphics.layers.Layer;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerTorsoLayer;

import java.util.HashMap;

public class PlayerGraphics extends EntityGraphics {
	@Override
	HashMap<Enum, Layer> addLayers() {
		final HashMap<Enum, Layer> layers = new HashMap<Enum, Layer>();
		layers.put(Layers.LEGS, new PlayerLegsLayer());
		layers.put(Layers.TORSO, new PlayerTorsoLayer());
		return layers;
	}
	public enum Layers {
		LEGS, TORSO
	}
}