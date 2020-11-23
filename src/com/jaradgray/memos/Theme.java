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
		// TODO Initialize instance variables based on given JSONObject
	}
	
	// Getters
	public String getName() { return mName; }
	public Color getFgColor() { return mFgColor; }
	public Color getBgColor() { return mBgColor; }
}
