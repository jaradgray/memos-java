package com.jaradgray.memos;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Contains utility methods for IO with the app's local settings file.
 * @author Jarad
 *
 */
public class SettingsUtils {
	// Constants
	public static final String SETTINGS_FILE_PATH = System.getenv("AppData") + File.separator + "Memos" + File.separator + "settings.json";
	
	
	// Public methods
	
	public static void createDefaultSettingsFile() {
		// Create default *Settings objects and get their JSONObject representations
		JSONObject windowSettingsObj = new WindowSettings().toJSONObject();
		JSONObject fontSettingsObj = new FontSettings().toJSONObject();
		JSONObject themeSettingsObj = new ThemeSettings().toJSONObject();
		
		// Get bundled theme data as a JSONObject
		JSONObject bundledThemesObj = ThemeSettings.getBundledThemesJSONObject();
		
		// Merge all settings JSONObjects into a single JSONObject
		JSONObject merged = new JSONObject();
		for (String key : JSONObject.getNames(windowSettingsObj)) {
			merged.put(key, windowSettingsObj.get(key));
		}
		for (String key : JSONObject.getNames(fontSettingsObj)) {
			merged.put(key, fontSettingsObj.get(key));
		}
		for (String key : JSONObject.getNames(themeSettingsObj)) {
			merged.put(key, themeSettingsObj.get(key));
		}
		for (String key : JSONObject.getNames(bundledThemesObj)) {
			merged.put(key, bundledThemesObj.get(key));
		}
		
		// Write merged JSONObject to settings file
		writeSettingsFile(merged.toString());
	}
	
	public static String getTextFromFile(File file) {
		String result = "";
		try {
			result = new String(Files.readAllBytes(file.toPath()));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Given a JSONObject, updates app's local settings file based on @obj.
	 * Only those keys in @obj are overwritten in the settings file.
	 * @param obj
	 */
	public static void updateSettingsFile(JSONObject obj) {
		// Create settings file with default data if it doesn't exist
		File settingsFile = new File(SETTINGS_FILE_PATH);
		if (!settingsFile.exists()) {
			createDefaultSettingsFile();
		}
		// TODO create settings file with default data if its data is corrupted
		
		// Get settings file data as a JSONObject
		JSONObject settingsJsonObj = new JSONObject(getTextFromFile(settingsFile));
		// For each key in obj, overwrite settings object's corresponding value with given object's value
		Iterator<String> keys = obj.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			settingsJsonObj.put(key, obj.get(key));
		}
		// Write settingsJsonObj to settings file
		writeSettingsFile(settingsJsonObj.toString());
	}
	
	/**
	 * Given a Theme, adds its JSON representation to the "all_themes" array in
	 * the app's local settings file
	 * 
	 * Note: assumes settings file exists, is formatted correctly, and contains
	 * 	the "all_themes" key-value pair
	 * @param t
	 */
	public static void addTheme(Theme t) {		
		// Get settings file data
		File settingsFile = new File(SETTINGS_FILE_PATH);
		JSONObject settingsJsonObj = new JSONObject(getTextFromFile(settingsFile));
		// Get the all_themes array
		JSONArray allThemesArr = settingsJsonObj.getJSONArray(ThemeSettings.KEY_ALL_THEMES);
		// Add the given Theme to it
		allThemesArr.put(t.toJSONObject());
		// Call updateSettingsFile, passing the updated array as a JSONObject
		JSONObject allThemesObj = new JSONObject()
				.put(ThemeSettings.KEY_ALL_THEMES, allThemesArr);
		updateSettingsFile(allThemesObj);
	}
	
	/**
	 * Sets the "cur_theme" property in the app's local settings
	 * file to the JSON representation of the given Theme
	 * @param theme
	 */
	public static void setCurrentTheme(Theme theme) {		
		// Set "cur_theme" property
		updateSettingsFile(new JSONObject()
				.put(ThemeSettings.KEY_CURRENT_THEME, theme.toJSONObject()));
	}
	
	/**
	 * Sets the "fg_color_transient" property in the app's local settings
	 * file to the hex String representation of the given Color
	 * @param c
	 */
	public static void setFgColorTransient(Color c) {		
		// Convert Color to hex String (e.g. "#FFFFFF")
		int r, g, b;
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		String colorString = String.format("#%02X%02X%02X", r, g, b);
		
		// Set "fg_color_transient" property
		updateSettingsFile(new JSONObject()
				.put(ThemeSettings.KEY_FG_COLOR_TRANSIENT, colorString));
	}
	
	/**
	 * Sets the "bg_color_transient" property in the app's local settings
	 * file to the hex String representation of the given Color
	 * @param c
	 */
	public static void setBgColorTransient(Color c) {		
		// Convert Color to hex String (e.g. "#FFFFFF")
		int r, g, b;
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		String colorString = String.format("#%02X%02X%02X", r, g, b);
		
		// Set "fg_color_transient" property
		updateSettingsFile(new JSONObject()
				.put(ThemeSettings.KEY_BG_COLOR_TRANSIENT, colorString));
	}
	
	/**
	 * Returns a List<Theme> object representing the "all_themes" array in the
	 * app's local settings file
	 * @return
	 */
	public static List<Theme> getAllThemes() {
		// Get settings file data
		File settingsFile = new File(SETTINGS_FILE_PATH);
		JSONObject settingsJsonObj = new JSONObject(getTextFromFile(settingsFile));
		// Get the all_themes array
		JSONArray allThemesArr = settingsJsonObj.getJSONArray(ThemeSettings.KEY_ALL_THEMES);
		
		// Build the list
		List<Theme> result = new ArrayList<>();
		for (Object o : allThemesArr) {
			if (o instanceof JSONObject) {
				result.add(new Theme((JSONObject) o));
			}
		}
		
		return result;
	}
	
	
	// Private methods
	
	/**
	 * Writes @json to app's local settings file.
	 * @param json
	 */
	private static void writeSettingsFile(String json) {
		try {
			// Note: Writes a String to a file creating the file and parent directories if they do not exist
			FileUtils.writeStringToFile(new File(SETTINGS_FILE_PATH), json, "utf-8");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
