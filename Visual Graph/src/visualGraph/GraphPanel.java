package visualGraph;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class GraphPanel extends JPanel{

	private VisualGraph vg;
	private Graph graph;

	public GraphPanel(VisualGraph visualgraph){
		super();
		vg = visualgraph;
	}

	public void setGraph(Graph g){
		graph = g;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

		if(graph.hasFormula()){
			Graphics2D graphics = (Graphics2D)g;
			graphics.setStroke(new BasicStroke(graph.getLineWidth()));
			
			double minX = vg.getGraphMinX();
			int iterations = (int)((double)getWidth() * graph.getAccuracy());
			double incrementation = 1.0D / (double)iterations;

			System.out.println(iterations + ", " + incrementation);

			graphics.setColor(graph.getColor());

			double lastAwnser;
			String s0 = Double.toString(minX);
			System.out.println(s0);
			String s = graph.getFormula().replaceAll("x", s0);
			lastAwnser = Calculator.evaluateExpression(vg.getVariableManager(), s);

			System.out.println(incrementation);

			double amplitude = getHeight() / vg.getGraphHeight();
			double translation = amplitude * (-1.0D * vg.getGraphMinY());

			for(int i = 1; i <= iterations; i++){
				double d = minX + i * incrementation * vg.getGraphWidth();
				s = graph.getFormula().replaceAll("x", Double.toString(d));
				double awnser = Calculator.evaluateExpression(vg.getVariableManager(), s);

				double x0 = (i - 1) * incrementation * getWidth();
				double x1 = i * incrementation * getWidth();
				double y0 = translation + lastAwnser * amplitude;
				double y1 = translation + awnser * amplitude;

				Line2D line = createLine(x0, y0, x1, y1);
				graphics.draw(line);

				lastAwnser = awnser;
			}
		}
	}

	private Line2D createLine(double x0, double y0, double x1, double y1){
		return new Line2D.Double(x0, y0, x1, y1);
	}
}
