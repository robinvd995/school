package visualGraph;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FunctionPanelIntersect extends BaseFunctionPanel{

	private static final String[] LABEL_VALUES = {
			"x0:", "x1:"
	};

	private JComboBox<GraphEntry> comboBox;

	public FunctionPanelIntersect(VisualGraph visualgraph) {
		super(visualgraph);
	}

	@Override
	protected void init(){

		JLabel targetLabel = new JLabel("targ:");
		targetLabel.setSize(40, 24);
		targetLabel.setLocation(10, 10);
		panel.add(targetLabel);
		
		comboBox = new JComboBox<GraphEntry>();
		comboBox.setSize(200, 24);
		comboBox.setLocation(43, 10);
		panel.add(comboBox);
		
	}

	@Override
	public void onOpen(int line) {
		comboBox.removeAllItems();

		int[] activeGraphs = vg.getOtherActiveGraphs(line);
		
		if(activeGraphs.length == 0){
			comboBox.addItem(new GraphEntry(-1, "NONE"));
			System.out.println("Adding none to function combo box");
		}
		else{
			for(int i = 0; i < activeGraphs.length; i++){
				comboBox.addItem(new GraphEntry(activeGraphs[i], vg.getGraphName(activeGraphs[i])));
			}
		}
	}

	@Override
	public void execute(VariableManager vm, int line) {

		GraphEntry selected = (GraphEntry) comboBox.getSelectedItem();		
		if(!selected.isValid()){
			vg.log("Invalid target");
		}
		int target = selected.getID();
		int iterations = vg.getGraphPointListSize(line);
		Point lastPoint = vg.getPointValue(line, 0);
		Point lastTargetPoint = vg.getPointValue(target, 0);
		for(int i = 1; i < iterations; i++){
			Point point = vg.getPointValue(line, i);
			Point targetPoint = vg.getPointValue(target, i);
			
			Point intersection = Calculator.intersectLines(lastPoint.getX(), lastPoint.getY(), point.getX(), point.getY(),
					lastTargetPoint.getX(), lastTargetPoint.getY(), targetPoint.getX(), targetPoint.getY());
			
			if(intersection != null){
				//System.out.println(intersection);
				vg.log(vg.getGraphName(line) + ": Intersection point with '" + vg.getGraphName(target) + "' = (" + intersection + ")");
				vg.setFunctionPanelRenderer(line, new FunctionRendererPoint(
						"Intersection with " + vg.getGraphName(target) + " = " + intersection,
						intersection.getX(), intersection.getY()).setCrossSize(5));
				break;
			}
			
			lastPoint = point;
			lastTargetPoint = targetPoint;
		}
	}

	@Override
	public int getHeight() {
		return 44;
	}

	public class GraphEntry{

		private final int id;
		private final String name;

		public GraphEntry(int i, String n){
			id = i;
			name = n;
		}

		public int getID(){
			return id;
		}

		public String toString(){
			return name;
		}
		
		public boolean isValid(){
			return id != -1;
		}
	}
}
