package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveThemeDialog extends JDialog {
	// Constants
	public static final int RESULT_SAVE = 0;
	public static final int RESULT_CANCEL = -1;

	// Instance variables
	private int mDialogResult = -1;
	private String mText;
	
	// Constructor
	public SaveThemeDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		
		// Set this Dialog's layout
		this.setLayout(new BorderLayout());

		// Create GUI layout
		JPanel mainPanel, namePanel, buttonsPanel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
		namePanel.add(new JLabel("Theme name:"));
		JTextField nameTextField = new JTextField();
		namePanel.add(nameTextField);
		
		JLabel errorLabel = new JLabel("Please enter a theme name");
		errorLabel.setForeground(errorLabel.getBackground()); // "hide" error label
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		JButton saveButton = new JButton("Save");
		JButton cancelButton = new JButton("Cancel");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// "Show" error label if text field is empty
				if (nameTextField.getText().equals("")) {
					errorLabel.setForeground(Color.red);
					return;
				}
				// Set members, hide and close this dialog
				mDialogResult = RESULT_SAVE;
				mText = nameTextField.getText();
				SaveThemeDialog.this.setVisible(false);
				SaveThemeDialog.this.dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Set dialog result, hide and close this dialog
				mDialogResult = RESULT_CANCEL;
				SaveThemeDialog.this.setVisible(false);
				SaveThemeDialog.this.dispose();	
			}
		});
		buttonsPanel.add(saveButton);
		buttonsPanel.add(cancelButton);
		
		mainPanel.add(namePanel);
		mainPanel.add(errorLabel);
		mainPanel.add(buttonsPanel);

		// Add components to this JDialog's content pane
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.pack();
	}
	
	// Getters
	public String getText() { return mText; }
	
	// Public methods
	/**
	 * Sets this dialog to visible and returns its result member.
	 * Note: since setting visibility to true on a JDialog is a modal operation,
	 * this method doesn't return until its visibility is set to false.
	 * @return
	 */
	public int showDialog() {
		this.setVisible(true);
		return mDialogResult;
	}
}
