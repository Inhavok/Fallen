package com.inhavok.fallen.components.entity_components.ai;

import com.inhavok.fallen.commands.Command;
import com.inhavok.fallen.components.entity_components.EntityComponent;

public class AIComponent extends EntityComponent {
	private final BehaviourTree behaviourTree;
	public AIComponent(final BehaviourTree behaviourTree) {
		this.behaviourTree = behaviourTree;
	}
	@Override
	public void handleCommand(final Command command) {
		if (command.getMessage() == Message.THINK) {

		}
	}
	public enum Message {
		THINK
	}
}