package visualGraph;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FunctionPanelValue extends BaseFunctionPanel{

	private JTextField inputField;
	
	public FunctionPanelValue(VisualGraph visualgraph) {
		super(visualgraph);
	}

	@Override
	protected void init(){
		JLabel label = new JLabel("x:");
		label.setSize(40, 24);
		label.setLocation(10, 10);
		panel.add(label);
		
		inputField = new JTextField();
		inputField.setSize(200, 25);
		inputField.setLocation(43, 10);
		panel.add(inputField);
	}
	
	@Override
	public void onOpen(int line) {
		inputField.setText("");
	}

	@Override
	public void execute(int line) {
		String s = inputField.getText();
		
		if(s.isEmpty()){
			vg.log("No number entered!");
			return;
		}
		
		double x;
		try{
			x = Double.parseDouble(s);
			int result = vg.isXInGraphRange(x);
			switch(result){
			case 0:
				double ans = vg.calculateGraphPoint(line, x);
				String message = getMessage(line, x, ans);
				vg.log(message);
				vg.setFunctionPanelRenderer(line, new FunctionRendererPoint(x, ans));
				//vg.setGraphFunctionPanelDrawParameters(line, message, x, ans);
				break;
			case 1:
				vg.log("Error, x > graph max x");
				break;
				
			case 2:
				vg.log("Error, x < graph min x");
				break;
			}
		}
		catch(NumberFormatException e){
			vg.log("Invalid number!");
		}
	}

	private String getMessage(int line, double x, double y){
		return vg.getGraphName(line) + ": for x = " + x + ", y = " + y;
	}
	
	@Override
	public int getHeight() {
		return 44;
	}
}
