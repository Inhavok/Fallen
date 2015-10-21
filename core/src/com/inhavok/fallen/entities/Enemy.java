package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsApplyLinearImpulse;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetY;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.states.Level;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public final class Enemy extends Entity {
	private int nextPatrolPoint = 1;
	private float waitStopwatch = 0;
	private final int waitDelay = 5;
	private boolean waiting;
	private final LinkedList<Vector2> path = new LinkedList<Vector2>();
	private Vector2 currentTarget = null;
	private final ArrayList<Level.PatrolPoint> patrolPoints = new ArrayList<Level.PatrolPoint>();
	public Enemy(final ArrayList<Level.PatrolPoint> patrolPoints) {
		super(patrolPoints.get(0).getPoint().x, patrolPoints.get(0).getPoint().y, 0);
		this.patrolPoints.addAll(patrolPoints);
	}
	public void update() {
		/*
		final Vector2 currentTarget = patrolPoints.get(nextPatrolPoint).getPoint();
		waitStopwatch += Application.SECONDS_PER_FRAME;
		if (!waiting && Vector2.dst(currentTarget.x, currentTarget.y, requestData(new GraphicsGetX(), Float.class), requestData(new GraphicsGetY(), Float.class)) < 0.1f) {
			execute(new GraphicsSetRotation(patrolPoints.get(nextPatrolPoint).getRotation()));
			nextPatrolPoint++;
			if (nextPatrolPoint > patrolPoints.size() - 1) {
				nextPatrolPoint = 0;
			}
			move(new Vector2());
			waitStopwatch = 0;
			waiting = true;
		} else if (waitStopwatch >= waitDelay) {
			waiting = false;
			final Vector2 impulse = new Vector2();
			if (requestData(new GraphicsGetX(), Float.class) < currentTarget.x - 0.1f) {
				impulse.add(0.5f, 0);
			}
			if (requestData(new GraphicsGetX(), Float.class) > currentTarget.x + 0.1f) {
				impulse.sub(0.5f, 0);
			}
			if (requestData(new GraphicsGetY(), Float.class) < currentTarget.y - 0.1f) {
				impulse.add(0, 0.5f);
			}
			if (requestData(new GraphicsGetY(), Float.class) > currentTarget.y + 0.1f) {
				impulse.sub(0, 0.5f);
			}
			move(impulse);
		}
		*/
		if (patrolPoints.size() > 1) {
			if (currentTarget == null) {
				if (path.isEmpty()) {
					path.addAll(AI.getPath(requestData(new PhysicsGetX(), Float.class), requestData(new PhysicsGetY(), Float.class), patrolPoints.get(nextPatrolPoint).getPoint().x, patrolPoints.get(nextPatrolPoint).getPoint().y));
				}
				currentTarget = path.pollFirst();
			} else if (Vector2.dst(currentTarget.x, currentTarget.y, requestData(new PhysicsGetX(), Float.class), requestData(new PhysicsGetY(), Float.class)) < 0.1f) {
				currentTarget = null;
				if (path.isEmpty()) {
					nextPatrolPoint++;
					if (nextPatrolPoint > patrolPoints.size() - 1) {
						nextPatrolPoint = 0;
					}
				}
			} else {
				final Vector2 impulse = new Vector2();
				if (requestData(new PhysicsGetX(), Float.class) < currentTarget.x - 0.1f) {
					impulse.add(0.5f, 0);
				}
				if (requestData(new PhysicsGetX(), Float.class) > currentTarget.x + 0.1f) {
					impulse.sub(0.5f, 0);
				}
				if (requestData(new PhysicsGetY(), Float.class) < currentTarget.y - 0.1f) {
					impulse.add(0, 0.5f);
				}
				if (requestData(new PhysicsGetY(), Float.class) > currentTarget.y + 0.1f) {
					impulse.sub(0, 0.5f);
				}
				move(impulse);
			}
		}
	}
	private void move(final Vector2 impulse) {
		if (impulse.len() > 0) {
			execute(new PhysicsApplyLinearImpulse(impulse.x, impulse.y));
			execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING));
			execute(new GraphicsSetAnimationFrameDuration(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING, 0.35f / impulse.len()));
			execute(new GraphicsSetRotation(impulse.angle() - 90));
		} else {
			execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.IDLE));
		}
	}
	@Override
	ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		final EntityGraphics graphics = new PlayerGraphics();
		final EntityPhysics physics = new EntityPhysics(graphics.getWidth() - 0.3f, graphics.getHeight() - 0.3f, BodyDef.BodyType.DynamicBody, 50, 0);
		components.add(graphics);
		components.add(physics);
		return components;
	}
}