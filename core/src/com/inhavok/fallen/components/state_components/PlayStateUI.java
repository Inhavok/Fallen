package com.inhavok.fallen.components.state_components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.commands.Command;

import java.util.LinkedList;

public final class PlayStateUI extends StateUI {
	private final Label fpsLabel;
	public PlayStateUI() {
		getTable().top().left();
		fpsLabel = new Label("", Assets.getSkin());
		getTable().add(fpsLabel).pad(5);
	}
	@Override
	public void updateState() {
		fpsLabel.setText("Frame rate: " + String.valueOf(Gdx.graphics.getFramesPerSecond()) + "FPS");
	}
}