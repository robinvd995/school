package visualGraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VisualGraph implements Runnable{

	private static final String MAIN_TITLE = "Visual Graph";

	private static final int MAIN_WIDTH = 1200;
	private static final int MAIN_HEIGHT = 900;

	private static final String MAIN_VERSION = "0.0.1";

	private MenuManager menuManager;
	private OptionsManager optionsManager;
	private InputManager inputManager;

	private JFrame frame;
	private GraphBackPanel graphBackPanel;
	private JPanel inputPanel;
	private JPanel logPanel;
	private JTextArea logArea;

	private JTextField[] textField;

	private FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Graph Files", "grph");

	private final GraphBounds graphBounds = new GraphBounds(-20, 10, -10, 10);
	private double graphStepX = 15.0D;
	private double graphStepY = 1.0D;
	private boolean graphBoundsChanged = false;
	
	public VisualGraph(){
		menuManager = new MenuManager(this);
		optionsManager = new OptionsManager(this);
		inputManager = new InputManager(this);
		
		init();

		log("Welcome to Visual Graph v" + MAIN_VERSION + "!");
	}

	@Override
	public void run() {
		System.out.println("Looping");
	}

	private void init(){

		menuManager.initMenu();

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

		int monitorWidth = gd.getDisplayMode().getWidth();
		int monitorHeight = gd.getDisplayMode().getHeight();

		frame = new JFrame();
		frame.setTitle(MAIN_TITLE);
		frame.setBounds((monitorWidth - MAIN_WIDTH) / 2, (monitorHeight - MAIN_HEIGHT) / 2, MAIN_WIDTH, MAIN_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		optionsManager.initOptionsWindow();

		menuManager.addToFrame(frame);

		Border border = BorderFactory.createEtchedBorder();

		graphBackPanel = new GraphBackPanel(this);
		graphBackPanel.setLocation(4, 4);
		graphBackPanel.setSize(800, 600);
		graphBackPanel.setVisible(true);
		graphBackPanel.setBackground(Color.WHITE);
		//graphBackPanel.setBorder(border);
		frame.add(graphBackPanel);

		inputPanel = new JPanel();
		inputPanel.setLocation(808, 4);
		inputPanel.setSize(384, 600);
		inputPanel.setVisible(true);
		inputPanel.setBorder(border);
		inputPanel.setLayout(null);
		frame.add(inputPanel);

		JLabel label = new JLabel("Input");
		label.setLocation(1, 1);
		label.setSize(381, 20);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBackground(Color.lightGray);
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		inputPanel.add(label);

		final int spaceBetween = 28;
		final int graphLines = 20;
		
		textField = new JTextField[graphLines];
		
		for(int i = 0; i < graphLines; i++){
			
			JLabel labelY0 = new JLabel("y" + i + ":");
			labelY0.setLocation(10, 30 + i * spaceBetween);
			labelY0.setSize(40, 24);
			inputPanel.add(labelY0);

			textField[i] = new JTextField();
			textField[i].setLocation(40, 30 + i * spaceBetween);
			textField[i].setSize(240, 25);
			inputPanel.add(textField[i]);
			
			JButton buttonDraw = new JButton("d");
			buttonDraw.setLocation(282, 30 + i * spaceBetween);
			buttonDraw.setSize(48, 24);
			buttonDraw.setActionCommand("draw " + i);
			buttonDraw.addActionListener(inputManager);
			inputPanel.add(buttonDraw);

			JButton buttonClear = new JButton("x");
			buttonClear.setLocation(282 + 48, 30 + i * spaceBetween);
			buttonClear.setSize(48, 24);
			buttonClear.setActionCommand("clear " + i);
			buttonClear.addActionListener(inputManager);
			
			inputPanel.add(buttonClear);
		}

		JLabel logLabel = new JLabel("Log");
		logLabel.setLocation(4, 608);
		logLabel.setSize(new Dimension(140, 21));
		logLabel.setOpaque(true);
		logLabel.setHorizontalAlignment(SwingConstants.CENTER);
		logLabel.setBackground(Color.lightGray);
		logLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.GRAY));
		frame.add(logLabel);

		logPanel = new JPanel();
		logPanel.setLocation(4, 628);
		logPanel.setSize(1188, 210);
		logPanel.setVisible(true);
		logPanel.setBorder(border);
		logPanel.setLayout(new BorderLayout());

		logArea = new JTextArea();
		logArea.setAutoscrolls(true);
		logArea.setEditable(false);
		logArea.setVisible(true);

		JScrollPane scroll = new JScrollPane(logArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVisible(true);

		logPanel.add(scroll);
		
		frame.add(logPanel);
		frame.setVisible(true);
	}

	public void log(String text){
		logArea.append("[" + getTime() + "]: " + text + "\n");
	}

	private String getTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	public void performAction(EnumAction action){
		switch(action){
		case IDLE: break;
		case EXIT:
			exit();
			break;
		case NEW:
			newFile();
			break;
		case OPEN:
			openFile();
			break;
		case SAVE:
			saveFile();
			break;
		case OPTIONS:
			showOptionsMenu();
			break;
		}
	}

	public JFrame getFrame(){
		return frame;
	}

	private void showOptionsMenu(){
		optionsManager.openOptionsWindow();
	}

	private void newFile(){
		String[] options = {"New", "Cancel"};
		if(showOptionPane("New", "Are you sure you want to start a new Graph?", options) == 0){
			//clear draw, reset parameters and variables
		}

	}

	private void openFile(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(fileFilter);
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
		}
	}

	private void saveFile(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(fileFilter);
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
		}
	}

	private void exit(){
		String[] options = {"Exit", "Cancel"};
		if(showOptionPane("Exit", "Are you sure you want to exit?", options) == 0)
			System.exit(0);
	}

	private int showOptionPane(String title, String message, String[] options){
		return JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
	}
	
	public void drawLine(int line){
		log("Drawing line: " + line);
	}
	
	public void clearLine(int line){
		log("Clearing line: " + line);
	}
	
	public double getGraphMinX(){
		return graphBounds.getMinX();
	}
	
	public double getGraphMaxX(){
		return graphBounds.getMaxX();
	}
	
	public double getGraphMinY(){
		return graphBounds.getMinY();
	}
	
	public double getGraphMaxY(){
		return graphBounds.getMaxY();
	}
	
	public double getGraphWidth(){
		return graphBounds.getWidth();
	}
	
	public double getGraphHeight(){
		return graphBounds.getHeight();
	}
	
	public double getGraphStepX(){
		return graphStepX;
	}
	
	public double getGraphStepY(){
		return graphStepY;
	}
	
	public void setGraphBounds(int minX, int maxX, int minY, int maxY){
		graphBounds.setBounds(minX, maxX, minY, maxY);
		graphBoundsChanged = true;
	}

	public boolean hasGraphBoundsChanged(){
		return graphBoundsChanged;
	}
}
