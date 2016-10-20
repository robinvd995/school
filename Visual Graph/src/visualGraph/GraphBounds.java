package visualGraph;

public class GraphBounds {

	public static final double[] DEFAULT_VALUES = {
			-10.0D, 10.0D, -10.0D, 10.0D, 1.0D, 1.0D
	};
	
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	
	private double stepX;
	private double stepY;
	
	public GraphBounds(){
		setDefault();
	}
	
	public void setDefault(){
		setBounds(DEFAULT_VALUES[0], DEFAULT_VALUES[1], DEFAULT_VALUES[2], DEFAULT_VALUES[3], DEFAULT_VALUES[4], DEFAULT_VALUES[5]);
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
	
	public double getStepX(){
		return stepX;
	}
	
	public double getStepY(){
		return stepY;
	}
	
	public boolean isInBounds(double x, double y){
		return x >= minX && x < maxX && y >= minY && y < maxY;
	}
	
	public void setBounds(double x0, double x1, double y0, double y1, double sx, double sy){
		minX = x0;
		maxX = x1;
		minY = y0;
		maxY = y1;
		stepX = sx;
		stepY = sy;
	}
}
