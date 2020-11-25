package com.jaradgray.memos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditorWindow extends JFrame {
	// Instance variables
	private JTextArea mTextArea;
	private EditorWindowViewModel mViewModel;
	private SettingsViewModel mSettingsVM;
	
	
	// Constructor
	public EditorWindow(String title) {
		super(title);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// Set layout manager
		setLayout(new BorderLayout());
		
		// Create Swing components
		mTextArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(mTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		mTextArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				// Not fired for plain-text documents
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// Notify VM text has changed
				mViewModel.onTextChanged(mTextArea.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// Notify VM text has changed
				mViewModel.onTextChanged(mTextArea.getText());
			}
			
		});
		
		// Get a ViewModel
		mViewModel = new EditorWindowViewModel(this);
		mSettingsVM = new SettingsViewModel();
		
		// Observe VM's data
		// memo
		mViewModel.getMemo().addObserver(new Observer() {
			@Override
			public void update(Observable arg0, Object arg1) {
				MemoFile m = (MemoFile) arg1;
				// Update view based on the given MemoFile
				// window title
				EditorWindow.this.setTitle(m.getFileName() + " - Memos");
				// text area
				mTextArea.setText(m.getText());
			}
		});
		// text changes
		mViewModel.getIsMemoTextSynced().addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				boolean isTextSynced = (boolean) arg;
				String title = EditorWindow.this.getTitle();
				if (isTextSynced) {
					// Remove preceding "*" if it exists
					if (title.startsWith("*")) {
						EditorWindow.this.setTitle(title.substring(1));
					}
				} else {
					// Add preceding "*" if it doesn't exist
					if (!title.startsWith("*")) {
						EditorWindow.this.setTitle("*" + title);
					}
				}
			}
		});
		// window settings
		mSettingsVM.getWindowSettings().addObserver(new Observer() {
			@Override
			public void update(Observable arg0, Object o) {
				WindowSettings settings = (WindowSettings) o;
				// Set location and size based on settings
				EditorWindow.this.setLocation(settings.getX(), settings.getY());
				EditorWindow.this.setSize(settings.getWidth(), settings.getHeight());
			}
		});
		// font settings
		mSettingsVM.getFontSettings().addObserver(new Observer() {
			@Override
			public void update(Observable arg0, Object o) {
				FontSettings settings = (FontSettings) o;
				// Set mTextArea data based on settings
				mTextArea.setFont(settings.getFont());
			}
		});
		
		// Observe theme data
		mSettingsVM.getThemeSettings().addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				ThemeSettings ts = (ThemeSettings) arg;
				
				// Update text area based on transient fg and bg colors
				mTextArea.setForeground(ts.getFgColorTransient());
				mTextArea.setBackground(ts.getBgColorTransient());
				
				// Note: we don't need to rebuild the menu here to indicate
				//	the current Theme, because the JRadioButtonMenuItem
				//	mechanism indicates the last-clicked item internally
			}
		});
		// the list of all Themes
		mSettingsVM.getAllThemes().addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				// Rebuild and revalidate this window's JMenuBar
				setJMenuBar(buildMenuBar());
				getJMenuBar().revalidate(); // revalidate for dynamic changes
			}
		});
		
		// Initialize component state from VM
		EditorWindow.this.setTitle(mViewModel.getMemo().get().getFileName() + " - Memos");
		mTextArea.setText(mViewModel.getMemo().get().getText());
		// window
		WindowSettings windowSettings = mSettingsVM.getWindowSettings().get();
		EditorWindow.this.setLocation(windowSettings.getX(), windowSettings.getY());
		EditorWindow.this.setSize(windowSettings.getWidth(), windowSettings.getHeight());
		// font
		FontSettings fontSettings = mSettingsVM.getFontSettings().get();
		mTextArea.setFont(fontSettings.getFont());
		// theme transient colors
		mTextArea.setForeground(mSettingsVM.getThemeSettings().get().getFgColorTransient());
		mTextArea.setBackground(mSettingsVM.getThemeSettings().get().getBgColorTransient());
		
		// Listen for window events
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Prompt user if there are unsaved changes
				if (!mViewModel.getIsMemoTextSynced().get()) {
					// Show dialog
					int dialogResult = JOptionPane.showOptionDialog(
							EditorWindow.this,
							"Do you want to save changes to " + mViewModel.getMemo().get().getFileName() + "?",
							"Memos",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.PLAIN_MESSAGE,
							null,
							new Object[] {"Save", "Don't save", "Cancel"},
							"Save");
					
					// Handle dialog result
					switch (dialogResult) {
						case JOptionPane.YES_OPTION:
							save();
							break;
						case JOptionPane.NO_OPTION:
							break;
						case JOptionPane.CANCEL_OPTION:
							return; // don't exit the app
					}
				}
				
				// Give SettingsViewModel current window info
				mSettingsVM.onWindowClosing(
						EditorWindow.this.getX(),
						EditorWindow.this.getY(),
						EditorWindow.this.getWidth(),
						EditorWindow.this.getHeight());
				
				// Close the application manually
				EditorWindow.this.dispose();
				System.exit(0);
			}
		});
		
		// Set JFrame's MenuBar
		setJMenuBar(buildMenuBar());
		
		// Add Swing components to JFrame's content pane
		Container c = getContentPane();
		c.add(scrollPane, BorderLayout.CENTER);
	}
	
	
	// Events
	
	
	
	
	// Private methods
	
	private JMenuBar buildMenuBar() {
		JMenuBar menuBar;
		JMenu fileMenu, editMenu, formatMenu, themeMenu;
		JMenuItem menuItem; // just need one JMenuItem according to the docs
		JRadioButtonMenuItem rbMenuItem;
		
		menuBar = new JMenuBar();
		
		// Build the "File" menu
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
		
		// Build the "Format" menu
		formatMenu = new JMenu("Format");
		formatMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(formatMenu);
		
		menuItem = new JMenuItem("Font...");
		menuItem.setMnemonic(KeyEvent.VK_F);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectFont();
			}
		});
		formatMenu.add(menuItem);
		
		// Build the "Format -> Theme" menu
		themeMenu = new JMenu("Theme");
		themeMenu.setMnemonic(KeyEvent.VK_T);
		formatMenu.add(themeMenu);
		
		// list all bundled or saved themes as radio button items
		String curThemeName = mSettingsVM.getThemeSettings().get().getTheme().getName();
		List<Theme> allThemes = mSettingsVM.getAllThemes().get();
		ButtonGroup group = new ButtonGroup();
		for (Theme t : allThemes) {
			rbMenuItem = new JRadioButtonMenuItem(t.getName());
			// indicate current Theme
			if (t.getName().equals(curThemeName)) {
				rbMenuItem.setSelected(true);
			}
			// listen for clicks
			rbMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					mSettingsVM.onThemeSelected(t);
				}
			});
			group.add(rbMenuItem);
			themeMenu.add(rbMenuItem);
		}
		
		themeMenu.addSeparator();
		menuItem = new JMenuItem("Select Colors...");
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectThemeColors();
			}
		});
		themeMenu.add(menuItem);
		
		menuItem = new JMenuItem("Save Current Theme...");
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveCurrentTheme();
			}
		});
		themeMenu.add(menuItem);
		
		// Return menuBar
		return menuBar;
	}
	
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
	
	private void selectFont() {
		// Show a JFontChooser dialog
		JFontChooser fc = new JFontChooser();
		// set fc's selections based on mTextArea's current settings
		fc.setSelectedFont(mTextArea.getFont());
		if (fc.showDialog(this) == JFontChooser.OK_OPTION) {
			// Notify mSettingsVM of the new font settings
			mSettingsVM.onFontSelected(fc.getSelectedFont());
		}
	}
	
	private void selectThemeColors() {
		Color cachedFgColor = mSettingsVM.getThemeSettings().get().getFgColorTransient();
		Color cachedBgColor = mSettingsVM.getThemeSettings().get().getBgColorTransient();
		SelectColorsDialog scd = new SelectColorsDialog(this, "Select Theme Colors", true, mSettingsVM);
		int result = scd.showDialog();
		switch (result) {
			case SelectColorsDialog.RESULT_OK:
				// "Keep changes" by doing nothing
				break;
			case SelectColorsDialog.RESULT_CANCEL:
				// "Discard changes" by updating SettingVM's transient colors with cached colors
				mSettingsVM.onFgColorTransientChanged(cachedFgColor);
				mSettingsVM.onBgColorTransientChanged(cachedBgColor);
				break;
			default:
				System.err.println("selectThemeColors(): unrecognized dialog result [" + result + "]");
				break;
		}
	}
	
	private void saveCurrentTheme() {
		SaveThemeDialog dialog = new SaveThemeDialog(this, "Save Theme", true);
		if (dialog.showDialog() == SaveThemeDialog.RESULT_SAVE) {			
			// Notify SettingsVM
			mSettingsVM.onThemeSaved(dialog.getText());
		}
	}
}
