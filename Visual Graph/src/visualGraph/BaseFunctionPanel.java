package visualGraph;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class BaseFunctionPanel {

	protected VisualGraph vg;
	
	protected JPanel panel;
	
	public BaseFunctionPanel(VisualGraph visualgraph){
		vg = visualgraph;
	}
	
	public void initPanel(){
		panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(256, getHeight());
		panel.setLocation(20, 60);
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.setVisible(false);
		
		init();
	}
	
	public JPanel getPanel(){
		return panel;
	}
	
	public void setVisible(boolean isVisible){
		panel.setVisible(isVisible);
	}
	
	protected abstract void init();
	public abstract void onOpen(int line);
	public abstract void execute(VariableManager vm, int line);
	
	public abstract int getHeight();
}
