package visualGraph;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class FunctionRendererPoint implements IFunctionRenderer{
	
	private String message;
	private double posX;
	private double posY;
	
	private int crossSize = 10;
	
	public FunctionRendererPoint(String msg, double x, double y){
		message = msg;
		posX = x;
		posY = y;
	}
	
	public FunctionRendererPoint setCrossSize(int size){
		crossSize = size;
		return this;
	}
	
	@Override
	public void render(Graphics2D graphics, int screenWidth, int screenHeight, double amplitudeX, double translationX, double amplitudeY, double translationY) {
		
		graphics.setStroke(new BasicStroke(3));
		
		double x = translationX + posX * amplitudeX;
		double y = translationY + posY * amplitudeY;
		
		graphics.draw(createLine(x - crossSize, y - crossSize, x + crossSize, y + crossSize));
		graphics.draw(createLine(x - crossSize, y + crossSize, x + crossSize, y - crossSize));
		
		double textPosX = x + 20;
		double textPosY = y - graphics.getFontMetrics().getHeight() / 2 + 3;
		if(x > screenWidth / 2){
			textPosX = x - graphics.getFontMetrics().stringWidth(message) - 20;
		}
		
		graphics.scale(1, -1);
		graphics.drawString(message, (float)textPosX, (float)(-1.0 * textPosY));
	}
	
	private Line2D createLine(double x0, double y0, double x1, double y1){
		return new Line2D.Double(x0, y0, x1, y1);
	}

}
