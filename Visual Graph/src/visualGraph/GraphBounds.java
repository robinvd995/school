package visualGraph;

public class GraphBounds {

	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	public GraphBounds(double x0, double x1, double y0, double y1){
		setBounds(x0, x1, y0, y1);
	}
	
	public double getMinX(){
		return minX;
	}
	
	public double getMaxX(){
		return maxX;
	}
	
	public double getMinY(){
		return minY;
	}
	
	public double getMaxY(){
		return maxY;
	}
	
	public double getWidth(){
		return maxX - minX;
	}
	
	public double getHeight(){
		return maxY - minY;
	}
	
	public boolean isInBounds(double x, double y){
		return x >= minX && x < maxX && y >= minY && y < maxY;
	}
	
	public void setBounds(double x0, double x1, double y0, double y1){
		minX = x0;
		maxX = x1;
		minY = y0;
		maxY = y1;
	}
}
