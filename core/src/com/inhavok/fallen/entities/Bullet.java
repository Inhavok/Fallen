package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.MathUtils;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.entity_components.EntityComponent;
import com.inhavok.fallen.utility.EntityCanvas;

import java.util.ArrayList;

public class Bullet extends Entity {
	private final int x;
	private final int y;
	private final float angle;
	private float distance;
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
		distance += Application.SECONDS_PER_STEP;
		final float angleInRadians = angle * MathUtils.degreesToRadians;
		EntityCanvas.queueVector(x, y, (float) (distance * Math.cos(angleInRadians)), (float) (distance * Math.sin(angleInRadians)));
	}
}