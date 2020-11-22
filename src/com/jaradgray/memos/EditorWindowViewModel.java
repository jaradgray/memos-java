package com.jaradgray.memos;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

import com.jaradgray.observable.MutableObservableObject;
import com.jaradgray.observable.ObservableObject;

public class EditorWindowViewModel {
	// Instance variables
	private Component mComponent;
	private MutableObservableObject<MemoFile> mMemo;
	private MutableObservableObject<Boolean> mIsMemoTextSynced;
	
	
	// Constructor
	public EditorWindowViewModel(Component component) {
		mComponent = component;
		mMemo = new MutableObservableObject<>();
		mMemo.set(new MemoFile("", ""));
		
		mIsMemoTextSynced = new MutableObservableObject<>();
		mIsMemoTextSynced.set(true);
	}
	
	
	// Accessors
	public ObservableObject<MemoFile> getMemo() { return mMemo; }
	public ObservableObject<Boolean> getIsMemoTextSynced() { return mIsMemoTextSynced; }
	
	
	// Public methods
	public void onTextChanged(String newText) {
		boolean isSynced = mMemo.get().getText().equals(newText);
		mIsMemoTextSynced.set(isSynced);
	}
	
	public void saveChanges(String newText) {
		if (newText.equals(mMemo.get().getText())) {
			return;
		}
		// If our memo doesn't have a file path, save as instead of save
		if (mMemo.get().getFilePath() == null || mMemo.get().getFilePath().equals("")) {
			saveChangesAs(newText);
			return;
		}
		
		// Write changes to file
		String path = mMemo.get().getFilePath();
		writeTextFile(path, newText);
		
		// Update mMemo
		mMemo.set(new MemoFile(path, newText));
	}
	
	public void saveChangesAs(String newText) {		
		// Show a "Save as" dialog, save file
		// create and show a file chooser
		final JFileChooser fc = new JFileChooser();
		if (fc.showSaveDialog(mComponent) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			// TODO prompt for confirmation if selected file exists
			
			// Write newText to selected file
			writeTextFile(file.getAbsolutePath(), newText);
			
			// Update mMemo
			mMemo.set(new MemoFile(file.getAbsolutePath(), newText));
		}
	}
	
	
	// Private methods
	
	/**
	 * Writes @text to file at @path, creating the file if it doesn't exist.
	 * @param path
	 * @param text
	 */
	private void writeTextFile(String path, String text) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(new File(path));
			outputStream.println(text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
}
