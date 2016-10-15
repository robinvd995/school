package visualGraph;

import java.awt.Color;

public class Graph{

	public static final int MIN_WIDTH = 1;
	public static final int MAX_WIDTH = 5;
	public static final int INITIAL_WIDTH = 1;
	
	private String name;
	private String formula;
	private Color color;
	private double accuracy;
	private int lineWidth = 1;
	
	public Graph(String n, Color c, double a){
		name = n;
		color = c;
		accuracy = a;
	}
	
	public String getName(){
		return name;
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
	
	public void setFormula(String f){
		formula = f;
	}
	
	public boolean hasFormula(){
		return formula != null && !formula.isEmpty();
	}
	
	public void setLineWidth(int width){
		lineWidth = width;
	}
	
	public int getLineWidth(){
		return lineWidth;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public void setAccuracy(double a){
		accuracy = a;
	}
}
