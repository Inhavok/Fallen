package com.inhavok.fallen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;

public final class Assets {
	private static final Skin skin = new Skin(new TextureAtlas(Gdx.files.internal("graphics" + File.separator + "skin" + File.separator + "skin.atlas")));
	private static final TextureAtlas entities = new TextureAtlas(Gdx.files.internal("graphics" + File.separator + "entities" + File.separator + "entities.atlas"));
	private static final TextureAtlas environment = new TextureAtlas(Gdx.files.internal("graphics" + File.separator + "environment" + File.separator + "environment.atlas"));
	private Assets() {
	}
	static void initialise() {
		final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("graphics" + File.separator + "skin" + File.separator + "calibri-light.ttf"));
		final FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		fontParameter.size = 14;
		skin.add("default-font", fontGenerator.generateFont(fontParameter), BitmapFont.class);
		fontParameter.size = 40;
		skin.add("title-font", fontGenerator.generateFont(fontParameter), BitmapFont.class);
		fontParameter.size = 30;
		skin.add("sub-title-font", fontGenerator.generateFont(fontParameter), BitmapFont.class);
		fontParameter.size = 20;
		skin.add("small-sub-title-font", fontGenerator.generateFont(fontParameter), BitmapFont.class);
		skin.load(Gdx.files.internal("graphics" + File.separator + "skin" + File.separator + "skin.json"));
		fontGenerator.dispose();
	}
	public static Skin getSkin() {
		return skin;
	}
	public static TextureAtlas getEntities() {
		return entities;
	}
	public static TextureAtlas getEnvironment() {
		return environment;
	}
	static void dispose() {
		skin.dispose();
		entities.dispose();
	}
}