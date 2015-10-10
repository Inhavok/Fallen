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
        final Iterator<Command> stateCommandIterator = stateCommandQueue.iterator();
        while (stateCommandIterator.hasNext()) {
            final Command command = stateCommandIterator.next();
            for (CommandListener stateCommandListener : stateCommandListeners) {
                if (command.run(stateCommandListener)) {
                    stateCommandIterator.remove();
                    break;
                }
            }
            if (stateCommandQueue.contains(command)) {
                stateCommandIterator.remove();
            }
        }
    }
    private static void executeEntityCommands() {
        final Iterator<Command> entityCommandIterator = entityCommandQueue.iterator();
        while (entityCommandIterator.hasNext()) {
            final Command command = entityCommandIterator.next();
            for (CommandListener entityCommandListener : entityCommandListeners) {
                if (command.run(entityCommandListener)) {
                    entityCommandIterator.remove();
                    break;
                }
            }
            if (entityCommandQueue.contains(command)) {
                entityCommandIterator.remove();
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
    public static <T> T requestData(final Command command) {
        switch (command.getType()) {
            case STATE:
                for (CommandListener stateCommandListener : stateCommandListeners) {
                    if (command.run(stateCommandListener)) {
                        break;
                    }
                }
                break;
            case ENTITY:
                for (CommandListener entityCommandListener : entityCommandListeners) {
                    if (command.run(entityCommandListener)) {
                        break;
                    }
                }
        }
        return (T) command.getData();
    }
    public static void reset() {
        stateCommandQueue.clear();
        entityCommandQueue.clear();
        stateCommandListeners.clear();
        stateCommandQueue.clear();
    }
}