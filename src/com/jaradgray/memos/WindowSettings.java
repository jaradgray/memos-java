package com.jaradgray.memos;

public class WindowSettings {
	// Instance variables
	private int mX;
	private int mY;
	private int mWidth;
	private int mHeight;
	
	// Constructor
	public WindowSettings(int x, int y, int width, int height) {
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;
	}
	
	// Getters
	public int getX() { return mX; }
	public int getY() { return mY; }
	public int getWidth() { return mWidth; }
	public int getHeight() { return mHeight; }
}
