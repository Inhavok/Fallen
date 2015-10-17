package com.inhavok.fallen.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.inhavok.fallen.components.state_components.MenuStateUI;
import com.inhavok.fallen.components.state_components.StateComponent;

import java.util.ArrayList;

/**
 * Created by Pizazzle on 10/17/2015.
 */
public class MenuState extends State {
    @Override
    ArrayList<StateComponent> addComponents() {
        final ArrayList<StateComponent> components = new ArrayList<StateComponent>();
        components.add(new MenuStateUI(this));
        return components;
    }

    @Override
    protected void handleKeyPress(int keycode) {
        if(keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        }
    }

    @Override
    protected void handleKeyRelease(int keycode) {

    }
}
