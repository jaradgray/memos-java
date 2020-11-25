package com.jaradgray.memos;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class Application {
	public static void main(String[] args) {
		// Make app look like a native Windows app
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// args contains command-line arguments
		
		// Create, initialize, and display the editor window
		EditorWindow window = new EditorWindow("Hello Memos World!");
		window.setVisible(true);
	}
}
