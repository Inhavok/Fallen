package com.inhavok.fallen.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Layer {
	private float angle;
	public abstract Sprite getSprite();
	public float getRotation() {
		return angle;
	}
	public void setRotation(final float angle) {
		this.angle = angle;
	}
}