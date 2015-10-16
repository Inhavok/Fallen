package com.inhavok.fallen;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

final class CreateAssets {
	private CreateAssets() {
	}
	public static void main(final String[] args) {
		final Settings settings = new Settings();
		settings.pot = false;
		settings.maxWidth = Integer.MAX_VALUE;
		settings.maxHeight = Integer.MAX_VALUE;
		settings.filterMin = TextureFilter.Linear;
		TexturePacker.process(settings, "C:\\Users\\Ben\\Desktop\\Developer\\Projects\\Fallen\\core\\assets\\graphics\\entities", "C:\\Users\\Ben\\Desktop\\Developer\\Projects\\Fallen\\core\\assets\\graphics\\entities", "entities");
		TexturePacker.process(settings, "C:\\Users\\Ben\\Desktop\\Developer\\Projects\\Fallen\\core\\assets\\graphics\\environment", "C:\\Users\\Ben\\Desktop\\Developer\\Projects\\Fallen\\core\\assets\\graphics\\environment", "environment");
	}
}