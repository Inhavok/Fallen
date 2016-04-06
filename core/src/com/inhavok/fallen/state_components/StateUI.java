package com.inhavok.fallen.state_components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.state.StateCommand;
import com.inhavok.fallen.states.State;

import java.util.ArrayList;

public abstract class StateUI extends StateComponent {
	private static Stage stage;
	private static final ArrayList<Integer> keysDown = new ArrayList<Integer>();
	private final Table table = new Table(Assets.getSkin());
	public StateUI(final State state) {
		super(state);
		table.setFillParent(true);
		table.setVisible(false);
		stage.addActor(table);
	}
	public static void initialise(final Viewport viewport, final SpriteBatch spriteBatch) {
		stage = new Stage(viewport, spriteBatch);
		stage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, final int keycode) {
				keysDown.add(keycode);
				Application.stateCommand(new StateCommand() {
					@Override
					public void execute(State listener) {
						listener.handleKeyPress(keycode);
					}
				});
				return false;
			}
			public boolean keyUp(InputEvent event, int keycode) {
				keysDown.remove(Integer.valueOf(keycode));
				return false;
			}
		});
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void handleCommand(final Command command) {
		command.execute(this);
	}
	public void update() {
	}
	public static void act() {
		stage.act();
	}
	public static void draw() {
		stage.draw();
	}
	public static void resize(final int width, final int height) {
		stage.getViewport().update(width, height);
		keysDown.clear();
	}
	public final void show() {
		table.setVisible(true);
	}
	public final void hide() {
		table.setVisible(false);
	}
	public static void dispose() {
		stage.dispose();
	}
	public static ArrayList<Integer> getKeysDown() {
		return keysDown;
	}
	final Table getTable() {
		return table;
	}
}