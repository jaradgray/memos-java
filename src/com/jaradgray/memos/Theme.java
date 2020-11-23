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
	
	// Getters
	public String getName() { return mName; }
	public Color getFgColor() { return mFgColor; }
	public Color getBgColor() { return mBgColor; }
}
