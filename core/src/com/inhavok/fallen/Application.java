package com.inhavok.fallen;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesDraw;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesUpdate;
import com.inhavok.fallen.commands.component_commands.state.state_ui.UIUpdate;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.state_components.StateEntities;
import com.inhavok.fallen.components.state_components.StateUI;
import com.inhavok.fallen.states.MenuState;
import com.inhavok.fallen.states.PlayState;
import com.inhavok.fallen.states.State;
import com.inhavok.fallen.commands.component_commands.state.state_entities.EntitiesInterpolate;
import com.inhavok.fallen.utility.EntityCanvas;

import java.util.ArrayList;

public final class Application extends ApplicationAdapter {
	public static final float SECONDS_PER_STEP = 1/60f;
	public static final int PIXELS_PER_METER = 16;
	private static final int ZOOM_FACTOR = 4;
	private static SpriteBatch spriteBatch;
	private static final ArrayList<State> states = new ArrayList<State>();
	private static State currentState;
	private static float accumulatedTime;
	@Override
	public void create() {
		//Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		Gdx.graphics.setWindowedMode(960, 600);
		Assets.initialise();
		spriteBatch = new SpriteBatch();
		EntityCanvas.initialise();
		StateUI.initialise(new ScreenViewport(), spriteBatch);
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
	private static void updateState() {
		accumulatedTime += Gdx.graphics.getDeltaTime();
		while (accumulatedTime >= SECONDS_PER_STEP) {
			EntityPhysics.step(SECONDS_PER_STEP, 8, 3);
			currentState.update();
			currentState.execute(new EntitiesUpdate());
			currentState.execute(new UIUpdate());
			accumulatedTime -= SECONDS_PER_STEP;
		}
		if (accumulatedTime > 0) {
			currentState.execute(new EntitiesInterpolate(accumulatedTime / SECONDS_PER_STEP));
		}
		StateUI.act();
	}
	private static void drawGraphics() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		currentState.execute(new EntitiesDraw(spriteBatch));
		EntityCanvas.draw();
		StateUI.draw();
	}
	public static void stateCommand(final Command command) {
		currentState.handleCommand(command);
	}
	public static int getVisibleWidth() {
		return Gdx.graphics.getWidth() / (PIXELS_PER_METER * ZOOM_FACTOR);
	}
	public static int getVisibleHeight() {
		return Gdx.graphics.getHeight() / (PIXELS_PER_METER * ZOOM_FACTOR);
	}
	@Override
	public void resize(int width, int height) {
		StateUI.resize(width, height);
		StateEntities.resize(getVisibleWidth(), getVisibleHeight());
	}
	@Override
	public void dispose() {
		EntityPhysics.dispose();
		StateUI.dispose();
		spriteBatch.dispose();
		Assets.dispose();
	}
}