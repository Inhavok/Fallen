package com.inhavok.fallen.commands;

public abstract class Command<T extends CommandListener> {
    private final Class<T> listeningClass;
    public Command(final Class<T> listeningClass) {
        this.listeningClass = listeningClass;
    }
    public final boolean run(final T listener) {
        if (listeningClass.isInstance(listener)) {
            execute(listener);
            return true;
        }
        return false;
    }
    public abstract void execute(final T listener);
}