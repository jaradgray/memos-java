package com.jaradgray.memos;

import java.io.File;

public class MemoFile {
	private String mFilePath;
	private String mText;
	
	
	// Constructor
	public MemoFile(String path, String text) {
		mFilePath = path;
		mText = text;
	}
	
	// Accessors
	public String getFilePath() { return mFilePath; }
	public void setFilePath(String filePath) { mFilePath = filePath; } 
	public String getText() { return mText; }
	public void setText(String text) { mText = text; }
	
	
	// Public methods
	public String getFileName() {
		String result = "";
		File f = new File(mFilePath);
		result = (f.getName().equals("")) ? "Untitled" : f.getName();
		return result;
	}
}
