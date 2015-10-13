package com.inhavok.fallen.components.state_components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.states.State;

public final class PlayStateUI extends StateUI {
	public PlayStateUI(final State state) {
		super(state);
		getTable().top().left();
		getTable().add(new Label("Fallen Pre-alpha", Assets.getSkin(), "sub-title")).pad(5, 10, 5, 10);
	}
}