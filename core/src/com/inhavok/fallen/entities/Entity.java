package com.inhavok.fallen.entities;

import com.badlogic.gdx.Gdx;
import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.*;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsSetRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsSetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsSetY;
import com.inhavok.fallen.commands.DataRequest;
import com.inhavok.fallen.components.entity_components.EntityComponent;
import com.inhavok.fallen.components.entity_components.graphics.EntityGraphics;

import java.util.ArrayList;

public abstract class Entity {
	private final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
	Entity(final float x, final float y, final float angle) {
		components.addAll(addComponents());
		execute(new GraphicsSetX(x));
		execute(new GraphicsSetY(y));
		execute(new GraphicsSetRotation(angle));
		execute(new PhysicsSetX(x));
		execute(new PhysicsSetY(y));
		execute(new PhysicsSetRotation(angle));
	}
	abstract ArrayList<EntityComponent> addComponents();
	public final <T extends EntityComponent> void execute(Command<T> command) {
		if (hasComponent(command.getListeningClass())) {
			for (EntityComponent component : components) {
				if (command.getListeningClass().isInstance(component)) {
					component.handleCommand(command);
				}
			}
		}
	}
	public final <T extends EntityComponent, S> S requestData(DataRequest<T> dataRequest, Class<S> dataClass) {
		if (hasComponent(dataRequest.getListeningClass())) {
			for (EntityComponent component : components) {
				if (dataRequest.getListeningClass().isInstance(component)) {
					component.handleCommand(dataRequest);
					return dataRequest.getData(dataClass);
				}
			}
		}
		return null;
	}
	public <T extends EntityComponent> boolean hasComponent(Class<T> componentClass) {
		return getComponent(componentClass) != null;
	}
	private <T extends EntityComponent> T getComponent(Class<T> componentClass) {
		for (EntityComponent component : components) {
			if (componentClass.isInstance(component)) {
				return componentClass.cast(component);
			}
		}
		return null;
	}
	public void updateState() {
		execute(new GraphicsAnimate(Gdx.graphics.getDeltaTime()));
	}
	public float getX() {
		return requestData(new GraphicsGetX(), Float.class);
	}
	public float getY() {
		return requestData(new GraphicsGetY(), Float.class);
	}
}