package com.inhavok.fallen.entity_components.ai;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.entity_components.EntityComponent;

public class EntityAI extends EntityComponent {
	private final BehaviourTree behaviourTree;
	public EntityAI(final BehaviourTree behaviourTree) {
		this.behaviourTree = behaviourTree;
	}
	@Override
	public void handleCommand(final Command command) {
		command.execute(this);
	}
	public void think() {
		behaviourTree.execute();
	}
}