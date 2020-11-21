package com.jaradgray.observable;

import java.util.Observable;

public class ObservableObject<T> extends Observable {
	protected T t;
	
	public T get() { return t; }
}
