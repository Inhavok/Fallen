package com.inhavok.fallen.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.components.state_components.PlayStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.components.state_components.StateEntities;

public final class PlayState extends State {
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
			Application.toggleFullscreen();
		}
	}
}