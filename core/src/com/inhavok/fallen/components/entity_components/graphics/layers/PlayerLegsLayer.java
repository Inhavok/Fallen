package com.inhavok.fallen.components.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

import java.util.ArrayList;

public class PlayerLegsLayer extends Layer {
	@Override
	ArrayList<Animation> addAnimations() {
		final ArrayList<Animation> animations = new ArrayList<Animation>();
		animations.add(EntityGraphics.createAnimation(Assets.getEntities().findRegion("player/legs_walk", 2)));
		animations.add(EntityGraphics.createAnimation(0.15f, Assets.getEntities().findRegions("player/legs_walk"), Animation.PlayMode.LOOP));
		return animations;
	}
}