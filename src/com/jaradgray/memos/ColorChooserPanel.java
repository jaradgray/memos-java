package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ColorChooserPanel extends JPanel {
	// Constructor
	public ColorChooserPanel(Color initialColor, String previewText) {
		super(new BorderLayout());
		
		// Build the GUI
		
		// Set up label to use as custom preview panel
		JLabel previewLabel = new JLabel(previewText);
		previewLabel.setForeground(Color.green);
		previewLabel.setBackground(initialColor);
		previewLabel.setOpaque(true); // is this necessary?
		previewLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
		previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		previewLabel.setPreferredSize(new Dimension(10, 50));
		
		// Set up ColorChooser
		JColorChooser colorChooser = new JColorChooser(initialColor);
		colorChooser.setPreviewPanel(new JPanel()); // remove default preview panel
		
		// Add components
		this.add(previewLabel, BorderLayout.CENTER);
		this.add(colorChooser, BorderLayout.PAGE_END);
	}
}
