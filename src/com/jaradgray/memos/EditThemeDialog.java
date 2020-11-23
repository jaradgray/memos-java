package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditThemeDialog extends JDialog {
	// Constructor
	public EditThemeDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		// Set this Dialog's layout
		this.setLayout(new BorderLayout());
		
		// Create GUI layout
		JPanel mainPanel, namePanel, fgColorPanel, bgColorPanel, buttonsPanel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
		namePanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		namePanel.add(new JLabel("Theme name:"));
		namePanel.add(new JTextField());
		
		fgColorPanel = new JPanel();
		fgColorPanel.setLayout(new BoxLayout(fgColorPanel, BoxLayout.LINE_AXIS));
		fgColorPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		fgColorPanel.add(new JLabel("Foreground color:"));
		JPanel fgColorPreviewPanel = new JPanel();
		fgColorPreviewPanel.setPreferredSize(new Dimension(100, 24));
		fgColorPreviewPanel.setBackground(Color.red);
		JButton fgColorButton = new JButton("Select color...");
		// TODO handle button clicks
		fgColorPanel.add(fgColorPreviewPanel);
		fgColorPanel.add(fgColorButton);
		
		bgColorPanel = new JPanel();
		bgColorPanel.setLayout(new BoxLayout(bgColorPanel, BoxLayout.LINE_AXIS));
		bgColorPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		bgColorPanel.add(new JLabel("Background color:"));
		JPanel bgColorPreviewPanel = new JPanel();
		bgColorPreviewPanel.setPreferredSize(new Dimension(100, 24));
		bgColorPreviewPanel.setBackground(Color.blue);
		JButton bgColorButton = new JButton("Select color...");
		// TODO handle button clicks
		bgColorPanel.add(bgColorPreviewPanel);
		bgColorPanel.add(bgColorButton);
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		buttonsPanel.add(new JButton("Apply"));
		buttonsPanel.add(new JButton("Apply and save"));
		buttonsPanel.add(new JButton("Cancel"));
		
		mainPanel.add(namePanel);
		mainPanel.add(fgColorPanel);
		mainPanel.add(bgColorPanel);
		mainPanel.add(buttonsPanel);
		
		// Create Swing components
		
		// Add components to this JDialog's content pane
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.pack();
	}
}
