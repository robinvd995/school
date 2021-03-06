package rekenmachine;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

public class Main {

	public static int buttonWidth = 80;
	public static int buttonHeight = 80;
	
	private JFrame frame;
	private JMenuBar menuBar;
	
	private JButton[] buttonArray;
	private JLabel label;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
					showPopup();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	private static void showPopup(){
		JFrame popup = new JFrame();
		popup.setBounds(100, 100, 300, 100);
		JLabel label = new JLabel("Hold shift for extra functions!");
		label.setHorizontalAlignment(JLabel.CENTER);
		popup.add(label);
		popup.setVisible(true);
		popup.setLocation(125, 350);
	}
	
	public Main() {
		initialize();
	}

	private void initMenu(){
		menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		menuBar.setVisible(true);
		frame.add(menuBar);
	}
	
	//Initializing Calculator
	private void initialize() {
		
		Calculator calculator = new Calculator(this);
		ActionManager actionManager = new ActionManager(calculator);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		frame.addKeyListener(actionManager);
		frame.setFocusable(true);
		
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Tahoma", Font.PLAIN, 24));
		label.setBackground(Color.WHITE);
		label.setBounds(12, 25, 320, 60);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setOpaque(true);
		frame.getContentPane().add(label);
		
		buttonArray = new JButton[EnumButton.getSize()];
		
		int borderOffsetX = 12;
		int borderOffsetY = 100;
		
		Font buttonFont = new Font("Arial", Font.BOLD, 20);
		for(int i = 0; i < EnumButton.getSize(); i++){
			EnumButton enumButton = EnumButton.getButton(i);
			if(enumButton == null)
				continue;
			
			buttonArray[i] = new JButton(enumButton.getText(false));
			buttonArray[i].setFocusable(false);
			buttonArray[i].setFont(buttonFont);
			buttonArray[i].setActionCommand(enumButton.getActionCommand(false));
			buttonArray[i].setBounds(borderOffsetX + enumButton.getRow() * buttonWidth, borderOffsetY + enumButton.getCollum() * buttonHeight, buttonWidth, buttonHeight);
			buttonArray[i].addActionListener(actionManager);
			frame.getContentPane().add(buttonArray[i]);
		}
		
		setAnsButtonTextColor(Color.red);
		
		initMenu();
	}
	/**
	 * Sets the text on the label in the GUI
	 * 
	 * @param s the new string you want to set the text to
	 */
	public void setLabelText(String s){
		label.setText(s);
	}
	
	/**
	 * Sets the color of the ans button text
	 * @param color
	 */
	public void setAnsButtonTextColor(Color color){
		buttonArray[EnumButton.LAST_AWNSER.ordinal()].setForeground(color);
	}
	
	public void changeButtons(boolean shiftDown){
		for(int i = 0; i < EnumButton.getSize(); i++){
			EnumButton enumButton = EnumButton.getButton(i);
			if(enumButton == null)
				continue;
			
			if(enumButton.hasSecondaryAction()){
				buttonArray[i].setText(enumButton.getText(shiftDown));
				buttonArray[i].setActionCommand(enumButton.getActionCommand(shiftDown));
			}
		}
	}
}
