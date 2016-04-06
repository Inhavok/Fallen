package com.inhavok.fallen.entity_components.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.entity_components.EntityComponent;
import com.inhavok.fallen.entity_components.graphics.layers.AnimatedLayer;
import com.inhavok.fallen.entity_components.graphics.layers.Layer;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class EntityGraphics extends EntityComponent {
	private final LinkedHashMap<Enum, Layer> layers;
	private final float width;
	private final float height;
	private float x;
	private float y;
	public EntityGraphics() {
		this(1);
	}
	public EntityGraphics(final float scale) {
		this.layers = addLayers();
		final ArrayList<Layer> layers = new ArrayList<Layer>();
		layers.addAll(this.layers.values());
		width = (layers.get(0).getSprite().getWidth() * scale) / Application.PIXELS_PER_METER;
		height = (layers.get(0).getSprite().getHeight() * scale) / Application.PIXELS_PER_METER;
	}
	abstract LinkedHashMap<Enum, Layer> addLayers();
	@Override
	public void handleCommand(Command command) {
		command.execute(this);
	}
	public void animate(final float delta) {
		for (Layer layer : layers.values()) {
			if (layer instanceof AnimatedLayer) {
				((AnimatedLayer) layer).animate(delta);
			}
		}
	}
	public static Animation createAnimation(final float frameDuration, final Array<AtlasRegion> atlasRegions, PlayMode playMode) {
		final Array<TextureRegion> textureRegions = new Array<TextureRegion>();
		textureRegions.addAll(atlasRegions);
		return new Animation(frameDuration, textureRegions, playMode);
	}
	public static Animation createAnimation(final AtlasRegion atlasRegion) {
		final Array<TextureRegion> textureRegions = new Array<TextureRegion>();
		textureRegions.add(atlasRegion);
		return new Animation(1, textureRegions, PlayMode.LOOP);
	}
	public void draw(final SpriteBatch spriteBatch) {
		for (Layer layer : layers.values()) {
			final Sprite sprite = layer.getSprite();
			spriteBatch.draw(new TextureRegion(sprite.getTexture(), sprite.getRegionX(), sprite.getRegionY(), sprite.getRegionWidth(), sprite.getRegionHeight()), x, y, width / 2, height / 2, width, height, sprite.getScaleX(), sprite.getScaleY(), layer.getRotation());
		}
	}
	public final float getWidth() {
		return width;
	}
	public final float getHeight() {
		return height;
	}
	public final float getX() {
		return x + width / 2;
	}
	public final float getY() {
		return y + height / 2;
	}
	public final float getRotation() {
		return ((Layer) layers.values().toArray()[0]).getRotation();
	}
	public final float getLayerRotation(final Enum layer) {
		return layers.get(layer).getRotation();
	}
	public final void setX(final float x) {
		this.x = x - width / 2;
	}
	public final void setY(final float y) {
		this.y = y - height / 2;
	}
	public final void setRotation(final float angle) {
		for (Layer layer : layers.values()) {
			layer.setRotation(angle);
		}
	}
	public final void setLayerRotation(final Enum layer, final float rotation) {
		layers.get(layer).setRotation(rotation);
	}
	public final void setAnimation(final Enum layer, final Enum animation) {
		if (layers.get(layer) instanceof AnimatedLayer) {
			final AnimatedLayer animatedLayer = (AnimatedLayer) layers.get(layer);
			if (animatedLayer.getAnimation() != animatedLayer.getAnimation(animation)) {
				animatedLayer.setAnimation(animation);
			}
		}
	}
	public final void setAnimationFrameDuration(final Enum layer, final Enum animation, final float frameDuration) {
		if (layers.get(layer) instanceof AnimatedLayer) {
			((AnimatedLayer) layers.get(layer)).setAnimationFrameDuration(animation, frameDuration);
		}
	}
}