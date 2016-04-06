package com.inhavok.fallen.entities.characters;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.Application;
import com.inhavok.fallen.commands.Data;
import com.inhavok.fallen.commands.entity.GraphicsCommand;
import com.inhavok.fallen.entity_components.EntityComponent;
import com.inhavok.fallen.entity_components.EntityPhysics;
import com.inhavok.fallen.utility.Ray;
import com.inhavok.fallen.entity_components.ai.EntityAI;
import com.inhavok.fallen.entity_components.ai.BehaviourTree;
import com.inhavok.fallen.entity_components.ai.facilitator.TestNode;
import com.inhavok.fallen.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.entity_components.graphics.layers.PlayerLegsLayer;
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
	private final Vector2 enemyPosition = new Vector2();
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
	@Override
	public void update() {
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
	private boolean foundEnemy() {
		final double fov = 130 * MathUtils.degreesToRadians;
		final double rayAngleIncrement = fov / 100;
		for (double i = -fov / 2; i <= fov / 2; i += rayAngleIncrement) {
			final boolean[] foundEnemy = {false};
			EntityPhysics.addRay(new Ray(new Vector2(getX(), getY()), (float) ((MathUtils.degreesToRadians * getGraphicsRotation()) + i + Math.PI / 2)) {
				@Override
				public void collision() {
					if (getHitFixture().getBody().getUserData() != null) {
						enemyPosition.set(getHitFixture().getBody().getPosition());
						foundEnemy[0] = true;
					}
				}
			});
			if (foundEnemy[0]) {
				return true;
			}
		}
		return false;
	}
	private boolean waiting() {
		return waitStopwatch < WAIT_DELAY;
	}
	private void calculateNextPatrolPoint() {
		currentPatrolPoint++;
		if (currentPatrolPoint > patrolPoints.size() - 1) {
			currentPatrolPoint = 0;
		}
		currentPath.addAll(Pathfinder.getPath(getX(), getY(), patrolPoints.get(currentPatrolPoint).getPoint().x, patrolPoints.get(currentPatrolPoint).getPoint().y));
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
		desiredRotation = patrolPoints.get(currentPatrolPoint).getRotation();
		waitStopwatch = 0;
	}
	private void rotate() {
		final Data<Float> data = new Data<Float>();
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				data.a = listener.getRotation();
			}
		});
		final float rotation = data.a;
		if (Math.abs(desiredRotation - rotation) > 7) {
			execute(new GraphicsCommand() {
				@Override
				public void execute(EntityGraphics listener) {
					listener.setRotation(rotation + 360 * Application.SECONDS_PER_STEP * GameMath.calDifferencePolarity(desiredRotation, rotation));
				}
			});
		}
	}
	@Override
	void walkEvent(final float angle, final float greatestComponentLength) {
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				listener.setAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING);
				listener.setAnimationFrameDuration(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.MOVING, calculateFrameDuration(greatestComponentLength));
			}
		});
		desiredRotation = angle - 90;
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