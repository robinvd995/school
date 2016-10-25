package visualGraph;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class FunctionManager implements ActionListener{
	
	private static final int WIDTH = 304;
	private static final int HEIGHT = 150;
	
	private final VisualGraph vg;
	
	private JDialog window;
	private JComboBox<EnumFunction> functionComboBox;
	
	private BaseFunctionPanel[] panels;
	private int activeFunctionPanel = 0;
	
	private int currentGraph;
	
	private JButton executeButton;
	
	public FunctionManager(VisualGraph visualgraph){
		vg = visualgraph;
	}
	
	public void initFunctionWindow(){
		window = new JDialog(vg.getFrame(), true);
		window.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		window.setTitle("Function");
		window.setResizable(false);
		window.setLayout(null);
		
		panels = new BaseFunctionPanel[EnumFunction.size()];
		
		functionComboBox = new JComboBox<EnumFunction>();
		for(EnumFunction ef : EnumFunction.values()){
			int i = ef.ordinal();
			functionComboBox.addItem(ef);
			panels[i] = createFunctionPanel(ef);
			panels[i].initPanel();
			window.add(panels[i].getPanel());
		}
		functionComboBox.setSize(256, 30);
		functionComboBox.setLocation(20, 20);
		functionComboBox.addActionListener(this);
		window.add(functionComboBox);
		
		executeButton = new JButton("Execute");
		executeButton.setSize(256, 30);
		executeButton.setVisible(true);
		executeButton.addActionListener(this);
		executeButton.setActionCommand("execute");
		window.add(executeButton);
		
		window.getRootPane().setDefaultButton(executeButton);
		
		setActiveFunctionPanel(0, true);
	}

	private BaseFunctionPanel createFunctionPanel(EnumFunction f){
		try {
			return f.getPanelClass().getDeclaredConstructor(VisualGraph.class).newInstance(vg);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void openFunctionWindow(int graph){
		currentGraph = graph;
		window.setVisible(true);
		setActiveFunctionPanel(0, false);
		functionComboBox.setSelectedIndex(0);
	}
	
	public void closeFunctionWindow(){
		currentGraph = 0;
		window.setVisible(false);
	}
	
	private void setActiveFunctionPanel(int active, boolean init){
		if(!init){
			panels[activeFunctionPanel].setVisible(false);
		}
		activeFunctionPanel = active;
		panels[activeFunctionPanel].onOpen(currentGraph);
		panels[activeFunctionPanel].setVisible(true);
		setWindowBounds();
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == functionComboBox){
			EnumFunction func = (EnumFunction) functionComboBox.getSelectedItem();
			setActiveFunctionPanel(func.ordinal(), false);
		}
		else if(evt.getActionCommand().equals("execute")){
			panels[activeFunctionPanel].execute(vg.getVariableManager(), currentGraph);
			closeFunctionWindow();
		}
	}
	
	private void setWindowBounds(){
		Rectangle rect = vg.getFrame().getBounds();
		int actualHeight = HEIGHT + panels[activeFunctionPanel].getHeight();
		int posX = (int)(rect.x + (rect.getWidth() - WIDTH) / 2);
		int posY = (int)(rect.y + (rect.getHeight() - actualHeight) / 2);
		
		executeButton.setLocation(20, actualHeight - 80);
		
		window.setBounds(posX, posY, WIDTH, actualHeight);
	}
}
