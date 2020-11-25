package com.jaradgray.memos;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jaradgray.observable.MutableObservableObject;
import com.jaradgray.observable.ObservableObject;

public class SettingsViewModel {
	// Instance variables
	private MutableObservableObject<WindowSettings> mWindowSettings = new MutableObservableObject<>();
	private MutableObservableObject<FontSettings> mFontSettings = new MutableObservableObject<>();
	private MutableObservableObject<ThemeSettings> mThemeSettings = new MutableObservableObject<>();
	private MutableObservableObject<List<Theme>> mAllThemes = new MutableObservableObject<>();
	
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
		
		// TODO move this and mAllThemes to ThemeSettings ?
		// Build allThemes list
		JSONObject obj = new JSONObject(json);
		JSONArray allThemesArr = obj.getJSONArray(ThemeSettings.KEY_ALL_THEMES);
		List<Theme> allThemes = new ArrayList<>();
		for (Object o : allThemesArr) {
			if (o instanceof JSONObject) {
				allThemes.add(new Theme((JSONObject) o));
			}
		}
		mAllThemes.set(allThemes);
	}
	
	
	// Getters
	public ObservableObject<WindowSettings> getWindowSettings() { return mWindowSettings; }
	public ObservableObject<FontSettings> getFontSettings() { return mFontSettings; }
	public ObservableObject<ThemeSettings> getThemeSettings() { return mThemeSettings; }
	public ObservableObject<List<Theme>> getAllThemes() { return mAllThemes; }
	
	
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
	
	public void onFgColorTransientChanged(Color color) {
		// Create new ThemeSettings object with given fg transient color and update member
		ThemeSettings ts = new ThemeSettings(
				color,
				mThemeSettings.get().getBgColorTransient(),
				mThemeSettings.get().getTheme());
		mThemeSettings.set(ts);
	}
	
	public void onBgColorTransientChanged(Color color) {
		// Create new ThemeSettings object with given bg transient color and update member
		ThemeSettings ts = new ThemeSettings(
				mThemeSettings.get().getFgColorTransient(),
				color,
				mThemeSettings.get().getTheme());
		mThemeSettings.set(ts);
	}
	
	public void onThemeSelected(Theme theme) {
		// Update ThemeSettings based on given Theme
		ThemeSettings ts = new ThemeSettings(
				theme.getFgColor(),
				theme.getBgColor(),
				theme);
		mThemeSettings.set(ts);
		
		// Update settings file
		JSONObject obj = ts.toJSONObject();
		SettingsUtils.updateSettingsFile(obj);
	}
	
	public void onThemeSaved(String name) {
		// Create a new Theme object from the given name and the current fg & bg transient colors
		Color fg = mThemeSettings.get().getFgColorTransient();
		Color bg = mThemeSettings.get().getBgColorTransient();
		Theme t = new Theme(name, fg, bg);
		
		// Update settings file
		// add theme to "all_themes" array in settings file
		SettingsUtils.addTheme(t);
		// update affected properties in settings file
		SettingsUtils.setCurrentTheme(t);
		SettingsUtils.setFgColorTransient(fg);
		SettingsUtils.setBgColorTransient(bg);
		
		// Update ThemeSettings to make the newly created Theme be the current Theme
		mThemeSettings.set(new ThemeSettings(fg, bg, t));
		// Update mAllThemes
		mAllThemes.set(SettingsUtils.getAllThemes());
	}
	
	
	// Private methods
}
