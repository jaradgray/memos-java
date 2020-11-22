package com.jaradgray.memos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.jaradgray.observable.MutableObservableObject;

public class SettingsViewModel {
	// Instance variables
	private MutableObservableObject<WindowSettings> mWindowSettings;
	
	
	// Constructor
	public SettingsViewModel() {
		// Get settings file data
		// create path to settings file
		String settingsFilePath = System.getenv("AppData") + File.separator + "Memos" + File.separator + "settings.txt";
		System.out.println("settings file path: " + settingsFilePath);
		File settingsFile = new File(settingsFilePath);
		if (!settingsFile.exists()) {
			// TODO create and initialize new settings file
			System.out.println("TODO create and initialize new settings file");
		}
//		String jsonString = getTextFromFile();
		// Parse data into JSON object
	}
	
	
	// Private methods
	private String getAppDataPath() {
		String result = "";
		result = System.getProperty("user.home");
		return result;
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
