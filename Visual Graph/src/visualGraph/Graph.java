package visualGraph;

import java.awt.Color;

public class Graph{

	private String formula;
	private Color color;
	private double accuracy;
	
	public Graph(String f, Color c, double a){
		formula = f;
		color = c;
		accuracy = a;
	}
	
	public String getFormula(){
		return formula;
	}
	
	public Color getColor(){
		return color;
	}
	
	public double getAccuracy(){
		return accuracy;
	}
}
