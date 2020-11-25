package com.jaradgray.memos;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	
	/**
	 * 
	 * @param newText
	 * @return
	 * 			JFileChooser.APPROVE_OPTION if the given String was saved
	 * 			JFileChooser.CANCEL_OPTION otherwise
	 */
	public int saveChanges(String newText) {
		if (newText.equals(mMemo.get().getText())) {
			return JFileChooser.CANCEL_OPTION;
		}
		// If our memo doesn't have a file path, save as instead of save
		if (mMemo.get().getFilePath() == null || mMemo.get().getFilePath().equals("")) {
			return saveChangesAs(newText);
		}
		
		// Write changes to file
		String path = mMemo.get().getFilePath();
		writeTextFile(path, newText);
		
		// Update mMemo
		mMemo.set(new MemoFile(path, newText));
		
		return JFileChooser.APPROVE_OPTION;
	}
	
	/**
	 * Shows a "Save as..." dialog.
	 * If a file is selected, saves newText to selected file and updates Memo member
	 * @param newText
	 * @return
	 * 			JFileChooser.APPROVE_OPTION if newText was saved to a file
	 * 			JFileChooser.CANCEL_OPTION if the user cancelled the "Save as..." dialog
	 */
	public int saveChangesAs(String newText) {		
		// Show a "Save as" dialog, save file
		// create and show a file chooser
		final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
		fc.setFileFilter(filter);
		int result = fc.showSaveDialog(mComponent); 
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			// Prompt for confirmation if selected file exists
			boolean doWrite = true;
			if (file.exists()) {
				// Show a confirmation dialog
				int confirmResult = JOptionPane.showOptionDialog(
						mComponent,
					    file.getName() + " already exists.\nDo you want to replace it?",
			    	    "Confirm Save As",
			    	    JOptionPane.YES_NO_OPTION,
			    	    JOptionPane.WARNING_MESSAGE,
			    	    null,
			    	    new Object[] {"Yes", "No"},
			    	    "No");
				
				// Handle dialog result
				if (confirmResult != JOptionPane.YES_OPTION) {
					doWrite = false;
					result = JFileChooser.CANCEL_OPTION;
				}
				
				// TODO keep FileChooser open during the confirmation process.
				//	code probably won't go here, but somewhere around here
			}
			
			// Proceed with the writing if we should
			if (doWrite) {
				// Write newText to selected file
				writeTextFile(file.getAbsolutePath(), newText);
				// Update mMemo
				mMemo.set(new MemoFile(file.getAbsolutePath(), newText));
			}
		}
		
		return result;
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
