package com.inhavok.fallen.state_components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.states.State;

public final class PlayStateUI extends StateUI {
	private final Label currentFPSLabel = new Label("", Assets.getSkin());
	private final float currentUpdateDelay = 0.2f;
	private float currentUpdateStopwatch = currentUpdateDelay;
	private int highFPS = Integer.MIN_VALUE;
	private final Label highFPSLabel = new Label("", Assets.getSkin());
	private final int highUpdateDelay = 1;
	private float highUpdateStopwatch = highUpdateDelay;
	private int lowFPS = Integer.MAX_VALUE;
	private final Label lowFPSLabel = new Label("", Assets.getSkin());
	private final int lowUpdateDelay = 1;
	private float lowUpdateStopwatch = lowUpdateDelay;
	private final Label deltaFPSLabel = new Label("", Assets.getSkin());
	public PlayStateUI(final State state) {
		super(state);
		getTable().top().left().pad(5);
		getTable().add(new Label("Fallen Pre-alpha", Assets.getSkin(), "title")).row();
		getTable().add(new Label("Frame rate", Assets.getSkin(), "small-sub-title")).left().row();
		getTable().add(currentFPSLabel).left().padLeft(10).row();
		getTable().add(highFPSLabel).left().padLeft(10).row();
		getTable().add(lowFPSLabel).left().padLeft(10).row();
		getTable().add(deltaFPSLabel).left().padLeft(10);
	}
	@Override
	public void update() {
		currentUpdateStopwatch += Application.SECONDS_PER_STEP;
		highUpdateStopwatch += Application.SECONDS_PER_STEP;
		lowUpdateStopwatch += Application.SECONDS_PER_STEP;
		if (currentUpdateStopwatch >= currentUpdateDelay) {
			currentFPSLabel.setText("Current: " + Math.round(1 / Gdx.graphics.getDeltaTime()));
			currentUpdateStopwatch = 0;
		}
		if (Math.round(1 / Gdx.graphics.getDeltaTime()) > highFPS) {
			highFPS = Math.round(1 / Gdx.graphics.getDeltaTime());
		}
		if (highUpdateStopwatch >= highUpdateDelay) {
			highFPSLabel.setText("High: " + highFPS);
			highUpdateStopwatch = 0;
			highFPS = Integer.MIN_VALUE;
		}
		if (Math.round(1 / Gdx.graphics.getDeltaTime()) < lowFPS) {
			lowFPS = Math.round(1 / Gdx.graphics.getDeltaTime());
		}
		if (lowUpdateStopwatch >= lowUpdateDelay) {
			lowFPSLabel.setText("Low: " + lowFPS);
			lowUpdateStopwatch = 0;
			lowFPS = Integer.MAX_VALUE;
		}
		deltaFPSLabel.setText("Delta: " + String.valueOf(highFPS - lowFPS));
	}
}