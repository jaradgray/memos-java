package com.jaradgray.memos;

import org.json.JSONObject;

public class WindowSettings {
	// Constants
	public static final String KEY_WINDOW_X = "window_x";
	public static final String KEY_WINDOW_Y = "window_y";
	public static final String KEY_WINDOW_WIDTH = "window_width";
	public static final String KEY_WINDOW_HEIGHT = "window_height";

	public static final int DEFAULT_WINDOW_X = 0;
	public static final int DEFAULT_WINDOW_Y = 0;
	public static final int DEFAULT_WINDOW_WIDTH = 600;
	public static final int DEFAULT_WINDOW_HEIGHT = 400;
	
	// Instance variables
	private int mWindowX;
	private int mWindowY;
	private int mWindowWidth;
	private int mWindowHeight;
	
	// Constructor
	/**
	 * Construct a WindowSettings object from a JSON string.
	 * @param json
	 */
	public WindowSettings(String json) {
		JSONObject obj = new JSONObject(json);
		// Parse JSONObject
		mWindowX = obj.getInt(KEY_WINDOW_X);
		mWindowY = obj.getInt(KEY_WINDOW_Y);
		mWindowWidth = obj.getInt(KEY_WINDOW_WIDTH);
		mWindowHeight = obj.getInt(KEY_WINDOW_HEIGHT);
		
		// TODO create fresh settings file or construct from defaults if there's an
		//	error parsing the json, or if any key we're interested in doesn't exist
	}
	
	/**
	 * Create a WindowSettings object from the given values.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public WindowSettings(int x, int y, int width, int height) {
		mWindowX = x;
		mWindowY = y;
		mWindowWidth = width;
		mWindowHeight = height;
	}
	
	// Getters
	public int getX() { return mWindowX; }
	public int getY() { return mWindowY; }
	public int getWidth() { return mWindowWidth; }
	public int getHeight() { return mWindowHeight; }
	
	// Public methods
	public JSONObject toJSONObject() {
		return new JSONObject()
				.put(KEY_WINDOW_X, mWindowX)
				.put(KEY_WINDOW_Y, mWindowY)
				.put(KEY_WINDOW_WIDTH, mWindowWidth)
				.put(KEY_WINDOW_HEIGHT, mWindowHeight);
	}
}
