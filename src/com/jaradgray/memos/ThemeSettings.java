package com.jaradgray.memos;

import java.awt.Color;
import java.util.ArrayList;

import org.json.JSONObject;

public class ThemeSettings {
	// Constants
	// bundled themes
	public static final String BUNDLED_THEME_NAME_0 = "Memos - Light";
	public static final String BUNDLED_THEME_FG_COLOR_0 = "#000000";
	public static final String BUNDLED_THEME_BG_COLOR_0 = "#FFFFFF";
	
	public static final String BUNDLED_THEME_NAME_1 = "Memos - Dark";
	public static final String BUNDLED_THEME_FG_COLOR_1 = "#FFFFFF";
	public static final String BUNDLED_THEME_BG_COLOR_1 = "#000000";
	
	// settings keys
	public static final String KEY_FG_COLOR_TRANSIENT = "fg_color_transient";
	public static final String KEY_BG_COLOR_TRANSIENT = "bg_color_transient";
	public static final String KEY_CURRENT_THEME = "cur_theme";
	
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
		JSONObject curThemeObj = obj.getJSONObject(KEY_CURRENT_THEME);
		
		// Set instance variables based on parsed data
		mFgColorTransient = Color.decode(fgColorString);
		mBgColorTransient = Color.decode(bgColorString);
		mTheme = new Theme(curThemeObj);
		
		// TODO create fresh settings file or construct from defaults if there's an
		//	error parsing the json, or if any key we're interested in doesn't exist
	}
	
	/**
	 * Constructs a new ThemeSettings with default values
	 */
	public ThemeSettings() {
		mTheme = new Theme();
		// TODO might need to instantiate new Color objects instead of referencing mTheme's
		mFgColorTransient = mTheme.getFgColor();
		mBgColorTransient = mTheme.getBgColor();
	}
	
	
	// Getters
	public Color getFgColorTransient() { return mFgColorTransient; }
	public Color getBgColorTransient() { return mBgColorTransient; }
	
	
	// Static methods
	
	/**
	 * Returns a JSONObject containing one key-value pair, whose key is
	 * {@link #KEY_ALL_THEMES} and whose value is an array of JSONObjects
	 * representing all Themes bundled with the app.
	 * @return
	 */
	public static JSONObject getBundledThemesJSONObject() {
		// Build the Collection containing JSONObjects representing all bundled Themes
		ArrayList<JSONObject> themesList = new ArrayList<>();
		JSONObject obj = new JSONObject()
				.put(KEY_THEME_NAME, BUNDLED_THEME_NAME_0)
				.put(KEY_FG_COLOR, BUNDLED_THEME_FG_COLOR_0)
				.put(KEY_BG_COLOR, BUNDLED_THEME_BG_COLOR_0);
		themesList.add(obj);
		obj = new JSONObject()
				.put(KEY_THEME_NAME, BUNDLED_THEME_NAME_1)
				.put(KEY_FG_COLOR, BUNDLED_THEME_FG_COLOR_1)
				.put(KEY_BG_COLOR, BUNDLED_THEME_BG_COLOR_1);
		themesList.add(obj);
		// Return a JSONObject containing all bundled Theme data
		return new JSONObject()
				.put(KEY_ALL_THEMES, themesList);
	}
	
	
	// Public methods
	
	/**
	 * Returns this ThemeSettings object's data represented as a JSONObject
	 * @return
	 */
	public JSONObject toJSONObject() {
		// Convert Color members to hex Strings (e.g. "#FFFFFF")
		int r, g, b;
		// fg
		r = mFgColorTransient.getRed();
		g = mFgColorTransient.getGreen();
		b = mFgColorTransient.getBlue();
		String fgHexString = String.format("#%02X%02X%02X", r, g, b);
		// bg
		r = mBgColorTransient.getRed();
		g = mBgColorTransient.getGreen();
		b = mBgColorTransient.getBlue();
		String bgHexString = String.format("#%02X%02X%02X", r, g, b);
		
		return new JSONObject()
				.put(KEY_CURRENT_THEME, mTheme.toJSONObject())
				.put(KEY_FG_COLOR_TRANSIENT, fgHexString)
				.put(KEY_BG_COLOR_TRANSIENT, bgHexString);
	}
}
