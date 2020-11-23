package com.jaradgray.memos;

import java.awt.Color;

import org.json.JSONObject;

public class ThemeSettings {
	// Constants
	public static final String KEY_FG_COLOR_TRANSIENT = "fg_color_transient";
	public static final String KEY_BG_COLOR_TRANSIENT = "bg_color_transient";
	public static final String KEY_CURRENT_THEME_NAME = "cur_theme_name";
	
	public static final String KEY_ALL_THEMES = "all_themes";
	public static final String KEY_THEME_NAME = "theme_name";
	public static final String KEY_FG_COLOR = "fg_color";
	public static final String KEY_BG_COLOR = "bg_color";
	
	// Instance variables
	private Color mFgColorTransient;	// current non-persisted fg color
	private Color mBgColorTransient;	// current non-persisted bg color
	private Theme mTheme;				// persisted theme data
	
	// Constructor
	/**
	 * Constructs a new ThemeSettings object based on the given JSON string
	 * @param json
	 */
	public ThemeSettings(String json) {
		JSONObject obj = new JSONObject(json);
		// Parse JSONObject
		String fgColorString = obj.getString(KEY_FG_COLOR_TRANSIENT);
		String bgColorString = obj.getString(KEY_BG_COLOR_TRANSIENT);
		// current theme from all themes object
		JSONObject allThemes = obj.getJSONObject(KEY_ALL_THEMES);
		JSONObject themeObj = allThemes.getJSONObject(KEY_CURRENT_THEME_NAME);
		
		// Set instance variables based on parsed data
		mFgColorTransient = Color.decode(fgColorString);
		mBgColorTransient = Color.decode(bgColorString);
		mTheme = new Theme(themeObj);
		
		// TODO create fresh settings file or construct from defaults if there's an
		//	error parsing the json, or if any key we're interested in doesn't exist
	}
}
