package visualGraph;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class FunctionPanelDelta extends BaseFunctionPanel{

	private static final String[] LABEL_VALUES = {
			"x0:", "x1:"
	};

	private JTextField[] inputField;

	public FunctionPanelDelta(VisualGraph visualgraph) {
		super(visualgraph);
	}

	@Override
	protected void init(){

		inputField = new JTextField[2];

		int spaceBetween = 28;

		for(int i = 0; i < inputField.length; i++){
			JLabel label = new JLabel(LABEL_VALUES[i]);
			label.setSize(40, 24);
			label.setLocation(10, 10 + spaceBetween * i);
			panel.add(label);

			inputField[i] = new JTextField();
			inputField[i].setSize(200, 25);
			inputField[i].setLocation(43, 10 + spaceBetween * i);
			panel.add(inputField[i]);
		}
	}

	@Override
	public void onOpen(int line) {
		inputField[0].setText("");
		inputField[1].setText("");
	}

	@Override
	public void execute(VariableManager vm, int line) {
		double[] values = new double[inputField.length];
		for(int i = 0; i < values.length; i++){
			String s = vm.replaceVariables(inputField[i].getText());
			if(s.isEmpty()){
				vg.log("No number entered!");
				return;
			}

			try{
				values[i] = Double.parseDouble(s);
				int result = vg.isXInGraphRange(values[i]);
				if(result == 1){
					vg.log("Error, x > graph max x");
					return;
				}
				else if(result == 2){
					vg.log("Error, x < graph min x");
					return;
				}

			}
			catch(NumberFormatException e){
				vg.log("Invalid number!");
				return;
			}
		}
		
		double y0 = vg.calculateGraphPoint(line, values[0]);
		double y1 = vg.calculateGraphPoint(line, values[1]);
		String sx0 = Utils.toStringAndCutDouble(values[0], 4);
		String sx1 = Utils.toStringAndCutDouble(values[1], 4);
		String sy0 = Utils.toStringAndCutDouble(y0, 4);
		String sy1 = Utils.toStringAndCutDouble(y1, 4);
		String ans = Utils.toStringAndCutDouble((y1 - y0) / (values[1] - values[0]), 4);
		vg.setFunctionPanelRenderer(line, new FunctionRendererDelta(values[0], y0, values[1], y1));
		vg.log(vg.getGraphName(line) + ": Delta between (" + sx0 + "," + sy0 + ") and (" + sx1 + ", " + sy1 + ") = " + ans);
	}

	@Override
	public int getHeight() {
		return 72;
	}
}
