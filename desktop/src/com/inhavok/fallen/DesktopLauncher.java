package com.inhavok.fallen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inhavok.fallen.Application;

final class DesktopLauncher {
	private DesktopLauncher() {
	}
	public static void main(final String[] args) {
		final LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.fullscreen = true;
		configuration.foregroundFPS = Math.round(1 / Application.SECONDS_PER_FRAME);
		configuration.backgroundFPS = configuration.foregroundFPS;
		new LwjglApplication(new Application(), configuration);
	}
}