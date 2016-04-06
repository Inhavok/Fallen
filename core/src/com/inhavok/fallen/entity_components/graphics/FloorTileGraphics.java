package com.inhavok.fallen.entity_components.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.inhavok.fallen.Assets;

import java.util.LinkedHashMap;

public class FloorTileGraphics extends EntityGraphics {
	@Override
	LinkedHashMap<Enum, com.inhavok.fallen.entity_components.graphics.layers.Layer> addLayers() {
		final LinkedHashMap<Enum, com.inhavok.fallen.entity_components.graphics.layers.Layer> layers = new LinkedHashMap<Enum, com.inhavok.fallen.entity_components.graphics.layers.Layer>();
		layers.put(Layer.BASE, new com.inhavok.fallen.entity_components.graphics.layers.Layer() {
			@Override
			public Sprite getSprite() {
				return new Sprite(Assets.getEnvironment().findRegion("floor"));
			}
		});
		return layers;
	}
	public enum Layer {
		BASE
	}
}