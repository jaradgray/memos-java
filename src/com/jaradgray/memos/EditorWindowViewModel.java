package com.jaradgray.memos;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;

public class EditorWindowViewModel {
	// Instance variables
	private Component mComponent;
	private MemoFile mMemo;
	
	
	// Constructor
	public EditorWindowViewModel(Component component) {
		mComponent = component;
		mMemo = new MemoFile();
	}
	
	
	// Public methods
	public void saveChanges(String newText) {
		if (newText.equals(mMemo.getText())) {
			return;
		}
		mMemo.setText(newText);
		if (mMemo.getFilePath() == null || mMemo.getFilePath().equals("")) {
			saveChangesAs(newText);
		}
		
		// TODO save changes to existing file
	}
	
	public void saveChangesAs(String newText) {
		// Show a "Save as" dialog, save file
		// create and show a file chooser
		final JFileChooser fc = new JFileChooser();
		if (fc.showSaveDialog(mComponent) == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			// TODO prompt for confirmation if selected file exists
			
			// save file
			PrintWriter outputStream = null;
			try {
				outputStream = new PrintWriter(file);
				outputStream.println(newText);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					outputStream.close();
				}
			}
		}
	}
}
