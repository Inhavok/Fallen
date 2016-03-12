package com.inhavok.fallen.commands;

public abstract class Command<T extends CommandListener> {
    private final Class<T> listeningClass;
    protected Command(final Class<T> listeningClass) {
        this.listeningClass = listeningClass;
    }
    public abstract void execute(T listener);
    public final Class<T> getListeningClass() {
        return listeningClass;
    }
}