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
		//inputField.addActionListener(manager);
		//inputField.setActionCommand("execute");
		panel.add(inputField);
	}
	
	@Override
	public void onOpen(int line) {
		inputField.setText("");
	}

	@Override
	public void execute(VariableManager vm, int line) {
		String s = vm.replaceVariables(inputField.getText());
		
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
				vg.log(getLogMessage(line, x, ans));
				vg.setFunctionPanelRenderer(line, new FunctionRendererPoint("x = " + Utils.toStringAndCutDouble(x, 4) + ", y = " + Utils.toStringAndCutDouble(ans, 4), x, ans).setCrossSize(5));
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

	private String getLogMessage(int line, double x, double y){
		return vg.getGraphName(line) + ": for x = " + Utils.toStringAndCutDouble(x, 4) + ", y = " + Utils.toStringAndCutDouble(y, 4);
	}
	
	@Override
	public int getHeight() {
		return 44;
	}
}
