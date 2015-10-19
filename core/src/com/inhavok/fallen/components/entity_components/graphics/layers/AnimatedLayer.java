package com.inhavok.fallen.components.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Enumeration;
import java.util.HashMap;

public abstract class AnimatedLayer extends Layer {
	private final HashMap<Enum, Animation> animations;
	private float stateTime;
	AnimatedLayer() {
		animations = addAnimations();
	}
	abstract HashMap<Enum, Animation> addAnimations();
	public final void animate(final float delta) {
		stateTime += delta;
	}
	public final Sprite getSprite() {
		return new Sprite(getAnimation().getKeyFrame(stateTime));
	}
	HashMap<Enum, Animation> getAnimations() {
		return animations;
	}
	protected abstract Animation getAnimation();
	public abstract void setAnimation(final Enum animation);
	public abstract void setAnimationFrameDuration(final Enum animation, final float frameDuration);
}