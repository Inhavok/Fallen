package com.inhavok.fallen.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsGetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsGetY;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetAnimation;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetLayerRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsApplyLinearImpulse;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.EntityPhysics;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.components.entity_components.graphics.PlayerGraphics;
import com.inhavok.fallen.components.entity_components.graphics.layers.PlayerLegsLayer;
import com.inhavok.fallen.states.Level;

import java.util.ArrayList;
import java.util.Stack;

public final class Enemy extends Entity {
	private int nextPatrolPoint = 1;
	private Vector2 currentTarget;
	private Stack<Vector2> path = new Stack<Vector2>();
	private final ArrayList<Level.PatrolPoint> patrolPoints = new ArrayList<Level.PatrolPoint>();
	public Enemy(final ArrayList<Level.PatrolPoint> patrolPoints) {
		super(patrolPoints.get(0).getPoint().x, patrolPoints.get(0).getPoint().y, patrolPoints.get(0).getRotation());
		this.patrolPoints.addAll(patrolPoints);
	}
	public void update() {
		if (patrolPoints.size() > 1) {
			if (currentTarget == null) {
				if (path.empty()) {
					path = AI.getPath(requestData(new GraphicsGetX(), Float.class), requestData(new GraphicsGetY(), Float.class), patrolPoints.get(nextPatrolPoint).getPoint());
				} else {
					currentTarget.set(path.pop());
				}
			} else if (currentTarget.sub(requestData(new GraphicsGetX(), Float.class), requestData(new GraphicsGetY(), Float.class)).len() < 0.1) {
				currentTarget = null;
				nextPatrolPoint++;
				if (nextPatrolPoint > patrolPoints.size() - 1) {
					nextPatrolPoint = 0;
				}
			} else {
				move(new Vector2(currentTarget.x - requestData(new GraphicsGetX(), Float.class), currentTarget.y - requestData(new GraphicsGetY(), Float.class)));
			}
		}
	}
	private void move(final Vector2 impulse) {
		if (impulse.len() > 0) {
			execute(new PhysicsApplyLinearImpulse(impulse.x, impulse.y));
			if (impulse.len() == 2) {
				execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.WALKING));
			} else if (impulse.len() == 3.5f) {
				execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.RUNNING));
			} else if (impulse.len() == 0.5f) {
				execute(new GraphicsSetAnimation(PlayerGraphics.Layer.LEGS, PlayerLegsLayer.Animation.CROUCHING));
			}
			execute(new GraphicsSetLayerRotation(PlayerGraphics.Layer.LEGS, impulse.angle() - 90));
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