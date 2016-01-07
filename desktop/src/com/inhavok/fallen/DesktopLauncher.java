package com.inhavok.fallen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

final class DesktopLauncher {
	private DesktopLauncher() {
	}
	public static void main(final String[] args) {
		final LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "Fallen";
		new LwjglApplication(new Application(), configuration);
	}
}