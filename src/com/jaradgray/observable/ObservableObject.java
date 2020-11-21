package com.jaradgray.observable;

import java.util.Observable;

public class ObservableObject<T> extends Observable {
	private T t;
	
	public void set(T t) {
		this.t = t;
		this.setChanged();
		this.notifyObservers(t);
	}
	
	public T get() { return t; }
}
