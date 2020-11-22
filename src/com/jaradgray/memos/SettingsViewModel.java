package com.jaradgray.memos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONObject;

import com.jaradgray.observable.MutableObservableObject;

public class SettingsViewModel {
	// Instance variables
	private MutableObservableObject<WindowSettings> mWindowSettings;
	
	
	// Constructor
	public SettingsViewModel() {
		// Get settings file data
		String jsonString = getTextFromFile(getSettingsFile());
		System.out.println("jsonString" + jsonString);
		// Parse data into JSON object
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
}
