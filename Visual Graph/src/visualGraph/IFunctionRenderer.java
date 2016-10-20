package visualGraph;

import java.awt.Graphics2D;

public interface IFunctionRenderer {

	void render(Graphics2D graphics, int screenWidth, int screenHeight, double amplitudeX, double translationX, double amplitudeY, double translationY);
}
