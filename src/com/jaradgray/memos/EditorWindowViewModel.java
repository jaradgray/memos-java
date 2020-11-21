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
	
	
	// Constructor
	public EditorWindowViewModel(Component component) {
		mComponent = component;
		mMemo = new MutableObservableObject<>();
		mMemo.set(new MemoFile());
	}
	
	
	// Accessors
	public ObservableObject<MemoFile> getMemo() { return mMemo; }
	
	
	// Public methods
	public void saveChanges(String newText) {
		if (newText.equals(mMemo.get().getText())) {
			return;
		}
		if (mMemo.get().getFilePath() == null || mMemo.get().getFilePath().equals("")) {
			saveChangesAs(newText);
			return;
		}
		
		// Update mMemo
		MemoFile memo = new MemoFile();
		memo.setText(newText);
		mMemo.set(memo);
		
		// TODO save changes to existing file
	}
	
	public void saveChangesAs(String newText) {		
		// Show a "Save as" dialog, save file
		// create and show a file chooser
		final JFileChooser fc = new JFileChooser();
		if (fc.showSaveDialog(mComponent) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			// TODO prompt for confirmation if selected file exists
			
			// Save file
			writeTextFile(file.getAbsolutePath(), newText);
			
			// Update mMemo
			MemoFile memo = new MemoFile();
			memo.setFilePath(file.getAbsolutePath());
			memo.setText(newText);
			mMemo.set(memo);
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
