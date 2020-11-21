package com.jaradgray.memos;

public class MemoFile {
	private String mFilePath;
	private String mText;
	
	// Accessors
	public String getFilePath() { return mFilePath; }
	public void setFilePath(String filePath) { mFilePath = filePath; } 
	public String getText() { return mText; }
	public void setText(String text) { mText = text; }
}
