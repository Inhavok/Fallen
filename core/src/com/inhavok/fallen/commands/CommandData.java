package com.inhavok.fallen.commands;

public final class CommandData<T> {
	private T data;
	public void setData(T data) {
		this.data = data;
	}
	public T getData() {
		return data;
	}
}