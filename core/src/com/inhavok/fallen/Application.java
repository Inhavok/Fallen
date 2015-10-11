package com.inhavok.fallen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesDraw;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesUpdateState;
import com.inhavok.fallen.commands.component_commands.state.state_ui.UIUpdateState;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.states.PlayState;
import com.inhavok.fallen.states.State;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesInterpolate;

import java.util.ArrayList;

public final class Application extends ApplicationAdapter {
	public static final float SECONDS_PER_FRAME = 1/60f;
	public static final int PIXELS_PER_METER = 32;
	private static SpriteBatch spriteBatch;
	private static final ArrayList<State> states = new ArrayList<State>();
	private static State currentState;
	private static float accumulatedTime;
	@Override
	public void create() {
		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
		Assets.initialise();
		spriteBatch = new SpriteBatch();
		StateUI.initialise(new ScreenViewport(), spriteBatch);
		states.add(new PlayState());
		currentState = states.get(0);
	}
	@Override
	public void render() {
		updateState();
		drawGraphics();
	}
	private static void updateState() {
		accumulatedTime += Gdx.graphics.getDeltaTime();
		while (accumulatedTime >= SECONDS_PER_FRAME) {
			EntityPhysics.step(SECONDS_PER_FRAME, 8, 3);
			currentState.execute(new EntitiesUpdateState());
			currentState.execute(new UIUpdateState());
			accumulatedTime -= SECONDS_PER_FRAME;
		}
		if (accumulatedTime > 0) {
			currentState.execute(new EntitiesInterpolate(accumulatedTime / SECONDS_PER_FRAME));
		}
		StateUI.act();
	}
	private static void drawGraphics() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		currentState.execute(new EntitiesDraw(spriteBatch));
		StateUI.draw();
	}
	public static void stateCommand(final Command command) {
		currentState.handleCommand(command);
	}
	public static void toggleFullscreen() {
		Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, !Gdx.graphics.isFullscreen());
	}
	@Override
	public void resize(int width, int height) {
		StateUI.resize(width, height);
		final float zoomFactor = 0.4f;
		StateEntities.resize(width / (PIXELS_PER_METER * zoomFactor), height / (PIXELS_PER_METER * zoomFactor));
	}
	@Override
	public void dispose() {
		EntityPhysics.dispose();
		StateUI.dispose();
		spriteBatch.dispose();
		Assets.dispose();
	}
}