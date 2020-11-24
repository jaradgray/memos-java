package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jaradgray.memos.ColorChooserPanel.ColorChooserListener;

public class SelectColorsDialog extends JDialog {
	// Constants
	public static final int RESULT_OK = 0;
	public static final int RESULT_CANCEL = -1;
	
	// Instance variables
	private int mDialogResult = -1;
	
	// Constructor
	public SelectColorsDialog(Frame owner, String title, boolean modal, Color fgColor, Color bgColor) {
		super(owner, title, modal);
		
		// Set this Dialog's layout
		this.setLayout(new BorderLayout());
		
		// Create GUI layout
		JPanel mainPanel, buttonsPanel;
		ColorChooserPanel fgChooser, bgChooser;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		fgChooser = new ColorChooserPanel(fgColor, "Foreground");
		fgChooser.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		fgChooser.setListener(new ColorChooserListener() {
			@Override
			public void colorChanged(Color c) {
				// TODO Notify SettingsVM
//				vm.onFgColorTransientChanged(c);
				System.out.println("color changed to " + c);
			}
		});

		bgChooser = new ColorChooserPanel(bgColor, "Background");
		bgChooser.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		bgChooser.setListener(new ColorChooserListener() {
			@Override
			public void colorChanged(Color c) {
				// TODO Notify SettingsVM
//				vm.onBgColorTransientChanged(c);
				System.out.println("color changed to " + c);
			}
		});
		
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		buttonsPanel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
		JButton okButton = new JButton("Ok");
		JButton cancelButton = new JButton("Cancel");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Set dialog result, hide and close this dialog
				mDialogResult = RESULT_OK;
				SelectColorsDialog.this.setVisible(false);
				SelectColorsDialog.this.dispose();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Set dialog result, hide and close this dialog
				mDialogResult = RESULT_CANCEL;
				SelectColorsDialog.this.setVisible(false);
				SelectColorsDialog.this.dispose();	
			}
		});
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		
		mainPanel.add(fgChooser);
		mainPanel.add(bgChooser);
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
