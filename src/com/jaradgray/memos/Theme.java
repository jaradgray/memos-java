package com.jaradgray.memos;

import java.awt.Color;

import org.json.JSONObject;

public class Theme {
	// Instance variables
	private String mName;
	private Color mFgColor;
	private Color mBgColor;
	
	// Constructor
	/**
	 * Constructs a Theme object based on the given JSONObject
	 * @param obj
	 */
	public Theme(JSONObject obj) {
		// Initialize instance variables based on given JSONObject
		mName = obj.getString(ThemeSettings.KEY_CURRENT_THEME_NAME);
		String fgString = obj.getString(ThemeSettings.KEY_FG_COLOR);
		String bgString = obj.getString(ThemeSettings.KEY_BG_COLOR);
		
		mFgColor = Color.decode(fgString);
		mBgColor = Color.decode(bgString);
	}
	
	/**
	 * Constructs a Theme object with default values
	 */
	public Theme() {
		// Use bundled Theme "0"'s values as the default values
		mName = ThemeSettings.BUNDLED_THEME_NAME_0;
		mFgColor = Color.decode(ThemeSettings.BUNDLED_THEME_FG_COLOR_0);
		mBgColor = Color.decode(ThemeSettings.BUNDLED_THEME_BG_COLOR_0);		
	}
	
	// Getters
	public String getName() { return mName; }
	public Color getFgColor() { return mFgColor; }
	public Color getBgColor() { return mBgColor; }
}
