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
	// Instance variables
	private MutableObservableObject<WindowSettings> mWindowSettings = new MutableObservableObject<>();
	
	
	// Constructor
	public SettingsViewModel() {
		// Get settings file data
		String jsonString = getTextFromFile(getSettingsFile());
		mWindowSettings.set(new WindowSettings(jsonString));
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
	private String getAppDataPath() {
		String result = "";
		result = System.getProperty("user.home");
		return result;
	}
	
	private File getSettingsFile() {
		// Build path string to our settings file
		// create strings for individual path components
		final String appDataPath = System.getenv("AppData");
		final String directoryName = "Memos";
		final String settingsFileName = "settings.json";
		// build compound path strings
		final String memosDirPath = appDataPath + File.separator + directoryName;
		final String settingsFilePath = memosDirPath + File.separator + settingsFileName;
		
		// Create a directory for our app in AppData/Roaming if it doesn't exist
		File memosDir = new File(memosDirPath);
		memosDir.mkdirs();
		
		// Get settings file, creating it if it doesn't exist
		File settingsFile = new File(settingsFilePath);
		// TODO create fresh settings file if there's an error parsing its JSON data
		if (!settingsFile.exists()) {
			// TODO create and initialize new settings file
			// create JSON data the settings file will contain
			String jsonString = new JSONObject()
					.put("window_x", 0)
					.put("window_y", 0)
					.put("window_width", 600)
					.put("window_height", 400)
					.toString();
			System.out.println("jsonString: " + jsonString);
			// TODO write jsonString to settings file
		}
		
		return settingsFile;
	}
	
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
	 * Writes @data to app's local settings file.
	 * @param data
	 */
	private void writeSettingsFile(String data) {
		try {
			// Note: Writes a String to a file creating the file and parent directories if they do not exist
			FileUtils.writeStringToFile(getSettingsFile(), data, "utf-8");
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
		// Get settings file data as a JSONObject
		JSONObject settingsJsonObj = new JSONObject(getTextFromFile(getSettingsFile()));
		// For each key in obj, overwrite settings object's corresponding value with given object's value
		Iterator<String> keys = obj.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			settingsJsonObj.put(key, obj.get(key));
		}
		// Write settingsJsonObj to settings file
		writeSettingsFile(settingsJsonObj.toString());
	}
}
