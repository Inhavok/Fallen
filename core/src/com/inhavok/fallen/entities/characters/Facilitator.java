package com.inhavok.fallen.entities.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;
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
import com.inhavok.fallen.utility.Level.PatrolPoint;
import com.inhavok.fallen.utility.Pathfinder;

import java.util.ArrayList;
import java.util.Stack;

public final class Facilitator extends BipedalEntity {
	private final ArrayList<PatrolPoint> patrolPoints = new ArrayList<PatrolPoint>();
	private int currentPatrolPoint = 0;
	private static final float WAIT_DELAY = 5;
	private float waitStopwatch = WAIT_DELAY;
	private final Stack<Vector2> currentPath = new Stack<Vector2>();
	private Vector2 currentTarget;
	private static final float TOLERANCE = 0.05f;
	private float desiredRotation;
	public Facilitator(final ArrayList<PatrolPoint> patrolPoints) {
		super(patrolPoints.get(0).getPoint().x, patrolPoints.get(0).getPoint().y, 0, 1);
		currentTarget = patrolPoints.get(0).getPoint();
		this.patrolPoints.addAll(patrolPoints);
		waitAtPatrolPoint();
	}
	@Override
	protected ArrayList<EntityComponent> addComponents() {
		final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
		final EntityGraphics graphics = new PlayerGraphics();
		final EntityPhysics physics = new EntityPhysics(graphics.getWidth() - 0.3f, graphics.getHeight() - 0.3f, BodyDef.BodyType.DynamicBody);
		components.add(graphics);
		components.add(physics);
		components.add(new EntityAI(new BehaviourTree(new TestNode())));
		return components;
	}
	public void update() {
		final Vector2 currentPatrolPoint = patrolPoints.get(this.currentPatrolPoint).getPoint();
		if (GameMath.closeTo(currentPatrolPoint.x, currentPatrolPoint.y, getX(), getY(), TOLERANCE)) {
			if (waiting()) {
				waitStopwatch += Application.SECONDS_PER_STEP;
				if (!waiting()) {
					calculateNextPatrolPoint();
					followTarget();
				}
			} else {
				waitAtPatrolPoint();
			}
		} else {
			followTarget();
		}
		final Vector2 velocity = requestData(new PhysicsGetLinearVelocity(), Vector2.class);
		if (GameMath.calGreatestComponentLength(velocity) < TOLERANCE) {
			execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.IDLE));
		}
		rotate();
	}
	private boolean waiting() {
		return waitStopwatch < WAIT_DELAY;
	}
	private void calculateNextPatrolPoint() {
		currentPatrolPoint++;
		if (currentPatrolPoint > patrolPoints.size() - 1) {
			currentPatrolPoint = 0;
		}
	}
	private void calculatePathPoint() {
		if (currentPath.isEmpty()) {
			currentPath.addAll(Pathfinder.getPath(getX(), getY(), patrolPoints.get(currentPatrolPoint).getPoint().x, patrolPoints.get(currentPatrolPoint).getPoint().y));
		}
		currentTarget = currentPath.pop();
	}
	private void followTarget() {
		final Vector2 walkVelocity = new Vector2(currentTarget.x - getX(), currentTarget.y - getY()).setLength(getBaseSpeed());
		if (GameMath.closeTo(currentTarget.x, currentTarget.y, getX(), getY(), TOLERANCE * 5)) {
			if (currentPath.size() > 0 || GameMath.closeTo(currentTarget.x, currentTarget.y, getX(), getY(), TOLERANCE)) {
				calculatePathPoint();
			} else {
				walkVelocity.scl(Vector2.len(currentTarget.x - getX(), currentTarget.y - getY()) / (TOLERANCE * 5));
			}
		}
		walk(walkVelocity);
	}
	private void waitAtPatrolPoint() {
		walk(Vector2.Zero);
		desiredRotation = patrolPoints.get(currentPatrolPoint).getRotation();
		waitStopwatch = 0;
	}
	private void rotate() {
		final float rotation = requestData(new GraphicsGetRotation(), Float.class);
		if (Math.abs(desiredRotation - rotation) <= TOLERANCE * 5) {
			execute(new GraphicsSetRotation(desiredRotation));
		} else {
			execute(new GraphicsSetRotation(rotation + 5 * GameMath.calDifferencePolarity(desiredRotation, rotation)));
		}
	}
	@Override
	void walkEvent(float angle, float greatestComponentLength) {
		execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING));
		execute(new GraphicsSetAnimationFrameDuration(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING, calculateFrameDuration(greatestComponentLength)));
		desiredRotation = angle - 90;
	}
}