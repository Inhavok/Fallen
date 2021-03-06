package com.inhavok.fallen.entities.characters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.utility.*;
import com.inhavok.fallen.commands.entity.GraphicsCommand;
import com.inhavok.fallen.entity_components.EntityComponent;
import com.inhavok.fallen.entity_components.EntityPhysics;
import com.inhavok.fallen.entity_components.ai.EntityAI;
import com.inhavok.fallen.entity_components.ai.BehaviourTree;
import com.inhavok.fallen.entity_components.ai.facilitator.TestNode;
import com.inhavok.fallen.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.utility.Level.PatrolPoint;

import java.util.ArrayList;
import java.util.Stack;

public final class Facilitator extends BipedalEntity {
	private final Pathfinder pathfinder;
	private final ArrayList<PatrolPoint> patrolPoints = new ArrayList<PatrolPoint>();
	private int currentPatrolPoint = 0;
	private static final float WAIT_DELAY = 5;
	private float waitStopwatch = WAIT_DELAY;
	private final Stack<Vector2> currentPath = new Stack<Vector2>();
	private Vector2 currentTarget;
	private final Vector2 enemyPosition = new Vector2();
	private static final float TOLERANCE = 0.05f;
	private float currentRotation;
	private float desiredRotation;
	public Facilitator(final Pathfinder pathfinder, final ArrayList<PatrolPoint> patrolPoints) {
		super(patrolPoints.get(0).getPoint().x, patrolPoints.get(0).getPoint().y, 0, 1);
		this.pathfinder = pathfinder;
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
	@Override
	public void update() {
		updateData();
		if (foundEnemy()) {
			walk(Vector2.Zero);
			desiredRotation = enemyPosition.sub(getX(), getY()).angle() - 90;
			rotate();
		} else {
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
			rotate();
		}
	}
	private void updateData() {
		final Data<Float> data = new Data<Float>();
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				data.a = listener.getRotation();
			}
		});
		currentRotation = data.a;
	}
	private boolean foundEnemy() {
		final double fov = 120 * MathUtils.degreesToRadians;
		final double rayAngleIncrement = fov / 60;
		final boolean[] foundEnemy = {false};
		for (double i = -fov / 2; i <= fov / 2; i += rayAngleIncrement) {
			EntityPhysics.addRay(new Ray(new Vector2(getX(), getY()), (float) ((currentRotation * MathUtils.degreesToRadians) + i + Math.PI / 2)) {
				@Override
				public void collision() {
					if (getHitFixture().getBody().getUserData() != null) {
						enemyPosition.set(getHitFixture().getBody().getPosition());
						foundEnemy[0] = true;
					}
				}
			});
		}
		return foundEnemy[0];
	}
	private boolean waiting() {
		return waitStopwatch < WAIT_DELAY;
	}
	private void calculateNextPatrolPoint() {
		currentPatrolPoint++;
		if (currentPatrolPoint > patrolPoints.size() - 1) {
			currentPatrolPoint = 0;
		}
		currentPath.addAll(pathfinder.getPath(getX(), getY(), patrolPoints.get(currentPatrolPoint).getPoint().x, patrolPoints.get(currentPatrolPoint).getPoint().y));
	}
	private void followTarget() {
		final Vector2 walkVelocity = new Vector2(currentTarget.x - getX(), currentTarget.y - getY()).setLength(getBaseSpeed());
		if (GameMath.closeTo(currentTarget.x, currentTarget.y, getX(), getY(), TOLERANCE * 5)) {
			if (targetIsPatrolPoint()) {
				walkVelocity.scl(Vector2.len(currentTarget.x - getX(), currentTarget.y - getY()) / (TOLERANCE * 5));
			} else {
				currentTarget = currentPath.pop();
			}
		}
		walk(walkVelocity);
	}
	private boolean targetIsPatrolPoint() {
		return currentPath.size() == 0;
	}
	private void waitAtPatrolPoint() {
		walk(Vector2.Zero);
		desiredRotation = patrolPoints.get(currentPatrolPoint).getRotation() - 90;
		waitStopwatch = 0;
	}
	private void rotate() {
		final float tolerance = 5;
		if (!GameMath.closeTo(currentRotation, desiredRotation, tolerance)) {
			execute(new GraphicsCommand() {
				@Override
				public void execute(EntityGraphics listener) {
					float rotationSpeed = 360 * GameMath.calRotationDirection(currentRotation, desiredRotation) * Application.SECONDS_PER_STEP;
					if (GameMath.closeTo(currentRotation, desiredRotation, tolerance * 5)) {
						rotationSpeed *= GameMath.calSmallestAngleDifference(currentRotation, desiredRotation) / (tolerance * 5);
					}
					listener.setRotation(currentRotation + rotationSpeed);
				}
			});
		}
	}
	@Override
	void walkEvent(final float angleInDegrees, final float greatestComponentLength) {
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				listener.setAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING);
				listener.setAnimationFrameDuration(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING, calculateFrameDuration(greatestComponentLength));
			}
		});
		desiredRotation = angleInDegrees - 90;
	}
	@Override
	void stopWalkEvent() {
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				listener.setAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.IDLE);
			}
		});
	}
}