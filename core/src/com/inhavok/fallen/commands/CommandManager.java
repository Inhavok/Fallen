package com.inhavok.fallen.commands;

import com.inhavok.fallen.components.state_components.StateComponent;
import com.inhavok.fallen.states.State;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public final class CommandManager {
    private static final LinkedList<Command> commandQueue = new LinkedList<Command>();
    private CommandManager() {
    }
    public static void execute(State currentState) {
        final Iterator<Command> commandIterator = commandQueue.iterator();
        while (commandIterator.hasNext()) {
            final Command command = commandIterator.next();
            if (command.run(currentState)) {
                commandIterator.remove();
            } else {
                final ArrayList<StateComponent> stateComponents = currentState.getComponents();
                for (StateComponent stateComponent : stateComponents) {
                    if (command.run(stateComponent)) {
                        commandIterator.remove();
                        break;
                    }
                }
            }
        }
    }
    public static void add(final Command command) {
        commandQueue.addLast(command);
    }
}