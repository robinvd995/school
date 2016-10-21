package visualGraph;

public class Point {

	private double posX;
	private double posY;
	
	public Point(double x, double y){
		posX = x;
		posY = y;
	}
	
	public double getX(){
		return posX;
	}
	
	public double getY(){
		return posY;
	}
	
	public String toString(){
		return Utils.toStringAndCutDouble(posX, 4) + "," + Utils.toStringAndCutDouble(posY, 4);
	}
}
