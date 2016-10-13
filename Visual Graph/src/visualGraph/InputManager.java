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
		if(args[0].equals("draw")){
			vg.drawLine(line);
		}
		if(args[0].equals("clear")){
			vg.clearLine(line);
		}
	}

}
