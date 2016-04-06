package com.inhavok.fallen.entities;


import com.inhavok.fallen.commands.*;
import com.inhavok.fallen.commands.entity.GraphicsCommand;
import com.inhavok.fallen.commands.entity.PhysicsCommand;
import com.inhavok.fallen.entity_components.EntityComponent;
import com.inhavok.fallen.entity_components.EntityPhysics;
import com.inhavok.fallen.entity_components.graphics.EntityGraphics;
import com.inhavok.fallen.utility.Data;

import java.util.ArrayList;

public abstract class Entity {
	private final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
	protected Entity(final float x, final float y, final float angle) {
		if (addComponents() != null) {
			components.addAll(addComponents());
		}
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				listener.setX(x);
				listener.setY(y);
				listener.setRotation(angle);
			}
		});
		execute(new PhysicsCommand() {
			@Override
			public void execute(EntityPhysics listener) {
				listener.setX(x);
				listener.setY(y);
			}
		});
	}
	protected abstract ArrayList<EntityComponent> addComponents();
	public void update() {
	}
	public final <T extends EntityComponent> void execute(final Command<T> command) {
		if (hasComponent(command.getListeningClass())) {
			for (EntityComponent component : components) {
				if (command.getListeningClass().isInstance(component)) {
					component.handleCommand(command);
				}
			}
		}
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
	public float getX() {
		final Data<Float> data = new Data<Float>();
		execute(new PhysicsCommand() {
			@Override
			public void execute(EntityPhysics listener) {
				data.a = listener.getX();
			}
		});
		return data.a;
	}
	public float getY() {
		final Data<Float> data = new Data<Float>();
		execute(new PhysicsCommand() {
			@Override
			public void execute(EntityPhysics listener) {
				data.a = listener.getY();
			}
		});
		return data.a;
	}
	public float getGraphicsRotation() {
		final Data<Float> data = new Data<Float>();
		execute(new GraphicsCommand() {
			@Override
			public void execute(EntityGraphics listener) {
				data.a = listener.getRotation();
			}
		});
		return data.a;
	}
}