package visualGraph;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class FunctionRendererDelta implements IFunctionRenderer{

	private static final int CROSS_SIZE = 5;
	
	private double posX0;
	private double posY0;
	private double posX1;
	private double posY1;

	public FunctionRendererDelta(double x0, double y0, double x1, double y1){
		posX0 = x0;
		posY0 = y0;
		posX1 = x1;
		posY1 = y1;
	}

	@Override
	public void render(Graphics2D graphics, int screenWidth, int screenHeight, double amplitudeX, double translationX, double amplitudeY, double translationY) {
		graphics.setStroke(new BasicStroke(3));

		double x0 = translationX + posX0 * amplitudeX;
		double y0 = translationY + posY0 * amplitudeY;

		graphics.draw(createLine(x0 - CROSS_SIZE, y0 - CROSS_SIZE, x0 + CROSS_SIZE, y0 + CROSS_SIZE));
		graphics.draw(createLine(x0 - CROSS_SIZE, y0 + CROSS_SIZE, x0 + CROSS_SIZE, y0 - CROSS_SIZE));
		
		double x1 = translationX + posX1 * amplitudeX;
		double y1 = translationY + posY1 * amplitudeY;

		graphics.draw(createLine(x1 - CROSS_SIZE, y1 - CROSS_SIZE, x1 + CROSS_SIZE, y1 + CROSS_SIZE));
		graphics.draw(createLine(x1 - CROSS_SIZE, y1 + CROSS_SIZE, x1 + CROSS_SIZE, y1 - CROSS_SIZE));
		
		graphics.setStroke(new BasicStroke(2));
		
		graphics.draw(createLine(x0, y0, x1,y1));
		
		String message = "DeltaY = " + Utils.toStringAndCutDouble((posY1 - posY0) / (posX1 - posX0), 4);
		
		double x = x0 + (x1 - x0) / 2;
		double y = y0 + (y1 - y0) / 2;
		
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
