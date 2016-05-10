package com.inhavok.fallen;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

final class CreateAssets {
	private CreateAssets() {
	}
	public static void main(final String[] args) {
		final TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.pot = false;
		settings.duplicatePadding = true;
		settings.maxWidth = Integer.MAX_VALUE;
		settings.maxHeight = Integer.MAX_VALUE;
		TexturePacker.process(settings, "C:\\Users\\Ben\\Desktop\\Developer\\Projects\\Fallen\\core\\assets\\graphics\\entities", "C:\\Users\\Ben\\Desktop\\Developer\\Projects\\Fallen\\core\\assets\\graphics\\entities", "entities");
		TexturePacker.process(settings, "C:\\Users\\Ben\\Desktop\\Developer\\Projects\\Fallen\\core\\assets\\graphics\\environment", "C:\\Users\\Ben\\Desktop\\Developer\\Projects\\Fallen\\core\\assets\\graphics\\environment", "environment");
	}
}