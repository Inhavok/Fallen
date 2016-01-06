package com.inhavok.fallen.commands;

public abstract class DataRequest<T extends CommandListener> extends Command<T> {
	protected DataRequest(final Class<T> listeningClass) {
		super(listeningClass);
	}
	public abstract <S> S getData(Class<S> dataClass);
	public abstract <S> void setData(S data);
}