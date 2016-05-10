package com.inhavok.fallen;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

final class DesktopLauncher {
	private DesktopLauncher() {
	}
	public static void main(final String[] args) {
		final Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("Fallen");
		configuration.setResizable(false);
		new Lwjgl3Application(new Application(), configuration);
	}
}