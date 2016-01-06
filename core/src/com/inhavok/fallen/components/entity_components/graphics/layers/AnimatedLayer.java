package com.inhavok.fallen.components.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
	public abstract Animation getAnimation();
	public final Animation getAnimation(final Enum animation) {
		return animations.get(animation);
	}
	public abstract void setAnimation(final Enum animation);
	public final void setAnimationFrameDuration(final Enum animation, final float frameDuration) {
		final float previousFrameDuration = getAnimations().get(animation).getFrameDuration();
		getAnimations().get(animation).setFrameDuration(frameDuration);
		stateTime *= frameDuration / previousFrameDuration;
	}
}