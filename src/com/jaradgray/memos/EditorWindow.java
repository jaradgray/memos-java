package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EditorWindow extends JFrame {
	public EditorWindow(String title) {
		super(title);
		
		// Set layout manager
		setLayout(new BorderLayout());
		
		// Create Swing components
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// menu bar
		JMenuBar menuBar;
		JMenu fileMenu, editMenu, formatMenu;
		JMenuItem menuItem; // just need one JMenuItem according to the docs
		
		menuBar = new JMenuBar();
		
		// build the "File" menu
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
			
		});
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Save As...");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAs();
			}
			
		});
		fileMenu.add(menuItem);
		
		fileMenu.addSeparator();
		
		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
			
		});
		fileMenu.add(menuItem);
		
		// set JFrame's JMenuBar
		setJMenuBar(menuBar);
		
		// Add Swing components to JFrame's content pane
		Container c = getContentPane();
		c.add(scrollPane, BorderLayout.CENTER);
	}
	
	
	// MenuItem action event handlers
	
	private void save() {
		System.out.println("Save MenuItem selected");
	}
	
	private void saveAs() {
		System.out.println("Save As... MenuItem selected");
	}
	
	private void exit() {
		System.out.println("Exit MenuItem selected");
	}
}
