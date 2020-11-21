package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class EditorWindow extends JFrame {
	// Instance variables
	private JTextArea mTextArea;
	private EditorWindowViewModel mViewModel;
	
	
	// Constructor
	public EditorWindow(String title) {
		super(title);
		
		// Set layout manager
		setLayout(new BorderLayout());
		
		// Create Swing components
		mTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(mTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// menu bar
		JMenuBar menuBar;
		JMenu fileMenu, editMenu, formatMenu;
		JMenuItem menuItem; // just need one JMenuItem according to the docs
		
		menuBar = new JMenuBar();
		
		// build the "File" menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		menuItem = new JMenuItem("Save");
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
			
		});
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Save As...");
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
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
		
		// Get a ViewModel
		mViewModel = new EditorWindowViewModel(this);
		
		// Observe VM's data
		mViewModel.getMemo().addObserver(new Observer() {
			@Override
			public void update(Observable arg0, Object arg1) {
				MemoFile m = (MemoFile) arg1;
				// Update view based on the given MemoFile
				// window title
				EditorWindow.this.setTitle(m.getFileName());
				// text area
				mTextArea.setText(m.getText());
			}
		});
		
		// TODO initialize component state from VM
		
		// Add Swing components to JFrame's content pane
		Container c = getContentPane();
		c.add(scrollPane, BorderLayout.CENTER);
	}
	
	
	// Private methods
	
	private void save() {
		System.out.println("Save MenuItem selected: " + mTextArea.getText());
		mViewModel.saveChanges(mTextArea.getText());
	}
	
	private void saveAs() {
		System.out.println("Save As... MenuItem selected");
		mViewModel.saveChangesAs(mTextArea.getText());
	}
	
	/** Closes this window via WindowEvent, as if the user clicked the "X" */
	private void exit() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
}
