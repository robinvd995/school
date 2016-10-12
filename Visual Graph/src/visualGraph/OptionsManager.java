package visualGraph;

import java.awt.Rectangle;

import javax.swing.JDialog;

public class OptionsManager {

	private static final int OPTIONS_WIDTH = 600;
	private static final int OPTIONS_HEIGHT = 400;
	
	private VisualGraph vg;
	
	private JDialog window;
	
	
	public OptionsManager(VisualGraph visualGraph){
		vg = visualGraph;
	}
	
	public void initOptionsWindow(){
		window = new JDialog(vg.getFrame(), true);
		window.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		window.setTitle("Options");
		window.setLayout(null);
		Rectangle rect = vg.getFrame().getBounds();
		int posX = (int)(rect.x + (rect.getWidth() - OPTIONS_WIDTH) / 2);
		int posY = (int)(rect.y + (rect.getHeight() - OPTIONS_HEIGHT) / 2);
		window.setBounds(posX, posY, OPTIONS_WIDTH, OPTIONS_HEIGHT);
	}
	
	public void openOptionsWindow(){
		window.setVisible(true);
	}
	
	public void closeOptionsWindow(){
		window.setVisible(false);
	}
}
