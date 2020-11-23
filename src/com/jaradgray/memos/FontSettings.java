package com.jaradgray.memos;

import java.awt.Font;

import org.json.JSONObject;

public class FontSettings {
	// Constants
	public static final String KEY_FONT_FACE_NAME = "font_face_name";
	public static final String KEY_STYLE = "font_style";
	public static final String KEY_SIZE = "font_size";
	
	public static final String DEFAULT_FONT_FACE_NAME = "Consolas";
	public static final int DEFAULT_STYLE = 0;
	public static final int DEFAULT_SIZE = 12;
	
	// Instance variables
	private Font mFont;
	
	// Constructors
	/** Constructs a FontSettings object with default values */
	public FontSettings() {
		mFont = new Font(DEFAULT_FONT_FACE_NAME, DEFAULT_STYLE, DEFAULT_SIZE);
	}
	
	/**
	 * Constructs a FontSettings object based on given Font
	 * @param font
	 */
	public FontSettings(Font font) {
		mFont = font;
	}
	
	/**
	 * Constructs a FontSettings object based on given JSON String
	 * @param json
	 */
	public FontSettings(String json) {
		JSONObject obj = new JSONObject(json);
		// Parse JSONObject
		String name = obj.getString(KEY_FONT_FACE_NAME);
		int style = obj.getInt(KEY_STYLE);
		int size = obj.getInt(KEY_SIZE);
		
		mFont = new Font(name, style, size);
		
		// TODO create fresh settings file or construct from defaults if there's an
		//	error parsing the json, or if any key we're interested in doesn't exist
	}
	
	// Getters
	public Font getFont() { return mFont; }
	
	// Public methods	
	public JSONObject toJSONObject() {
		return new JSONObject()
				.put(KEY_FONT_FACE_NAME, mFont.getFontName())
				.put(KEY_STYLE, mFont.getStyle())
				.put(KEY_SIZE, mFont.getSize());
	}
}
