package com.inhavok.fallen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Assets {
	private static final Skin skin = new Skin(new TextureAtlas(Gdx.files.internal("graphics\\skin\\skin.atlas")));
	//private static final TextureAtlas entities = new TextureAtlas(Gdx.files.internal("graphics\\entities\\entities.atlas"));
	private Assets() {
	}
	static void initialise() {
		final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("graphics\\skin\\calibri-light.ttf"));
		final FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();
		fontParameter.size = 14;
		skin.add("default-font", fontGenerator.generateFont(fontParameter));
		fontParameter.size = 60;
		skin.add("title-font", fontGenerator.generateFont(fontParameter));
		fontParameter.size = 30;
		skin.add("sub-title-font", fontGenerator.generateFont(fontParameter));
		fontParameter.size = 20;
		skin.add("small-sub-title-font", fontGenerator.generateFont(fontParameter));
		skin.load(Gdx.files.internal("graphics\\skin\\skin.json"));
		fontGenerator.dispose();
	}
	public static Skin getSkin() {
		return skin;
	}
	/*
	public static TextureAtlas getEntities() {
		return entities;
	}
	*/
	static void dispose() {
		skin.dispose();
		//entities.dispose();
	}
}