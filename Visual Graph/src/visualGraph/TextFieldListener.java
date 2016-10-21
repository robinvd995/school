package visualGraph;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextFieldListener implements DocumentListener, FocusListener{

	private VisualGraph vg;
	private int graph;
	
	public TextFieldListener(VisualGraph visualgraph, int id){
		vg = visualgraph;
		graph = id;
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		onTextFieldChanged();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		onTextFieldChanged();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		onTextFieldChanged();
	}

	private void onTextFieldChanged(){
		vg.onTextFieldChanged(graph);
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		vg.onTextfieldFocusLost(graph);
	}
}
