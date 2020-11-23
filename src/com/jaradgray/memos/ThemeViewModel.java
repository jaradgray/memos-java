package com.jaradgray.memos;

import java.awt.Color;

import com.jaradgray.observable.MutableObservableObject;
import com.jaradgray.observable.ObservableObject;

/**
 * The ThemeViewModel class exposes members that can affect Views that depend
 * on theme settings.
 * 
 * @author Jarad
 *
 */
public class ThemeViewModel {
	// Instance variables
	private MutableObservableObject<Color> mFgColorTransient = new MutableObservableObject<>();
	private MutableObservableObject<Color> mBgColorTransient = new MutableObservableObject<>();
	private MutableObservableObject<Color> mCurrentThemeFgColor = new MutableObservableObject<>();
	private MutableObservableObject<Color> mCurrentThemeBgColor = new MutableObservableObject<>();
	private MutableObservableObject<String> mCurrentThemeName = new MutableObservableObject<>();
	
	// Constructor
	
	// Getters
	public ObservableObject<Color> getFgColorTransient() { return mFgColorTransient; }
	public ObservableObject<Color> getBgColorTransient() { return mBgColorTransient; }
	public ObservableObject<Color> getCurrentThemeFgColor() { return mCurrentThemeFgColor; }
	public ObservableObject<Color> getCurrentThemeBgColor() { return mCurrentThemeBgColor; }
	public ObservableObject<String> getCurrentThemeName() { return mCurrentThemeName; }
}
