package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EditThemeDialog extends JDialog {
	// Constructor
	public EditThemeDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		// Set this Dialog's layout
		this.setLayout(new BorderLayout());
		
		// Create GUI layout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		mainPanel.add(new JLabel("label 1"));
		mainPanel.add(new JLabel("label 2"));
		mainPanel.add(new JLabel("label 3"));
		
		// Create Swing components
		
		// Add components to this JDialog's content pane
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.pack();
	}
}
