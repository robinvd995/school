package visualGraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphSettingsManager implements ChangeListener,ActionListener{

	private static final int OPTIONS_WIDTH = 320;
	private static final int OPTIONS_HEIGHT = 256;

	private final VisualGraph vg;

	private JDialog window;

	private int graphID = -1;

	private JTextField nameField;
	private JTextField colorField;
	private JSlider lineWidthSlider;
	private JSlider[] colorSlider;

	private JLabel colorShowLabel;

	public GraphSettingsManager(VisualGraph visualgraph){
		vg = visualgraph;
	}

	public void initSettingsWindow(){
		window = new JDialog(vg.getFrame(), true);
		window.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		window.setTitle("Settings");
		window.setResizable(false);
		window.setLayout(null);
		Rectangle rect = vg.getFrame().getBounds();
		int posX = (int)(rect.x + (rect.getWidth() - OPTIONS_WIDTH) / 2);
		int posY = (int)(rect.y + (rect.getHeight() - OPTIONS_HEIGHT) / 2);
		window.setBounds(posX, posY, OPTIONS_WIDTH, OPTIONS_HEIGHT);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setLocation(20, 20);
		nameLabel.setSize(100, 24);
		window.add(nameLabel);

		nameField = new JTextField();
		nameField.setLocation(100, 20);
		nameField.setSize(200, 24);
		window.add(nameField);

		JLabel widthLabel = new JLabel("Line Width:");
		widthLabel.setLocation(20, 52);
		widthLabel.setSize(100, 20);
		window.add(widthLabel);

		JPanel sliderPanel = new JPanel();
		sliderPanel.setSize(200, 36);
		sliderPanel.setLocation(100, 44);
		window.add(sliderPanel);

		lineWidthSlider = new JSlider(JSlider.HORIZONTAL, Graph.MIN_WIDTH, Graph.MAX_WIDTH, Graph.INITIAL_WIDTH);
		lineWidthSlider.setMajorTickSpacing(2);
		lineWidthSlider.setMinorTickSpacing(1);
		lineWidthSlider.setVisible(true);
		lineWidthSlider.setPaintTicks(true);
		sliderPanel.add(lineWidthSlider);

		colorSlider = new JSlider[3];
		String[] colorString = {
				"R", "G", "B"
		};
		for(int i = 0; i < 3; i++){

			JLabel colorLabel = new JLabel("Color " + colorString[i] + ":");
			colorLabel.setSize(100, 20);
			colorLabel.setLocation(20, 87 + 30 * i);
			window.add(colorLabel);

			JPanel colorSliderPanel = new JPanel();
			colorSliderPanel.setSize(170, 30);
			colorSliderPanel.setLocation(100, 84 + 30 * i);
			colorSliderPanel.setLayout(new BorderLayout());
			window.add(colorSliderPanel);

			colorSlider[i] = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
			colorSlider[i].setVisible(true);
			colorSlider[i].addChangeListener(this);
			colorSliderPanel.add(colorSlider[i]);
		}

		colorShowLabel = new JLabel();
		colorShowLabel.setLocation(276, 86);
		colorShowLabel.setSize(24, 80);
		colorShowLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		colorShowLabel.setOpaque(true);
		window.add(colorShowLabel);

		JButton applyButton = new JButton("Apply");
		applyButton.setLocation(20, 180);
		applyButton.setSize(130, 24);
		applyButton.setVisible(true);
		applyButton.setActionCommand("apply");
		applyButton.addActionListener(this);
		window.add(applyButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setLocation(170, 180);
		cancelButton.setSize(130, 24);
		cancelButton.setVisible(true);
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		window.add(cancelButton);
	}

	public void openSettingsWindow(int id){
		graphID = id;
		nameField.setText(vg.getGraphName(graphID));
		lineWidthSlider.setValue(vg.getGraphLineWidth(graphID));
		colorSlider[0].setValue(vg.getGraphColor(graphID).getRed());
		colorSlider[1].setValue(vg.getGraphColor(graphID).getGreen());
		colorSlider[2].setValue(vg.getGraphColor(graphID).getBlue());
		colorShowLabel.setBackground(vg.getGraphColor(graphID));
		window.setVisible(true);
	}

	private Color getSliderColor(){
		return new Color(colorSlider[0].getValue(), colorSlider[1].getValue(), colorSlider[2].getValue());
	}
	
	private void updateShowColor(){
		colorShowLabel.setBackground(getSliderColor());
		colorShowLabel.updateUI();
	}

	public void closeSettingsWindow(){
		graphID = -1;
		window.setVisible(false);
	}

	@Override
	public void stateChanged(ChangeEvent evt) {
		if(evt.getSource() instanceof JSlider){
			JSlider slider = (JSlider) evt.getSource();
			if(slider == colorSlider[0] || slider == colorSlider[1] || slider == colorSlider[2]){
				updateShowColor();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getActionCommand().equals("apply")){
			String name = nameField.getText();
			if(!name.isEmpty()){
				vg.setGraphName(graphID, name);
			}else{
				vg.log("Error, Invalid name!");
			}
			vg.setGraphLineWidth(graphID, lineWidthSlider.getValue());
			vg.setGraphColor(graphID, getSliderColor());
			vg.markGraphForRedraw(graphID);
			
			closeSettingsWindow();
		}
		else if(evt.getActionCommand().equals("cancel")){
			closeSettingsWindow();
		}
	}
}
