package com.inhavok.fallen.components.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

import java.util.ArrayList;

public class PlayerTorsoLayer extends Layer {
	@Override
	ArrayList<Animation> addAnimations() {
		final ArrayList<Animation> animations = new ArrayList<Animation>();
		animations.add(EntityGraphics.createAnimation(Assets.getEntities().findRegion("player/torso")));
		return animations;
	}
}