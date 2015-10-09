package com.inhavok.fallen.components.entity_components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public final class EntityPhysics implements EntityComponent {
	private static final World world = new World(new Vector2(0, 0), true);
	private final Body body;
	public EntityPhysics(final float width, final float height, final BodyType bodyType, final float linearDamping, final float angularDamping) {
		final BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		body = world.createBody(bodyDef);
		body.setLinearDamping(linearDamping);
		body.setAngularDamping(angularDamping);
		addRectangularFixture(width, height);
	}
	public static void step(final float timeStep, final int velocityIterations, final int positionIterations) {
		world.step(timeStep, velocityIterations, positionIterations);
	}
	private void addRectangularFixture(final float width, final float height) {
		final FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1;
		fixtureDef.friction = 0;
		final PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(width / 2, height / 2);
		fixtureDef.shape = polygonShape;
		body.createFixture(fixtureDef);
		polygonShape.dispose();
	}
	public void applyLinearImpulse(final float horizontalImpulse, final float verticalImpulse) {
		body.applyLinearImpulse(horizontalImpulse, verticalImpulse, body.getPosition().x, body.getPosition().y, true);
	}
	public void applyAngularImpulse(final float impulse) {
		body.applyAngularImpulse(impulse, true);
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
	public float getHorizontalVelocity() {
		return body.getLinearVelocity().x;
	}
	public float getVerticalVelocity() {
		return body.getLinearVelocity().y;
	}
	public float getMass() {
		return body.getMass();
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