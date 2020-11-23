package com.jaradgray.memos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
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
		
		// Merge all settings JSONObjects into a single JSONObject
		JSONObject merged = new JSONObject();
		for (String key : JSONObject.getNames(windowSettingsObj)) {
			merged.put(key, windowSettingsObj.get(key));
		}
		for (String key : JSONObject.getNames(fontSettingsObj)) {
			merged.put(key, fontSettingsObj.get(key));
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
