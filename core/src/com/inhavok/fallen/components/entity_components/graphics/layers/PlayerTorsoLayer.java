package com.inhavok.fallen.components.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerTorsoLayer extends Layer {
	@Override
	HashMap<Enum, Animation> addAnimations() {
		final HashMap<Enum, Animation> animations = new HashMap<Enum, Animation>();
		animations.put(null, EntityGraphics.createAnimation(Assets.getEntities().findRegion("player/torso")));
		return animations;
	}
	@Override
	public Animation getAnimation() {
		final ArrayList<Animation> animations = new ArrayList<Animation>();
		animations.addAll(getAnimations().values());
		return animations.get(0);
	}
	@Override
	public void setAnimation(final Enum animation) {
	}
}