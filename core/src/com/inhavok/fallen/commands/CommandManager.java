package com.inhavok.fallen.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public final class CommandManager {
    private static final LinkedList<Command> stateCommandQueue = new LinkedList<Command>();
    private static final LinkedList<Command> entityCommandQueue = new LinkedList<Command>();
    private static final ArrayList<CommandListener> stateCommandListeners = new ArrayList<CommandListener>();
    private static final ArrayList<CommandListener> entityCommandListeners = new ArrayList<CommandListener>();
    private CommandManager() {
    }
    public static void execute() {
        executeStateCommands();
        executeEntityCommands();
    }
    private static void executeStateCommands() {
        final Iterator<Command> stateCommandsIterator = stateCommandQueue.iterator();
        while (stateCommandsIterator.hasNext()) {
            final Command command = stateCommandsIterator.next();
            for (CommandListener stateCommandListener : stateCommandListeners) {
                if (command.run(stateCommandListener)) {
                    stateCommandsIterator.remove();
                    break;
                }
            }
        }
    }
    private static void executeEntityCommands() {
        final Iterator<Command> entityCommandsIterator = entityCommandQueue.iterator();
        while (entityCommandsIterator.hasNext()) {
            final Command command = entityCommandsIterator.next();
            for (CommandListener entityCommandListener : entityCommandListeners) {
                if (command.run(entityCommandListener)) {
                    entityCommandsIterator.remove();
                    break;
                }
            }
        }
    }
    public static void add(final Command command) {
        switch (command.getType()) {
            case STATE:
                stateCommandQueue.addLast(command);
                break;
            case ENTITY:
                entityCommandQueue.addLast(command);
                break;
        }
    }
    public static void add(final CommandListener commandListener) {
        switch (commandListener.getType()) {
            case STATE:
                stateCommandListeners.add(commandListener);
                break;
            case ENTITY:
                entityCommandListeners.add(commandListener);
                break;
        }
    }
    public static void reset() {
        stateCommandQueue.clear();
        entityCommandQueue.clear();
        stateCommandListeners.clear();
        stateCommandQueue.clear();
    }
}