package com.inhavok.fallen.components.entity_components.graphics.layers;

import com.inhavok.fallen.Assets;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

import java.util.HashMap;

public class PlayerLegsLayer extends AnimatedLayer {
	private Animation currentAnimation = Animation.IDLE;
	@Override
	HashMap<Enum, com.badlogic.gdx.graphics.g2d.Animation> addAnimations() {
		final HashMap<Enum, com.badlogic.gdx.graphics.g2d.Animation> animations = new HashMap<Enum, com.badlogic.gdx.graphics.g2d.Animation>();
		animations.put(Animation.IDLE, EntityGraphics.createAnimation(Assets.getEntities().findRegion("player/legs_walk", 2)));
		animations.put(Animation.WALKING, EntityGraphics.createAnimation(0.2f, Assets.getEntities().findRegions("player/legs_walk"), com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP));
		return animations;
	}
	@Override
	public com.badlogic.gdx.graphics.g2d.Animation getAnimation() {
		return getAnimations().get(currentAnimation);
	}
	@Override
	public void setAnimation(final Enum animation) {
		if (animation == Animation.IDLE) {
			currentAnimation = Animation.IDLE;
		} else if (animation == Animation.WALKING) {
			currentAnimation = Animation.WALKING;
		}
	}
	public enum Animation {
		IDLE, WALKING
	}
}