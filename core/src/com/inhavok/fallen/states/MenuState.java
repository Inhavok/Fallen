package com.inhavok.fallen.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.inhavok.fallen.components.state_components.MenuStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;

import java.util.ArrayList;

public final class MenuState extends State {
    @Override
    ArrayList<StateComponent> addComponents() {
        final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
        components.add(new MenuStateUI(this));
        return components;
    }
    @Override
    public void handleKeyPress(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        }
    }
}