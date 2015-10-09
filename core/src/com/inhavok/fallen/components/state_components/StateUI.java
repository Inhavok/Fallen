package com.inhavok.fallen.components.state_components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.commands.CommandManager;
import com.inhavok.fallen.commands.state_commands.HandleKeyPress;

import java.util.ArrayList;

public abstract class StateUI extends StateComponent {
	private static Stage stage;
	private static final ArrayList<Integer> keysDown = new ArrayList<Integer>();
	private final Table table = new Table(Assets.getSkin());
	public StateUI() {
		table.setFillParent(true);
		stage.addActor(table);
	}
	public static final void initialise(final Viewport viewport, final SpriteBatch spriteBatch) {
		stage = new Stage(viewport, spriteBatch);
		stage.addListener(new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				keysDown.add(keycode);
				CommandManager.add(new HandleKeyPress(keycode));
				return false;
			}
			public boolean keyUp(InputEvent event, int keycode) {
				keysDown.remove(Integer.valueOf(keycode));
				return false;
			}
		});
		Gdx.input.setInputProcessor(stage);
	}
	public void updateState() {
	}
	public static final void act() {
		stage.act();
	}
	public static final void draw() {
		stage.draw();
	}
	public static final void resize(final int width, final int height) {
		stage.getViewport().update(width, height);
	}
	public static final void dispose() {
		stage.dispose();
	}
	public static ArrayList<Integer> getKeysDown() {
		return keysDown;
	}
	final Table getTable() {
		return table;
	}
}