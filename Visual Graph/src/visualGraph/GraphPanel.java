package visualGraph;

import java.awt.Graphics;

import javax.swing.JPanel;

public class GraphPanel extends JPanel{

	public GraphPanel(){
		super();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		System.out.println("Printing");
	}
}
