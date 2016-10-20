package visualGraph;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class FunctionRendererPoint implements IFunctionRenderer{

	private static final int CROSS_SIZE = 10;
	
	private double posX;
	private double posY;
	
	public FunctionRendererPoint(double x, double y){
		posX = x;
		posY = y;
	}
	
	@Override
	public void render(Graphics2D graphics, int screenWidth, int screenHeight, double amplitudeX, double translationX, double amplitudeY, double translationY) {
		
		graphics.setStroke(new BasicStroke(3));
		
		double x = translationX + posX * amplitudeX;
		double y = translationY + posY * amplitudeY;
		
		graphics.draw(createLine(x - CROSS_SIZE, y - CROSS_SIZE, x + CROSS_SIZE, y + CROSS_SIZE));
		graphics.draw(createLine(x - CROSS_SIZE, y + CROSS_SIZE, x + CROSS_SIZE, y - CROSS_SIZE));
		
		String message = "x = " + posX + ", y = " + posY;
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
