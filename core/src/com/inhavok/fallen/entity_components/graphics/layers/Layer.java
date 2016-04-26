package com.inhavok.fallen.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Layer {
	private float rotation;
	public abstract Sprite getSprite();
	public float getRotation() {
		return rotation;
	}
	public void setRotation(final float angleInDegrees) {
		rotation = angleInDegrees;
	}
}