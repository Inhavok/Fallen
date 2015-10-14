package com.inhavok.fallen.components.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

public abstract class Layer {
	private final HashMap<Enum, Animation> animations;
	private float stateTime;
	private float angle;
	Layer() {
		this.animations = addAnimations();
	}
	abstract HashMap<Enum, Animation> addAnimations();
	public final void animate(final float delta) {
		stateTime += delta;
	}
	HashMap<Enum, Animation> getAnimations() {
		return animations;
	}
	protected abstract Animation getAnimation();
	public final Sprite getSprite() {
		return new Sprite(getAnimation().getKeyFrame(stateTime));
	}
	public float getRotation() {
		return angle;
	}
	public abstract void setAnimation(final Enum animation);
	public void setRotation(final float angle) {
		this.angle = angle;
	}
}