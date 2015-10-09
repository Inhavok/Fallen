package com.inhavok.fallen.states;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.state_components.PlayStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.components.state_components.StateEntities;

public final class PlayState extends State {
	private boolean fullScreen = true;
	@Override
	ArrayList<StateComponent> addComponents() {
		final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
		components.add(new PlayStateUI());
		components.add(new StateEntities());
		return components;
	}
	@Override
	public void handleKeyPress(final int keycode) {
		if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
		} else if (StateUI.getKeysDown().contains(Keys.F) && StateUI.getKeysDown().contains(Keys.SHIFT_LEFT)) {
			if (!fullScreen) {
				Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
				fullScreen = true;
			} else {
				Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, false);
				fullScreen = false;
			}
		}
	}
}