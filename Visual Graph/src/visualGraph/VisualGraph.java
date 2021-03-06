package visualGraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class VisualGraph implements Runnable{

	//Dna Graph:
	//Blue DNA = sin(x) + x * 0.5 + (x * 0.2)^2			Color: 0,50,100		Width: 3
	//Orange DNA = -sin(x) + x * 0.5 + (x * 0.2)^2		Color: 100,50,0		Width: 3
	//Middle DNA = sin(x*15)*sin(x)+x*0.5+(x*0.2)^2		Color: 0,0,0		Width: 2

	private static final String MAIN_TITLE = "Visual Graph";

	private static final int MAIN_WIDTH = 1200;
	private static final int MAIN_HEIGHT = 900;

	private static final String MAIN_VERSION = "0.2.3";

	private static final int MAX_GRAPHS = 6;
	private static final Color[] STANDARD_GRAPH_COLORS = {
			Color.BLUE, Color.RED, Color.GREEN,
			Color.MAGENTA, Color.YELLOW, Color.CYAN
	};
	private static final String STANDARD_GRAPH_NAME = "y";
	private static final double STANDARD_GRAPH_ACCURACY = 1.0D;

	private MenuManager menuManager;
	private OptionsManager optionsManager;
	private InputManager inputManager;
	private VariableManager variableManager;
	private GraphSettingsManager graphSettingsManager;
	private FunctionManager functionManager;

	private JFrame frame;
	private GraphBackPanel graphBackPanel;
	private JPanel inputPanel;
	private JPanel logPanel;
	private JTextArea logArea;

	private GraphFunctionPanel graphFunctionPanel;

	private JLabel[] graphNameLabels;
	private JButton[] functionButtons;
	private JButton[] drawButtons;
	private JButton[] clearButtons;
	
	private GraphPanel[] graphPanels;

	private JTextField[] textField;

	private FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Graph Files", "grph");

	private Graph[] graphs;

	private final GraphBounds graphBounds = new GraphBounds();

	private int currentFunctionDrawn = -1;

	public VisualGraph(){
		menuManager = new MenuManager(this);
		optionsManager = new OptionsManager(this);
		inputManager = new InputManager(this);
		graphSettingsManager = new GraphSettingsManager(this);
		functionManager = new FunctionManager(this);

		variableManager = new VariableManager();
	}

	@Override
	public void run() {
		init();
		log("Welcome to Visual Graph v" + MAIN_VERSION + "!");
	}

	private void setIcon(){
		try{
			URL icon64URL = getClass().getResource("icon64x64.png");
			URL icon48URL = getClass().getResource("icon48x48.png");
			URL icon32URL = getClass().getResource("icon32x32.png");
			URL icon16URL = getClass().getResource("icon16x16.png");
			ImageIcon icon64 = new ImageIcon(icon64URL);
			ImageIcon icon48 = new ImageIcon(icon48URL);
			ImageIcon icon32 = new ImageIcon(icon32URL);
			ImageIcon icon16 = new ImageIcon(icon16URL);
			List<Image> iconList = new ArrayList<Image>();
			iconList.add(icon64.getImage());
			iconList.add(icon48.getImage());
			iconList.add(icon32.getImage());
			iconList.add(icon16.getImage());
			frame.setIconImages(iconList);
		}
		catch(Exception e){
			System.out.println("Error loading icons!");
		}
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

		setIcon();

		optionsManager.initOptionsWindow();

		menuManager.addToFrame(frame);

		Border border = BorderFactory.createEtchedBorder();

		graphBackPanel = new GraphBackPanel(this);
		graphBackPanel.setLocation(4, 4);
		graphBackPanel.setSize(800, 600);
		graphBackPanel.setVisible(true);
		graphBackPanel.setBackground(Color.WHITE);
		graphBackPanel.setLayout(null);
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

		final int spaceBetween = 95;

		textField = new JTextField[MAX_GRAPHS];
		graphs = new Graph[MAX_GRAPHS];
		graphPanels = new GraphPanel[MAX_GRAPHS];
		graphNameLabels = new JLabel[MAX_GRAPHS];
		functionButtons = new JButton[MAX_GRAPHS];
		drawButtons = new JButton[MAX_GRAPHS];
		clearButtons = new JButton[MAX_GRAPHS];

		graphFunctionPanel = new GraphFunctionPanel(this);
		graphFunctionPanel.setLocation(0,0);
		graphFunctionPanel.setSize(800,600);
		graphFunctionPanel.setVisible(true);
		graphFunctionPanel.setOpaque(false);
		graphBackPanel.add(graphFunctionPanel);

		for(int i = 0; i < MAX_GRAPHS; i++){

			graphs[i] = new Graph(STANDARD_GRAPH_NAME + i, STANDARD_GRAPH_COLORS[i], STANDARD_GRAPH_ACCURACY);
			graphs[i].setFormula("");

			graphPanels[i] = new GraphPanel(this);
			graphPanels[i].setGraph(graphs[i]);
			graphPanels[i].setLocation(0,0);
			graphPanels[i].setSize(800,600);
			graphPanels[i].setVisible(true);
			graphPanels[i].setOpaque(false);
			graphBackPanel.add(graphPanels[i]);

			graphNameLabels[i] = new JLabel(graphs[i].getName() + ":");
			graphNameLabels[i].setLocation(20, 30 + i * spaceBetween);
			graphNameLabels[i].setSize(300, 20);
			inputPanel.add(graphNameLabels[i]);

			textField[i] = new JTextField();
			textField[i].setLocation(10, 50 + i * spaceBetween);
			textField[i].setSize(281, 25);
			textField[i].addActionListener(inputManager);
			textField[i].setActionCommand("draw " + i);
			inputPanel.add(textField[i]);

			clearButtons[i] = new JButton("Clear");
			clearButtons[i].setLocation(294, 50 + i * spaceBetween);
			clearButtons[i].setSize(80, 56);
			//clearButtons[i].setLocation(198, 76 + i * spaceBetween);
			//clearButtons[i].setSize(92, 30);
			clearButtons[i].setActionCommand("clear " + i);
			clearButtons[i].addActionListener(inputManager);
			clearButtons[i].setEnabled(true);
			clearButtons[i].setFocusable(false);
			inputPanel.add(clearButtons[i]);

			drawButtons[i] = new JButton("Draw");
			drawButtons[i].setLocation(10, 76 + i * spaceBetween);
			drawButtons[i].setSize(92, 30);
			drawButtons[i].setActionCommand("draw " + i);
			drawButtons[i].addActionListener(inputManager);
			drawButtons[i].setEnabled(true);
			drawButtons[i].setFocusable(false);
			inputPanel.add(drawButtons[i]);

			functionButtons[i] = new JButton("Function");
			functionButtons[i].setLocation(104, 76 + i * spaceBetween);
			functionButtons[i].setSize(92, 30);
			functionButtons[i].setActionCommand("function " + i);
			functionButtons[i].setEnabled(false);
			functionButtons[i].addActionListener(inputManager);
			functionButtons[i].setFocusable(false);
			inputPanel.add(functionButtons[i]);

			JButton buttonSettings = new JButton("Settings");
			buttonSettings.setLocation(198, 76 + i * spaceBetween);
			buttonSettings.setSize(92, 30);
			buttonSettings.setActionCommand("settings " + i);
			buttonSettings.addActionListener(inputManager);
			buttonSettings.setFocusable(false);
			inputPanel.add(buttonSettings);
		}

		graphSettingsManager.initSettingsWindow();
		functionManager.initFunctionWindow();

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
	
	private void clearLog(){
		logArea.setText(null);
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
		case BOUNDS:
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
			clearFunctionPanel();
			graphBounds.setDefault();
			clearLog();
			
			for(int i = 0; i < MAX_GRAPHS; i++){
				setGraphName(i, STANDARD_GRAPH_NAME + i);
				setGraphFormula(i, "", false);
				textField[i].setText("");
				graphs[i].setAccuracy(STANDARD_GRAPH_ACCURACY);
				graphs[i].setColor(STANDARD_GRAPH_COLORS[i]);
				graphs[i].setLineWidth(1);
			}

			graphBackPanel.updateUI();
		}

	}

	private void openFile(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(fileFilter);
		if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			readXML(file);
		}
	}

	private void saveFile(){
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(fileFilter);
		if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			writeXML(file);
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
		String formula = textField[line].getText();
		if(!formula.isEmpty()){
			log("Drawing line: " + graphs[line].getName());
			setGraphFormula(line, formula, true);
		}
		else{
			log("No formula found for " + graphs[line].getName() + "!");
		}
	}

	public void clearLine(int line){
		if(!isActiveGraph(line)){
			log("Nothing to clear for " + graphs[line].getName() + "!");
			return;
		}
		
		if(line == currentFunctionDrawn){
			clearFunctionPanel();
		}else{
			log("Clearing line: " + graphs[line].getName());
			setGraphFormula(line, "", true);
			textField[line].setText("");
		}
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
		return graphBounds.getStepX();
	}

	public double getGraphStepY(){
		return graphBounds.getStepY();
	}

	public void setGraphBounds(double minX, double maxX, double minY, double maxY, double stepX, double stepY){
		if(minX >= maxX){
			log("Error, min X is greater then, or equals to max X!");
		}else if(maxX <= minX){
			log("Error, max X is less then, or equals to min X!");
		}else if(minY >= maxY){
			log("Error, minY is greater then, or equals to max Y!");
		}else if(maxY <= minY){
			log("Error, max Y is less then, or equals to min Y!");
		}else{
			graphBounds.setBounds(minX, maxX, minY, maxY, stepX, stepY);
			graphBackPanel.updateUI();
		}
	}

	public void setGraphFormula(int graph, String formula, boolean update){	
		graphs[graph].setFormula(formula);
		
		if(formula.isEmpty()){
			functionButtons[graph].setEnabled(false);
		}
		else{
			functionButtons[graph].setEnabled(true);
		}
		
		if(update){
			if(graph == currentFunctionDrawn){
				clearFunctionPanel();
			}
			graphPanels[graph].updateUI();
		}
	}

	public VariableManager getVariableManager(){
		return variableManager;
	}

	public String getGraphName(int graph){
		return graphs[graph].getName();
	}

	public void openGraphSettingsMenu(int graph){
		graphSettingsManager.openSettingsWindow(graph);
	}

	public Color getGraphColor(int graph){
		return graphs[graph].getColor();
	}

	public void setGraphName(int graph, String name){
		graphs[graph].setName(name);
		graphNameLabels[graph].setText(name + ":");
		graphNameLabels[graph].updateUI();
	}

	public void setGraphColor(int graph, Color color){
		graphs[graph].setColor(color);
	}

	public void setGraphLineWidth(int graph, int lineWidth){
		graphs[graph].setLineWidth(lineWidth);
	}

	public void markGraphForRedraw(int graph){
		graphPanels[graph].updateUI();
	}

	public int getGraphLineWidth(int graph){
		return graphs[graph].getLineWidth();
	}

	public void readXML(File file){
		try {
			
			clearFunctionPanel();
			clearLog();
			
			//File file = new File("file.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(file);

			doc.getDocumentElement().normalize();

			NodeList graphList = doc.getElementsByTagName("Graph");

			for(int i = 0; i < graphList.getLength(); i++){
				Node node = graphList.item(i);

				if(node.getNodeType() == Node.ELEMENT_NODE){
					Element element = (Element) node;
					int graphID = Integer.parseInt(element.getAttribute("id"));
					String formula = element.getElementsByTagName("Formula").item(0).getTextContent();
					String name = element.getElementsByTagName("Name").item(0).getTextContent();
					double accuracy = Double.parseDouble(element.getElementsByTagName("Accuracy").item(0).getTextContent());
					int width = Integer.parseInt(element.getElementsByTagName("Width").item(0).getTextContent());
					int colorR = Integer.parseInt(element.getElementsByTagName("ColorR").item(0).getTextContent());
					int colorG = Integer.parseInt(element.getElementsByTagName("ColorG").item(0).getTextContent());
					int colorB = Integer.parseInt(element.getElementsByTagName("ColorB").item(0).getTextContent());
					setGraphName(graphID, name);
					graphs[graphID].setAccuracy(accuracy);
					graphs[graphID].setLineWidth(width);
					graphs[graphID].setColor(new Color(colorR, colorG, colorB));
					textField[graphID].setText(formula);
					setGraphFormula(graphID, formula, false);
				}
			}

			Element boundsNode = (Element) doc.getElementsByTagName("Bounds").item(0);
			double minX = Double.parseDouble(boundsNode.getElementsByTagName("minX").item(0).getTextContent());
			double maxX = Double.parseDouble(boundsNode.getElementsByTagName("maxX").item(0).getTextContent());
			double minY = Double.parseDouble(boundsNode.getElementsByTagName("minY").item(0).getTextContent());
			double maxY = Double.parseDouble(boundsNode.getElementsByTagName("maxY").item(0).getTextContent());
			double stepX = Double.parseDouble(boundsNode.getElementsByTagName("stepX").item(0).getTextContent());
			double stepY = Double.parseDouble(boundsNode.getElementsByTagName("stepY").item(0).getTextContent());

			setGraphBounds(minX, maxX, minY, maxY, stepX, stepY);

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void writeXML(File file){

		if(!file.exists())
			file = new File(file.getAbsolutePath() + ".grph");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = dbf.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element mainRootElement = doc.createElement("VisualGraph");
			doc.appendChild(mainRootElement);

			for(int i = 0; i < MAX_GRAPHS; i++){
				mainRootElement.appendChild(getGraphNode(doc, i));
			}

			mainRootElement.appendChild(getBoundsNode(doc));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	private Node getGraphNode(Document doc, int graph){
		Element graphElement = doc.createElement("Graph");
		graphElement.setAttribute("id", Integer.toString(graph));
		graphElement.appendChild(getElement(doc, "Formula", graphs[graph].getFormula()));
		graphElement.appendChild(getElement(doc, "Name", graphs[graph].getName()));
		graphElement.appendChild(getElement(doc, "Accuracy", Double.toString(graphs[graph].getAccuracy())));
		graphElement.appendChild(getElement(doc, "Width", Integer.toString(graphs[graph].getLineWidth())));
		graphElement.appendChild(getElement(doc, "ColorR", Integer.toString(graphs[graph].getColor().getRed())));
		graphElement.appendChild(getElement(doc, "ColorG", Integer.toString(graphs[graph].getColor().getGreen())));
		graphElement.appendChild(getElement(doc, "ColorB", Integer.toString(graphs[graph].getColor().getBlue())));
		return graphElement;
	}

	private Node getBoundsNode(Document doc){
		Element boundsElement = doc.createElement("Bounds");
		boundsElement.appendChild(getElement(doc, "minX", Double.toString(graphBounds.getMinX())));
		boundsElement.appendChild(getElement(doc, "maxX", Double.toString(graphBounds.getMaxX())));
		boundsElement.appendChild(getElement(doc, "minY", Double.toString(graphBounds.getMinY())));
		boundsElement.appendChild(getElement(doc, "maxY", Double.toString(graphBounds.getMaxY())));
		boundsElement.appendChild(getElement(doc, "stepX", Double.toString(graphBounds.getStepX())));
		boundsElement.appendChild(getElement(doc, "stepY", Double.toString(graphBounds.getStepY())));
		return boundsElement;
	}

	private Node getElement(Document doc, String name, String value){
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	public void openFunctionWindow(int graph){
		functionManager.openFunctionWindow(graph);
	}

	public double calculateGraphPoint(int graph, double x){
		String s = graphs[graph].getFormula().replaceAll("x", Double.toString(x));
		double ans = Calculator.evaluateExpression(getVariableManager(), s);
		return ans;
	}

	public int isXInGraphRange(double x){
		return x < getGraphMinX() ? 2 : x > getGraphMaxX() ? 1 : 0;
	}

	public void setFunctionPanelRenderer(int graph, IFunctionRenderer renderer){
		currentFunctionDrawn = graph;
		graphFunctionPanel.setFunctionRenderer(renderer);
		graphFunctionPanel.updateUI();
	}

	public void clearFunctionPanel(){
		currentFunctionDrawn = -1;
		graphFunctionPanel.clearDraw();
		graphFunctionPanel.updateUI();
	}

	public double calculateDelta(int graph, double x0, double x1){
		double ansX0 = calculateGraphPoint(graph, x0);
		double ansX1 = calculateGraphPoint(graph, x1);
		return ansX1 - ansX0;
	}
	
	public int[] getOtherActiveGraphs(int self){
		List<Integer> list = new ArrayList<Integer>();
		int i;
		for(i = 0; i < MAX_GRAPHS; i++){
			
			if(i == self)
				continue;
			
			if(isActiveGraph(i)){
				list.add(i);
			}
		}
		
		int[] activeGraphs = new int[list.size()];
		for(i = 0; i < list.size(); i++){
			activeGraphs[i] = list.get(i);
		}
		return activeGraphs;
	}
	
	public int getGraphPointListSize(int graph){
		return graphs[graph].getPointListSize();
	}
	
	public Point getPointValue(int graph, int index){
		return graphs[graph].getPointValue(index);
	}
	
	public boolean isActiveGraph(int line){
		return graphs[line].hasFormula();
	}
}
