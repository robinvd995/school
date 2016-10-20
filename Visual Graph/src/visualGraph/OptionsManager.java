package visualGraph;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OptionsManager implements ActionListener{

	private static final String[] LABEL_TEXT = {
			"min X", "max X", "min Y", "max Y", "step X", "step Y"
	};
	
	private static final int OPTIONS_WIDTH = 188;
	private static final int OPTIONS_HEIGHT = 278;
	
	private VisualGraph vg;
	
	private JDialog window;
	
	private JTextField[] textField = new JTextField[6];
	
	public OptionsManager(VisualGraph visualGraph){
		vg = visualGraph;
	}
	
	public void initOptionsWindow(){
		window = new JDialog(vg.getFrame(), true);
		window.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		window.setTitle("Graph Bounds");
		window.setResizable(false);
		window.setLayout(null);
		Rectangle rect = vg.getFrame().getBounds();
		int posX = (int)(rect.x + (rect.getWidth() - OPTIONS_WIDTH) / 2);
		int posY = (int)(rect.y + (rect.getHeight() - OPTIONS_HEIGHT) / 2);
		window.setBounds(posX, posY, OPTIONS_WIDTH, OPTIONS_HEIGHT);
		
		//Stuff
		
		for(int i = 0; i < textField.length; i++){
			JLabel label = new JLabel(LABEL_TEXT[i]);
			label.setLocation(20, 20 + 20 * i);
			label.setSize(60, 20);
			window.add(label);
			
			textField[i] = new JTextField(Double.toString(getGraphValue(i)));
			textField[i].setLocation(80, 20 + 20 * i);
			textField[i].setSize(80, 20);
			window.add(textField[i]);
			
		}
		
		/*JLabel xMin = new JLabel("Min X");
		xMin.setLocation(20, 20);
		xMin.setSize(60, 20);
		window.add(xMin);
		
		text = new JTextField("");
		text.setLocation(80, 20);
		text.setSize(60, 20);
		window.add(text);
		
		JLabel xMax = new JLabel("Max X");
		xMax.setLocation(20, 40);
		xMax.setSize(60, 20);
		window.add(xMax);
		
		text1 = new JTextField("");
		text1.setLocation(80, 40);
		text1.setSize(60, 20);
		window.add(text1);
		
		JLabel yMin = new JLabel("Min Y");
		yMin.setLocation(20, 60);
		yMin.setSize(60, 20);
		window.add(yMin);
		
		text2 = new JTextField("");
		text2.setLocation(80, 60);
		text2.setSize(60, 20);
		window.add(text2);
		
		JLabel yMax = new JLabel("Max Y");
		yMax.setLocation(20, 80);
		yMax.setSize(60, 20);
		window.add(yMax);
		
		text3 = new JTextField("");
		text3.setLocation(80, 80);
		text3.setSize(60, 20);
		window.add(text3);*/
		
		JButton apply = new JButton("Apply");
		apply.setLocation(20, 40 + textField.length * 20);
		apply.setSize(140, 20);
		apply.addActionListener(this);
		apply.setActionCommand("apply");
		window.add(apply);
		
		JButton defaults = new JButton("Defaults");
		defaults.setLocation(20, 62 + textField.length * 20);
		defaults.setSize(140, 20);
		defaults.addActionListener(this);
		defaults.setActionCommand("defaults");
		window.add(defaults);
		
		JButton cancel = new JButton("Cancel");
		cancel.setLocation(20, 84 + textField.length * 20);
		cancel.setSize(140, 20);
		cancel.addActionListener(this);
		cancel.setActionCommand("cancel");
		window.add(cancel);
	}
	
	private void setToDefaults(){
		for(int i = 0; i < textField.length; i++){
			textField[i].setText(Double.toString(GraphBounds.DEFAULT_VALUES[i]));
		}
	}
	
	public void openOptionsWindow(){
		for(int i = 0; i < textField.length; i++){
			textField[i].setText(Double.toString(getGraphValue(i)));
		}
		window.setVisible(true);
	}
	
	public void closeOptionsWindow(){
		window.setVisible(false);
	}
	
	private double getGraphValue(int i){
		switch(i){
		default: return 0;
		case 0: return vg.getGraphMinX();
		case 1: return vg.getGraphMaxX();
		case 2: return vg.getGraphMinY();
		case 3: return vg.getGraphMaxY();
		case 4: return vg.getGraphStepX();
		case 5: return vg.getGraphStepY();
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getActionCommand().equals("cancel")){
			closeOptionsWindow();	
		}
		else if(evt.getActionCommand().equals("defaults")){
			setToDefaults();
		}
		else if(evt.getActionCommand().equals("apply")){
			boolean error = false;
			double[] values = new double[textField.length];
			for(int i = 0; i < textField.length; i++){
				try{
					String s = vg.getVariableManager().replaceVariables(textField[i].getText());
					values[i] = Double.parseDouble(s);
				}
				catch(NumberFormatException e){
					e.printStackTrace();
					vg.log("Illegal entry found at '" + LABEL_TEXT[i] + "'!");
					error = true;
					break;
				}
			}
			
			if(error){
				vg.log("Could not update the graph bounds!");
			}
			else{
				vg.setGraphBounds(values[0], values[1], values[2], values[3], values[4], values[5]);
			}
			
			closeOptionsWindow();
		}
	}
}
