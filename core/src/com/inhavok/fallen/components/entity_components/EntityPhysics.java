package com.inhavok.fallen.components.entity_components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.*;

public final class EntityPhysics extends EntityComponent {
	private static final World world = new World(new Vector2(0, 0), true);
	private final Body body;
	public EntityPhysics(final float width, final float height, final BodyType bodyType) {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		addRectangularFixture(width, height, bodyType);
	}
	public static void step(final float timeStep, final int velocityIterations, final int positionIterations) {
		world.step(timeStep, velocityIterations, positionIterations);
	}
	@Override
	public void handleCommand(Command command) {
		if (command.getMessage() == Message.CHANGE_LINEAR_VELOCITY) {
			changeLinearVelocity(((PhysicsChangeLinearVelocity) command).getNewVelocity());
		} else if (command.getMessage() == Message.GET_X) {
			((PhysicsGetX) command).setData(getX());
		} else if (command.getMessage() == Message.GET_Y) {
			((PhysicsGetY) command).setData(getY());
		} else if (command.getMessage() == Message.GET_ROTATION) {
			((PhysicsGetRotation) command).setData(getRotation());
		} else if (command.getMessage() == Message.GET_LINEAR_VELOCITY) {
			((PhysicsGetLinearVelocity) command).setData(getLinearVelocity());
		} else if (command.getMessage() == Message.SET_X) {
			setX(((PhysicsSetX) command).getX());
		} else if (command.getMessage() == Message.SET_Y) {
			setY(((PhysicsSetY) command).getY());
		} else if (command.getMessage() == Message.SET_ROTATION) {
			setRotation(((PhysicsSetRotation) command).getAngle());
		}
	}
	private void addRectangularFixture(final float width, final float height, BodyType bodyType) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 0;
		if (bodyType == BodyType.StaticBody) {
			final EdgeShape edgeShape = new EdgeShape();
			fixtureDef.shape = edgeShape;
			edgeShape.set(-width / 2, -height / 2, width / 2, -height / 2);
			body.createFixture(fixtureDef);
			edgeShape.set(width / 2, -height / 2, width / 2, height / 2);
			body.createFixture(fixtureDef);
			edgeShape.set(width / 2, height / 2, -width / 2, height / 2);
			body.createFixture(fixtureDef);
			edgeShape.set(-width / 2, height / 2, -width / 2, -height / 2);
			body.createFixture(fixtureDef);
			edgeShape.dispose();
		} else {
			final PolygonShape polygonShape = new PolygonShape();
			final CircleShape circleShape = new CircleShape();
			circleShape.setRadius(width / 2);
			polygonShape.setAsBox(width / 2, height / 2);
			fixtureDef.shape = circleShape;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
		}
	}
	private void changeLinearVelocity(final Vector2 newVelocity) {
		body.applyLinearImpulse(newVelocity.sub(body.getLinearVelocity()).scl(body.getMass()), body.getPosition(), true);
	}
	public static void dispose() {
		world.dispose();
	}
	private float getX() {
		return body.getPosition().x;
	}
	private float getY() {
		return body.getPosition().y;
	}
	private float getRotation() {
		return body.getAngle();
	}
	private Vector2 getLinearVelocity() {
		return body.getLinearVelocity();
	}
	private void setX(final float x) {
		body.setTransform(x, body.getPosition().y, body.getAngle());
	}
	private void setY(final float y) {
		body.setTransform(body.getPosition().x, y, body.getAngle());
	}
	private void setRotation(final float angle) {
		body.setTransform(body.getPosition().x, body.getPosition().y, angle);
	}
	public enum Message {
		CHANGE_LINEAR_VELOCITY, GET_X, GET_Y, GET_ROTATION, GET_LINEAR_VELOCITY, SET_X, SET_Y, SET_ROTATION
	}
}