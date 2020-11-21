package com.jaradgray.observable;

public class MutableObservableObject<T> extends ObservableObject<T> {
	public void set(T t) {
		this.t = t;
		this.setChanged();
		this.notifyObservers(t);
	}
}
