package com.inhavok.fallen.components.state_components;

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
import com.inhavok.fallen.commands.state_commands.HandleKeyPress;
import com.inhavok.fallen.commands.state_commands.HandleKeyRelease;
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
			public boolean keyDown(InputEvent event, int keycode) {
				keysDown.add(keycode);
				Application.stateCommand(new HandleKeyPress(keycode));
				return false;
			}
			public boolean keyUp(InputEvent event, int keycode) {
				keysDown.remove(Integer.valueOf(keycode));
				Application.stateCommand(new HandleKeyRelease(keycode));
				return false;
			}
		});
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void handleCommand(Command command) {
		if (command.getMessage() == Message.SHOW) {
			table.setVisible(true);
		} else if (command.getMessage() == Message.UPDATE_STATE) {
			updateState();
		}
	}
	private void updateState() {
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
	public static void dispose() {
		stage.dispose();
	}
	public static ArrayList<Integer> getKeysDown() {
		return keysDown;
	}
	final Table getTable() {
		return table;
	}
	public enum Message {
		SHOW, UPDATE_STATE
	}
}