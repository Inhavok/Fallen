package com.inhavok.fallen.components.entity_components.graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;

import java.util.LinkedHashMap;

public class PlayerGraphics extends EntityGraphics {
	public PlayerGraphics() {
		super(1/4f);
	}
	@Override
	LinkedHashMap<Enum, com.inhavok.fallen.components.entity_components.graphics.layers.Layer> addLayers() {
		final LinkedHashMap<Enum, com.inhavok.fallen.components.entity_components.graphics.layers.Layer> layers = new LinkedHashMap<Enum, com.inhavok.fallen.components.entity_components.graphics.layers.Layer>();
		layers.put(Layer.LEGS, new PlayerLegsLayer());
		layers.put(Layer.TORSO, new com.inhavok.fallen.components.entity_components.graphics.layers.Layer() {
			@Override
			public Sprite getSprite() {
				return new Sprite(Assets.getEntities().findRegion("player/torso"));
			}
		});
		return layers;
	}
	public enum Layer {
		LEGS, TORSO
	}
}