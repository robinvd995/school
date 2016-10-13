package visualGraph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class GraphBackPanel extends JPanel{

	private final VisualGraph vg;

	public GraphBackPanel(VisualGraph visualgraph){
		super();
		vg = visualgraph;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		System.out.println("Drawing lines");

		Graphics2D graphics = (Graphics2D)g;
		graphics.translate(0, getHeight());
		graphics.scale(1, -1);

		double x;
		double y;

		if(vg.getGraphMinX() >= 0.0D){
			x = 0.0D;
		}
		else if(vg.getGraphMaxX() <= 0.0D){
			x = 1.0D - (1.0D / getWidth());
		}
		else{
			x = Math.abs(vg.getGraphMinX()) / vg.getGraphWidth();
		}

		if(vg.getGraphMinY() >= 0.0D){
			y = 0.0D + (1.0D / getHeight());
		}
		else if(vg.getGraphMaxY() < 0.0D){
			y = 1.0D;
		}
		else{
			y = Math.abs(vg.getGraphMinY()) / vg.getGraphHeight();
		}

		int i;
		int stepAmount, numberOfMinSteps;
		double stepSize;

		graphics.setColor(Color.LIGHT_GRAY);
		stepAmount = (int) (Math.abs(vg.getGraphHeight()) / vg.getGraphStepY());
		stepSize = 1.0D / (double)stepAmount;
		numberOfMinSteps = (vg.getGraphMinY() < 0.0D) ? (int) (Math.abs(vg.getGraphMinY()) / vg.getGraphStepY()) : 0;
		System.out.println(stepAmount);
		for(i = -numberOfMinSteps; i < stepAmount; i++){
			double stepY = y + stepSize * i;
			Line2D line = createLine(0.0D, stepY, 1.0D, stepY);
			graphics.draw(line);
		}
		
		stepAmount = (int) (Math.abs(vg.getGraphWidth()) / vg.getGraphStepX());
		stepSize = 1.0D / (double)stepAmount;
		numberOfMinSteps = (vg.getGraphMinX() < 0.0D) ? (int) (Math.abs(vg.getGraphMinX()) / vg.getGraphStepX()) : 0;
		System.out.println(stepAmount);
		for(i = -numberOfMinSteps; i < stepAmount; i++){
			double stepX = x + stepSize * i;
			Line2D line = createLine(stepX, 0.0D, stepX, 1.0D);
			graphics.draw(line);
		}

		graphics.setColor(Color.BLACK);

		Line2D horLine = new Line2D.Double(0.0D, y * (double) this.getHeight(), this.getWidth(), y * (double) this.getHeight());
		Line2D verLine = new Line2D.Double(x * (double) this.getWidth(), 0.0D, x * (double) this.getWidth(), this.getHeight());

		graphics.draw(horLine);
		graphics.draw(verLine);
	}

	private Line2D createLine(double x0, double y0, double x1, double y1){
		return new Line2D.Double(x0 * (double)getWidth(), y0 * (double)getHeight(), x1 * (double)getWidth(), y1 * (double)getHeight());
	}
}
