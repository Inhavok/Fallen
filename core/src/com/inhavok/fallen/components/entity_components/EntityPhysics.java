package com.inhavok.fallen.components.entity_components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.inhavok.fallen.commands.Command;

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
		command.execute(this);
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
	public void changeLinearVelocity(final Vector2 newVelocity) {
		body.applyLinearImpulse(newVelocity.sub(body.getLinearVelocity()).scl(body.getMass()), body.getPosition(), true);
	}
	public static void dispose() {
		world.dispose();
	}
	public float getX() {
		return body.getPosition().x;
	}
	public float getY() {
		return body.getPosition().y;
	}
	public float getRotation() {
		return body.getAngle();
	}
	public Vector2 getLinearVelocity() {
		return body.getLinearVelocity();
	}
	public void setX(final float x) {
		body.setTransform(x, body.getPosition().y, body.getAngle());
	}
	public void setY(final float y) {
		body.setTransform(body.getPosition().x, y, body.getAngle());
	}
	public void setRotation(final float angle) {
		body.setTransform(body.getPosition().x, body.getPosition().y, angle);
	}
}