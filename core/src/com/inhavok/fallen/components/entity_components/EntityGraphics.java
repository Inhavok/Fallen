package com.inhavok.fallen.components.entity_components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.inhavok.fallen.Application;

import java.util.ArrayList;

public final class EntityGraphics extends EntityComponent {
	private final Sprite sprite;
	private final ArrayList<Animation> animations = new ArrayList<Animation>();
	private float stateTime;
	public EntityGraphics(final Animation animation) {
		animation.setPlayMode(PlayMode.LOOP);
		animations.add(animation);
		sprite = new Sprite(animations.get(0).getKeyFrame(0));
		sprite.setOriginCenter();
		sprite.setScale(1f / Application.PIXELS_PER_METER);
	}
	public void animate(final float delta) {
		stateTime += delta;
	}
	public static Animation createAnimation(final float frameDuration, final Array<AtlasRegion> atlasRegions) {
		final Array<TextureRegion> textureRegions = new Array<TextureRegion>();
		textureRegions.addAll(atlasRegions);
		return new Animation(frameDuration, textureRegions);
	}
	public Sprite getSprite() {
		sprite.setRegion(animations.get(0).getKeyFrame(stateTime));
		return sprite;
	}
	public float getWidth() {
		return sprite.getWidth();
	}
	public float getHeight() {
		return sprite.getHeight();
	}
	public float getX() {
		return sprite.getX() + sprite.getWidth() / 2;
	}
	public float getY() {
		return sprite.getY() + sprite.getHeight() / 2;
	}
	public float getRotation() {
		return sprite.getRotation();
	}
	public void setX(final float x) {
		sprite.setX(x - sprite.getWidth() / 2);
	}
	public void setY(final float y) {
		sprite.setY(y - sprite.getHeight() / 2);
	}
	public void setRotation(final float rotation) {
		sprite.setRotation(rotation);
	}
}