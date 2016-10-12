package visualGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuManager implements ActionListener, ItemListener{

	private final VisualGraph vg;
	
	private JMenuBar menuBar;
	
	private JMenu menuFile;
	private JMenu menuOptions;
	
	private JMenuItem itemNew;
	private JMenuItem itemOpen;
	private JMenuItem itemSave;
	private JMenuItem itemExit;
	
	private JMenuItem itemOptions;
	
	public MenuManager(VisualGraph visualGraph){
		vg = visualGraph;
	}
	
	public void initMenu(){
		menuBar = new JMenuBar();
		
		menuFile = new JMenu("File");
		//menuFile.setMnemonic(KeyEvent.VK_ESCAPE);
		//menuFile.getAccessibleContext().setAccessibleDescription("File Menu");
		menuBar.add(menuFile);
		
		itemNew = new JMenuItem("New", KeyEvent.VK_N);
		itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		itemNew.setActionCommand(EnumAction.NEW.getActionCommand());
		itemNew.addActionListener(this);
		
		itemOpen = new JMenuItem("Open Graph");
		itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		itemOpen.setActionCommand(EnumAction.OPEN.getActionCommand());
		itemOpen.addActionListener(this);
		
		itemSave = new JMenuItem("Save Graph");
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		itemSave.setActionCommand(EnumAction.SAVE.getActionCommand());
		itemSave.addActionListener(this);
		
		itemExit = new JMenuItem("Exit");
		itemExit.setActionCommand(EnumAction.EXIT.getActionCommand());
		itemExit.addActionListener(this);
		
		menuFile.add(itemNew);
		menuFile.addSeparator();
		menuFile.add(itemOpen);
		menuFile.add(itemSave);
		menuFile.addSeparator();
		menuFile.add(itemExit);
		
		menuOptions = new JMenu("Options");
		menuBar.add(menuOptions);
		
		itemOptions = new JMenuItem("Options");
		itemOptions.setActionCommand(EnumAction.OPTIONS.getActionCommand());
		itemOptions.addActionListener(this);
		
		menuOptions.add(itemOptions);
		
		
	}
	
	public void addToFrame(JFrame theFrame){
		theFrame.setJMenuBar(menuBar);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		EnumAction action = EnumAction.getActionFromCommand(e.getActionCommand());
		vg.performAction(action);
	}
}
