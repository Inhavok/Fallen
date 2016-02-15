package com.inhavok.fallen.entities;

import com.inhavok.fallen.Application;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.utility.EntityCanvas;

import java.util.ArrayList;

//TODO implement Bullet
public class Bullet extends Entity {
	private final int x;
	private final int y;
	private final float angle;
	public Bullet(float x, float y, float angle) {
		super(x, y, angle);
		this.x = (int) x;
		this.y = (int) y;
		this.angle = angle;
	}
	@Override
	protected ArrayList<EntityComponent> addComponents() {
		return null;
	}
	@Override
	public void update() {
		EntityCanvas.drawVector(x, y, Application.getVisibleWidth(), Application.getVisibleHeight());
	}
}