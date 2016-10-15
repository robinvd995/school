package visualGraph;

import java.awt.Rectangle;

import javax.swing.JDialog;

public class FunctionManager {

	private static final int OPTIONS_WIDTH = 188;
	private static final int OPTIONS_HEIGHT = 256;
	
	private final VisualGraph vg;
	
	private JDialog window;
	
	public FunctionManager(VisualGraph visualgraph){
		vg = visualgraph;
	}
	
	public void initFunctionWindow(){
		window = new JDialog(vg.getFrame(), true);
		window.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		window.setTitle("Options");
		window.setResizable(false);
		window.setLayout(null);
		Rectangle rect = vg.getFrame().getBounds();
		int posX = (int)(rect.x + (rect.getWidth() - OPTIONS_WIDTH) / 2);
		int posY = (int)(rect.y + (rect.getHeight() - OPTIONS_HEIGHT) / 2);
		window.setBounds(posX, posY, OPTIONS_WIDTH, OPTIONS_HEIGHT);
	}
}
