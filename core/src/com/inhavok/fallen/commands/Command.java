package com.inhavok.fallen.commands;

public abstract class Command<T extends CommandListener> {
    private final Class<T> listeningClass;
    private final CommandFilter commandType;
    protected Command(final Class<T> listeningClass, CommandFilter commandType) {
        this.listeningClass = listeningClass;
        this.commandType = commandType;
    }
    public final boolean run(final T listener) {
        if (listeningClass.isInstance(listener)) {
            execute(listener);
            return true;
        }
        return false;
    }
    protected abstract void execute(final T listener);
    protected <U> U getData() {
        return null;
    }
    public CommandFilter getType() {
        return commandType;
    }
}