package com.inhavok.fallen.components.entity_components.graphics.layers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.ArrayList;

public abstract class Layer {
	private final ArrayList<Animation> animations = new ArrayList<Animation>();
	private Fixture fixture;
	private float stateTime;
	Layer() {
		this.animations.addAll(addAnimations());
	}
	abstract ArrayList<Animation> addAnimations();
	public void animate(final float delta) {
		stateTime += delta;
	}
	public Sprite getSprite() {
		return new Sprite(animations.get(0).getKeyFrame(stateTime));
	}
	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}
}