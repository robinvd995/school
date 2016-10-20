package visualGraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputManager implements ActionListener{

	private final VisualGraph vg;
	
	public InputManager(VisualGraph visualgraph){
		vg = visualgraph;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String[] args = e.getActionCommand().split(" ");
		
		if(args.length != 2)
			return;
		
		int line = Integer.parseInt(args[1]);
		
		switch(args[0]){
		case "draw":
			vg.drawLine(line);
			break;
			
		case "clear":
			vg.clearLine(line);
			break;
			
		case "settings":
			vg.openGraphSettingsMenu(line);
			break;
			
		case "function":
			vg.openFunctionWindow(line);
			break;
		}
	}

}
