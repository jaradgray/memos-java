package com.jaradgray.memos;

import java.awt.Font;
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
	private MutableObservableObject<FontSettings> mFontSettings = new MutableObservableObject<>();
	private MutableObservableObject<ThemeSettings> mThemeSettings = new MutableObservableObject<>();
	
	// Constructor
	public SettingsViewModel() {		
		// Set members based on settings file data, create settings file if it doesn't exist
		File settingsFile = new File(SettingsUtils.SETTINGS_FILE_PATH);
		if (!settingsFile.exists()) {
			SettingsUtils.createDefaultSettingsFile();
		}
		String json = SettingsUtils.getTextFromFile(settingsFile);
		mWindowSettings.set(new WindowSettings(json));
		mFontSettings.set(new FontSettings(json));
		mThemeSettings.set(new ThemeSettings(json));
	}
	
	
	// Getters
	public ObservableObject<WindowSettings> getWindowSettings() { return mWindowSettings; }
	public ObservableObject<FontSettings> getFontSettings() { return mFontSettings; }
	public ObservableObject<ThemeSettings> getThemeSettings() { return mThemeSettings; }
	
	
	// Public methods
	public void onWindowClosing(int x, int y, int width, int height) {
		// Update WindowSettings with new values
		mWindowSettings.set(new WindowSettings(x, y, width, height));
		// Update settings file with new values
		JSONObject obj = mWindowSettings.get().toJSONObject();
		SettingsUtils.updateSettingsFile(obj);
	}
	
	public void onFontSelected(Font font) {
		// Update FontSettings with new Font
		mFontSettings.set(new FontSettings(font));
		// Update settings file with new font values
		JSONObject obj = mFontSettings.get().toJSONObject();
		SettingsUtils.updateSettingsFile(obj);
	}
	
	
	// Private methods
}
