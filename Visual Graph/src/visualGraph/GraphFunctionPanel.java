package visualGraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class GraphFunctionPanel extends JPanel{
	
	private VisualGraph vg;

	private IFunctionRenderer renderer;

	public GraphFunctionPanel(VisualGraph visualgraph){
		super();
		vg = visualgraph;
	}

	public void setFunctionRenderer(IFunctionRenderer r){
		renderer = r;
	}
	
	public void clearDraw(){
		renderer = null;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if(renderer != null){
			Graphics2D graphics = (Graphics2D)g;
			graphics.setColor(Color.BLACK);
			
			double amplitudeY = getHeight() / vg.getGraphHeight();
			double translationY = amplitudeY * (-1.0D * vg.getGraphMinY());
			double amplitudeX = getWidth() / vg.getGraphWidth();
			double translationX = amplitudeX * (-1.0D * vg.getGraphMinX());
			
			renderer.render(graphics, getWidth(), getHeight(), amplitudeX, translationX, amplitudeY, translationY);
		}
	}

	private Line2D createLine(double x0, double y0, double x1, double y1){
		return new Line2D.Double(x0, y0, x1, y1);
	}
}
