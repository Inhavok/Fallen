package com.inhavok.fallen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.state.EntitiesCommand;
import com.inhavok.fallen.commands.state.UICommand;
import com.inhavok.fallen.entity_components.EntityPhysics;
import com.inhavok.fallen.state_components.StateEntities;
import com.inhavok.fallen.state_components.StateUI;
import com.inhavok.fallen.states.MenuState;
import com.inhavok.fallen.states.PlayState;
import com.inhavok.fallen.states.State;
import com.inhavok.fallen.utility.EntityCanvas;

import java.util.ArrayList;

public final class Application extends ApplicationAdapter {
	public static final float SECONDS_PER_STEP = 1/60f;
	public static final int PIXELS_PER_METER = 32;
	private SpriteBatch spriteBatch;
	private final ArrayList<State> states = new ArrayList<State>();
	private State currentState;
	private static float accumulatedTime;
	@Override
	public void create() {
		Gdx.graphics.setWindowedMode(960, 600);
		Assets.initialise();
		spriteBatch = new SpriteBatch();
		EntityCanvas.initialise();
		StateUI.initialise(this, new ScreenViewport(), spriteBatch);
		states.add(new PlayState());
		states.add(new MenuState());
		currentState = states.get(0);
		currentState.activate();
	}
	@Override
	public void render() {
		updateState();
		drawGraphics();
	}
	private void updateState() {
		accumulatedTime += Gdx.graphics.getRawDeltaTime();
		while (accumulatedTime >= SECONDS_PER_STEP) {
			EntityPhysics.step(SECONDS_PER_STEP, 8, 3);
			currentState.update();
			currentState.execute(new EntitiesCommand() {
				@Override
				public void execute(StateEntities listener) {
					listener.update();
				}
			});
			currentState.execute(new UICommand() {
				@Override
				public void execute(StateUI listener) {
					listener.update();
				}
			});
			accumulatedTime -= SECONDS_PER_STEP;
		}
		if (accumulatedTime > 0) {
			currentState.execute(new EntitiesCommand() {
				@Override
				public void execute(StateEntities listener) {
					listener.interpolate(accumulatedTime / SECONDS_PER_STEP);
				}
			});
		}
		StateUI.act();
	}
	private void drawGraphics() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		currentState.execute(new EntitiesCommand() {
			@Override
			public void execute(StateEntities listener) {
				listener.draw(spriteBatch);
				EntityCanvas.draw(listener.getCamera().combined);
			}
		});
		StateUI.draw();
	}
	public void stateCommand(final Command command) {
		currentState.handleCommand(command);
	}
	@Override
	public void resize(final int width, final int height) {
		StateUI.resize(width, height);
		currentState.execute(new EntitiesCommand() {
			@Override
			public void execute(StateEntities listener) {
				final float newWidth = 10 * (width / (float) height);
				listener.resize(newWidth, (newWidth * height) / (float) width);
			}
		});
	}
	@Override
	public void dispose() {
		EntityPhysics.dispose();
		StateUI.dispose();
		spriteBatch.dispose();
		Assets.dispose();
	}
}