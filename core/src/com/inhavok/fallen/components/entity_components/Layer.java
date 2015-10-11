package com.inhavok.fallen.components.entity_components;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Fixture;

class Layer {

	//O

	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private float stateTime;


	private Layer(HashMap<String, Animation> animations, Fixture f) {
		Fixture f1 = f;
		this.animations = animations;
	}

	public Layer (HashMap<String, Animation> animations) {
		this(animations, null);
	}

	public Sprite getSprite() {
		Sprite sprite = new Sprite();
		sprite.setRegion((new ArrayList<Animation>(animations.values())).get(0).getKeyFrame(stateTime));
		return sprite;
	}

	public Animation getAnimation() {
		return (new ArrayList<Animation>(animations.values())).get(0);
	}

	public void playAnimation(String name) {

	}



}