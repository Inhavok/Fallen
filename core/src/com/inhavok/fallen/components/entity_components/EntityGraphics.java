package com.inhavok.fallen.components.entity_components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;

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
	@Override
	public void handleCommand(Command command) {
		if (command.getMessage() == Message.DRAW) {
			draw(((GraphicsDraw) command).getSpriteBatch());
		} else if (command.getMessage() == Message.ANIMATE) {
			animate(((GraphicsAnimate) command).getDelta());
		} else if (command.getMessage() == Message.GET_X) {
			((GraphicsGetX) command).setData(getX());
		} else if (command.getMessage() == Message.GET_Y) {
			((GraphicsGetY) command).setData(getY());
		} else if (command.getMessage() == Message.GET_ROTATION) {
			((GraphicsGetRotation) command).setData(getRotation());
		} else if (command.getMessage() == Message.SET_X) {
			setX(((GraphicsSetX) command).getX());
		} else if (command.getMessage() == Message.SET_Y) {
			setY(((GraphicsSetY) command).getY());
		} else if (command.getMessage() == Message.SET_ROTATION) {
			setRotation(((GraphicsSetRotation) command).getAngle());
		}
	}
	private void animate(final float delta) {
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
	private void draw(final SpriteBatch spriteBatch) {
		spriteBatch.draw(new TextureRegion(sprite.getTexture(), sprite.getRegionX(), sprite.getRegionY(), sprite.getRegionWidth(), sprite.getRegionHeight()), sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation());
	}
	public float getWidth() {
		return sprite.getWidth();
	}
	public float getHeight() {
		return sprite.getHeight();
	}
	private float getX() {
		return sprite.getX() + sprite.getWidth() / 2;
	}
	private float getY() {
		return sprite.getY() + sprite.getHeight() / 2;
	}
	private float getRotation() {
		return sprite.getRotation();
	}
	private void setX(final float x) {
		sprite.setX(x - sprite.getWidth() / 2);
	}
	private void setY(final float y) {
		sprite.setY(y - sprite.getHeight() / 2);
	}
	private void setRotation(final float rotation) {
		sprite.setRotation(rotation);
	}
	public enum Message {
		ANIMATE, DRAW, GET_X, GET_Y, GET_ROTATION, SET_X, SET_Y, SET_ROTATION
	}
}