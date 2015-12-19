package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsApplyLinearImpulse;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsGetLinearVelocity;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.entity_components.ai.EntityAI;
import com.inhavok.fallen.components.entity_components.ai.BehaviourTree;
import com.inhavok.fallen.components.entity_components.ai.facilitator.TestNode;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.utility.GameMath;
import com.inhavok.fallen.utility.Level;
import com.inhavok.fallen.utility.Pathfinder;

import java.util.ArrayList;
import java.util.Stack;

public final class Facilitator extends Entity {
	private final ArrayList<Level.PatrolPoint> patrolPoints = new ArrayList<Level.PatrolPoint>();
	private int currentPatrolPoint = 0;
	private float waitStopwatch = 5;
	private final Stack<Vector2> path = new Stack<Vector2>();
	private Vector2 currentTarget;
	private final float tolerance = 0.1f;
	public Facilitator(final ArrayList<Level.PatrolPoint> patrolPoints) {
		super(patrolPoints.get(0).getPoint().x, patrolPoints.get(0).getPoint().y, 0);
		currentTarget = patrolPoints.get(0).getPoint();
		this.patrolPoints.addAll(patrolPoints);
		waitAtPatrolPoint();
	}
	public void update() {
		final Vector2 referencePatrolPoint = patrolPoints.get(currentPatrolPoint).getPoint();
		if (GameMath.closeTo(referencePatrolPoint.x, referencePatrolPoint.y, getX(), getY(), tolerance)) {
			if (waiting()) {
				waitStopwatch += Application.SECONDS_PER_STEP;
				if (!waiting()) {
					calculateNextPatrolPoint();
					calculatePathPoint();
					move();
				}
			} else {
				waitAtPatrolPoint();
			}
		} else {
			if (GameMath.closeTo(currentTarget.x, currentTarget.y, getX(), getY(), tolerance)) {
				calculatePathPoint();
			}
			move();
		}
		updateGraphics();
	}
	private boolean waiting() {
		return waitStopwatch < 5;
	}
	private void calculateNextPatrolPoint() {
		currentPatrolPoint++;
		if (currentPatrolPoint > patrolPoints.size() - 1) {
			currentPatrolPoint = 0;
		}
	}
	private void calculatePathPoint() {
		if (path.isEmpty()) {
			path.addAll(Pathfinder.getPath(getX(), getY(), patrolPoints.get(currentPatrolPoint).getPoint().x, patrolPoints.get(currentPatrolPoint).getPoint().y));
		}
		currentTarget = path.pop();
	}
	private void waitAtPatrolPoint() {
		execute(new GraphicsSetRotation(patrolPoints.get(currentPatrolPoint).getRotation()));
		waitStopwatch = 0;
	}
	private void move() {
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
		execute(new PhysicsApplyLinearImpulse(impulse.x, impulse.y));
	}
	private void updateGraphics() {
		final Vector2 velocity = requestData(new PhysicsGetLinearVelocity(), Vector2.class);
		if (velocity.len() > 0) {
			execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING));
			execute(new GraphicsSetAnimationFrameDuration(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING, 0.75f / velocity.len()));
			execute(new GraphicsSetRotation(velocity.angle() - 90));
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
		components.add(new EntityAI(new BehaviourTree(new TestNode())));
		return components;
	}
}