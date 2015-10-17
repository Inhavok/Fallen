package com.inhavok.fallen.components.entity_components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.*;

public final class EntityPhysics extends EntityComponent {
	private static final World world = new World(new Vector2(0, 0), true);
	private final Body body;
	public boolean colliding;
	public EntityPhysics(final float width, final float height, final BodyType bodyType, final float linearDamping, final float angularDamping) {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.fixedRotation = true;
		body = world.createBody(bodyDef);
		body.setLinearDamping(linearDamping);
		body.setAngularDamping(angularDamping);
		addRectangularFixture(width, height, bodyType);

		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if(contact.getFixtureA().getBody() == body) {
					colliding = true;
				}
			}

			@Override
			public void endContact(Contact contact) {
				if(contact.getFixtureA().getBody() == body) {
					colliding = false;
				}
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {}
		});

	}
	public static void step(final float timeStep, final int velocityIterations, final int positionIterations) {
		world.step(timeStep, velocityIterations, positionIterations);
	}
	@Override
	public void handleCommand(Command command) {
		if (command.getMessage() == Message.GET_X) {
			((PhysicsGetX) command).setData(getX());
		} else if (command.getMessage() == Message.GET_Y) {
			((PhysicsGetY) command).setData(getY());
		} else if (command.getMessage() == Message.GET_ROTATION) {
			((PhysicsGetRotation) command).setData(getRotation());
		} else if (command.getMessage() == Message.SET_X) {
			setX(((PhysicsSetX) command).getX());
		} else if (command.getMessage() == Message.SET_Y) {
			setY(((PhysicsSetY) command).getY());
		} else if (command.getMessage() == Message.SET_ROTATION) {
			setRotation(((PhysicsSetRotation) command).getAngle());
		} else if (command.getMessage() == Message.APPLY_LINEAR_IMPULSE) {
			applyLinearImpulse(((PhysicsApplyLinearImpulse) command).getImpulse());
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
	private void applyLinearImpulse(final Vector2 impulse) {
		body.applyLinearImpulse(impulse, body.getPosition(), true);
	}
	public void applyAngularImpulse(final float impulse) {
		body.applyAngularImpulse(impulse, true);
	}
	public static void dispose() {
		world.dispose();
	}
	public static World getWorld() {
		return world;
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
	public float getHorizontalVelocity() {
		return body.getLinearVelocity().x;
	}
	public float getVerticalVelocity() {
		return body.getLinearVelocity().y;
	}
	public float getMass() {
		return body.getMass();
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
		GET_X, GET_Y, GET_ROTATION, SET_X, SET_Y, SET_ROTATION, APPLY_LINEAR_IMPULSE
	}

	public boolean isColliding() {
		return colliding;
	}
}