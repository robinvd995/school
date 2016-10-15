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
		
		System.out.println("Action Performed");
		if(args.length != 2)
			return;
		System.out.println("x == 2");
		
		int line = Integer.parseInt(args[1]);
		/*if(args[0].equals("draw")){
			System.out.println("draw");
			vg.drawLine(line);
		}
		else if(args[0].equals("clear")){
			vg.clearLine(line);
		}*/
		
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
		}
	}

}
