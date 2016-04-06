package com.inhavok.fallen.state_components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.inhavok.fallen.Assets;
import com.inhavok.fallen.states.State;

public class MenuStateUI extends StateUI {
    public MenuStateUI(final State state) {
        super(state);
        getTable().top().center();
        getTable().add(new Label("Fallen Pre-Alpha", Assets.getSkin(), "sub-title"));
        getTable().bottom().left();
        getTable().add(new Label("Menu State", Assets.getSkin(), "sub-title")).pad(5, 10, 5, 10);
    }
}