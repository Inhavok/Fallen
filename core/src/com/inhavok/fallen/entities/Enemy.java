package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsApplyLinearImpulse;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.utility.Level;
import com.inhavok.fallen.utility.Pathfinder;

import java.util.ArrayList;
import java.util.Stack;

public final class Enemy extends Entity {
	private final ArrayList<Level.PatrolPoint> patrolPoints = new ArrayList<Level.PatrolPoint>();
	private int currentPatrolPoint = 0;
	private float waitStopwatch = 3;
	private final Stack<Vector2> path = new Stack<Vector2>();
	private boolean needPathPoint = false;
	private Vector2 currentTarget;
	private final float tolerance = 0.1f;
	public Enemy(final ArrayList<Level.PatrolPoint> patrolPoints) {
		super(patrolPoints.get(0).getPoint().x, patrolPoints.get(0).getPoint().y, 0);
		currentTarget = patrolPoints.get(0).getPoint();
		this.patrolPoints.addAll(patrolPoints);
	}
	public void update() {
		waitStopwatch += Application.SECONDS_PER_STEP;
		if (needPathPoint) {
			if (!waiting()) {
				calculatePathPoint();
				needPathPoint = false;
			}
		} else if (atTarget()) {
			if (!waiting()) {
				if (path.isEmpty()) {
					waitAtPatrolPoint();
					calculateNextPatrolPoint();
				}
				needPathPoint = true;
			}
		} else {
			moveTowardsTarget();
		}
	}
	private void calculatePathPoint() {
		if (path.isEmpty()) {
			path.addAll(Pathfinder.getPath(getX(), getY(), patrolPoints.get(currentPatrolPoint).getPoint().x, patrolPoints.get(currentPatrolPoint).getPoint().y));
		}
		currentTarget = path.pop();
	}
	private boolean atTarget() {
		return Vector2.dst(currentTarget.x, currentTarget.y, getX(), getY()) <= tolerance;
	}
	private boolean waiting() {
		return waitStopwatch < 3;
	}
	private void waitAtPatrolPoint() {
		execute(new GraphicsSetRotation(patrolPoints.get(currentPatrolPoint).getRotation()));
		waitStopwatch = 0;
	}
	private void calculateNextPatrolPoint() {
		currentPatrolPoint++;
		if (currentPatrolPoint > patrolPoints.size() - 1) {
			currentPatrolPoint = 0;
		}
	}
	private void moveTowardsTarget() {
		final Vector2 impulse = new Vector2();
		if (getX() < currentTarget.x - tolerance) {
			impulse.add(0.5f, 0);
		}
		if (getX() > currentTarget.x + tolerance) {
			impulse.sub(0.5f, 0);
		}
		if (getY() < currentTarget.y - tolerance) {
			impulse.add(0, 0.5f);
		}
		if (getY() > currentTarget.y + tolerance) {
			impulse.sub(0, 0.5f);
		}
		move(impulse);
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