package com.jaradgray.memos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.jaradgray.observable.MutableObservableObject;
import com.jaradgray.observable.ObservableObject;

public class SettingsViewModel {
	// Constants
	public static final String SETTINGS_FILE_PATH = System.getenv("AppData") + File.separator + "Memos" + File.separator + "settings.json";
	
	// Instance variables
	private MutableObservableObject<WindowSettings> mWindowSettings = new MutableObservableObject<>();
	
	// Constructor
	public SettingsViewModel() {		
		// Set mWindowSettings member based on settings file data, create settings file if it doesn't exist
		File settingsFile = new File(SETTINGS_FILE_PATH);
		if (!settingsFile.exists()) {
			createDefaultSettingsFile();
		}
		String json = getTextFromFile(settingsFile);
		mWindowSettings.set(new WindowSettings(json));
	}
	
	
	// Getters
	public ObservableObject<WindowSettings> getWindowSettings() { return mWindowSettings; }
	
	
	// Public methods
	public void onWindowClosing(int x, int y, int width, int height) {
		// Update WindowSettings with new values
		mWindowSettings.set(new WindowSettings(x, y, width, height));
		// Update settings file with new values
		JSONObject obj = mWindowSettings.get().toJSONObject();
		updateSettingsFile(obj);
	}
	
	
	// Private methods
	
	private String getTextFromFile(File file) {
		String result = "";
		try {
			result = new String(Files.readAllBytes(file.toPath()));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Writes @json to app's local settings file.
	 * @param json
	 */
	private void writeSettingsFile(String json) {
		try {
			// Note: Writes a String to a file creating the file and parent directories if they do not exist
			FileUtils.writeStringToFile(new File(SETTINGS_FILE_PATH), json, "utf-8");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Given a JSONObject, updates app's local settings file based on @obj.
	 * The given JSONObject can contain a subset of the settings file's key-value pairs.
	 * @param obj
	 */
	private void updateSettingsFile(JSONObject obj) {
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
	
	private void createDefaultSettingsFile() {
		writeSettingsFile(new WindowSettings().toJSONObject().toString());
	}
}
