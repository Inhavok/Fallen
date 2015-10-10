package com.inhavok.fallen.entities;

import com.badlogic.gdx.Gdx;
import com.inhavok.fallen.commands.CommandListener;
import com.inhavok.fallen.commands.CommandManager;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsAnimate;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_graphics.GraphicsSetY;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsSetRotation;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsSetX;
import com.inhavok.fallen.commands.component_commands.entity.entity_physics.PhysicsSetY;
import com.inhavok.fallen.components.entity_components.EntityComponent;

import java.util.ArrayList;

public abstract class Entity implements CommandListener {
	private final ArrayList<EntityComponent> components = new ArrayList<EntityComponent>();
	private Entity(final float x, final float y, final float angle) {
		components.addAll(addComponents());
		CommandManager.add(new GraphicsSetX(x));
		CommandManager.add(new GraphicsSetY(y));
		CommandManager.add(new GraphicsSetRotation(angle));
		CommandManager.add(new PhysicsSetX(x));
		CommandManager.add(new PhysicsSetY(y));
		CommandManager.add(new PhysicsSetRotation(angle));
	}
	abstract ArrayList<EntityComponent> addComponents();
	public void activate() {
		CommandManager.add(this);
		for (EntityComponent component : components) {
			CommandManager.add(component);
		}
	}
	public final void update() {
		CommandManager.add(new GraphicsAnimate(Gdx.graphics.getDeltaTime()));
	}
}