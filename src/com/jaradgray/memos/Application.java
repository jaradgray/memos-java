package com.jaradgray.memos;

import javax.swing.JFrame;

public class Application {
	public static void main(String[] args) {
		// args contains command-line arguments
		
		// Create, initialize, and display the editor window
		EditorWindow window = new EditorWindow("Hello Memos World!");
		window.setSize(600, 400);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
