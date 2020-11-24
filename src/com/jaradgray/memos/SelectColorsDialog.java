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
import javax.swing.JTextField;

public class SelectColorsDialog extends JDialog {
	// Constants
	public static final int RESULT_OK = 0;
	public static final int RESULT_CANCEL = -1;
	
	// Instance variables
	private int mDialogResult = -1;
	
	// Constructor
	public SelectColorsDialog(Frame owner, String title, boolean modal, SettingsViewModel vm) {
		super(owner, title, modal);
		
		// Set this Dialog's layout
		this.setLayout(new BorderLayout());
		
		// Create GUI layout
		JPanel mainPanel, fgColorPanel, bgColorPanel, buttonsPanel;
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		fgColorPanel = new JPanel();
		fgColorPanel.setLayout(new BoxLayout(fgColorPanel, BoxLayout.LINE_AXIS));
		fgColorPanel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
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
						SelectColorsDialog.this,
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
		bgColorPanel.setAlignmentX(JComponent.RIGHT_ALIGNMENT);
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
						SelectColorsDialog.this,
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
		
		mainPanel.add(fgColorPanel);
		mainPanel.add(bgColorPanel);
		mainPanel.add(buttonsPanel);
		
		// Create Swing components
		
		// TODO observe ViewModel's data
		vm.getThemeSettings().addObserver(new Observer() {
			@Override
			public void update(Observable arg0, Object o) {
				ThemeSettings ts = (ThemeSettings) o;
				// we're observing the entire ThemeSettings object
				// 	all we need to do is set colors based on transient colors
				fgColorPreviewPanel.setBackground(ts.getFgColorTransient());
				bgColorPreviewPanel.setBackground(ts.getBgColorTransient());
			}
		});
		
		// TODO Initialize View data to vm's state
		ThemeSettings ts = vm.getThemeSettings().get();
		fgColorPreviewPanel.setBackground(ts.getFgColorTransient());
		bgColorPreviewPanel.setBackground(ts.getBgColorTransient());
		
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
