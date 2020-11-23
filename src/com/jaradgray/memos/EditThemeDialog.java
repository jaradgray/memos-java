package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditThemeDialog extends JDialog {
	// Constants
	public static final int RESULT_APPLY = 0;
	public static final int RESULT_APPLY_AND_SAVE = 1;
	public static final int RESULT_CANCEL = -1;
	
	// Instance variables
	private int mDialogResult = -1;
	
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
		fgColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Show a color chooser dialog
				Color c = JColorChooser.showDialog(
						EditThemeDialog.this,
						"Select foreground color",
						fgColorPreviewPanel.getBackground());
				if (c == null) {
					return;
				}
				fgColorPreviewPanel.setBackground(c);
			}
		});
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
		bgColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Show a color chooser dialog				
				Color c = JColorChooser.showDialog(
						EditThemeDialog.this,
						"Select background color",
						bgColorPreviewPanel.getBackground());
				if (c == null) {
					return;
				}
				bgColorPreviewPanel.setBackground(c);
			}
		});
		bgColorPanel.add(bgColorPreviewPanel);
		bgColorPanel.add(bgColorButton);
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
		JButton applyButton = new JButton("Apply");
		JButton applyAndSaveButton = new JButton("Apply and save");
		JButton cancelButton = new JButton("Cancel");
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Set dialog result, hide and close this dialog
				mDialogResult = RESULT_APPLY;
				EditThemeDialog.this.setVisible(false);
				EditThemeDialog.this.dispose();
			}
		});
		applyAndSaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Set dialog result, hide and close this dialog
				mDialogResult = RESULT_APPLY_AND_SAVE;
				EditThemeDialog.this.setVisible(false);
				EditThemeDialog.this.dispose();	
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Set dialog result, hide and close this dialog
				mDialogResult = RESULT_CANCEL;
				EditThemeDialog.this.setVisible(false);
				EditThemeDialog.this.dispose();	
			}
		});
		buttonsPanel.add(applyButton);
		buttonsPanel.add(applyAndSaveButton);
		buttonsPanel.add(cancelButton);
		
		mainPanel.add(namePanel);
		mainPanel.add(fgColorPanel);
		mainPanel.add(bgColorPanel);
		mainPanel.add(buttonsPanel);
		
		// Create Swing components
		
		// Add components to this JDialog's content pane
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.pack();
	}
	
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
