package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.MathUtils;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.entity_components.EntityComponent;
import com.inhavok.fallen.utility.EntityCanvas;

import java.util.ArrayList;

public class Bullet extends Entity {
	private final float x;
	private final float y;
	private final float rotation;
	private float distance;
	public Bullet(float x, float y, float rotation) {
		super(x, y, rotation);
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}
	@Override
	protected ArrayList<EntityComponent> addComponents() {
		return null;
	}
	@Override
	public void update() {
		distance += Application.SECONDS_PER_STEP;
		final float rotationInRadians = rotation * MathUtils.degreesToRadians;
		EntityCanvas.queueVector(x, y, (float) (distance * Math.cos(rotationInRadians)), (float) (distance * Math.sin(rotationInRadians)));
	}
	@Override
	public float getX() {
		return (float) (distance * Math.cos(rotation));
	}
	@Override
	public float getY() {
		return (float) (distance * Math.sin(rotation));
	}
}