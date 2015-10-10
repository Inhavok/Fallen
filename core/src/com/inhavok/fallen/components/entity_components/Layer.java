package com.inhavok.fallen.components.entity_components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Fixture;

class Layer {
	
	//O
	
	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private Fixture f;
	private float stateTime;
	
	
	public Layer(HashMap<String, Animation> animations, Fixture f) {
		this.f = f;
		this.animations = animations;
	}
	
	public Layer (HashMap<String, Animation> animations) {
		this(animations, null);
	}
	
	public Sprite getSprite() {
		Sprite sprite;
		return sprite.setRegion((new ArrayList<Animation>(animations)).get(0).getKeyFrame(stateTime));
		
		
	}
	
	
	
}