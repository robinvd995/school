package rekenmachine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.ibm.icu.util.BytesTrie.Iterator;

public class ActionManager implements ActionListener, KeyListener{

	private static final List<HashMap<Integer,EnumButton>> hotkeyMap = new ArrayList<HashMap<Integer,EnumButton>>();

	private Calculator calc;
	private boolean isShiftDown;

	public ActionManager(Calculator c){
		calc = c;
		addHotkeys();
	}

	private void addHotkeys(){
		hotkeyMap.clear();
		
		HashMap<Integer,EnumButton> normalMap = new HashMap<Integer,EnumButton>();
		HashMap<Integer,EnumButton> shiftMap = new HashMap<Integer,EnumButton>();
		
		for(EnumButton button : EnumButton.values()){
			if(!button.hasHotkeys()) continue;

			int[] hotkeys = button.getHotkeys();
			for(int i = 0; i < hotkeys.length; i++){
				addHotkeyToMap(button.isShiftHotkey(i) ? shiftMap : normalMap, hotkeys[i], button);
			}
		}
		
		hotkeyMap.add(0, normalMap);
		hotkeyMap.add(1, shiftMap);
	}
	
	private void iterateThroughMap(HashMap<Integer,EnumButton> map){
		
	}

	private void addHotkeyToMap(HashMap<Integer,EnumButton> map, int key, EnumButton button){
		if(map.containsKey(key)){
			System.out.println("Failed to add hotkey to button '" + button.name() + "', already have a hotkey with id '" + key + "'");
			return;
		}
		map.put(key, button);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		executeCommand(evt.getActionCommand());
	}

	private void executeCommand(final String s){
		if(s.startsWith("nmb")){
			String nmbString = s.substring(3);
			int number = Integer.valueOf(nmbString);
			calc.addNumber(number);
		}

		if(s.startsWith("ope")){
			String oppString = s.substring(3);
			calc.addOperator(oppString);
		}

		switch(s){
		case "clear":
			calc.clear();
			break;
		case "equals":
			calc.equals();
			break;
		case "dec":
			calc.addDecimal();
			break;
		case "ans":
			calc.addAwnser();
			break;
		case "back":
			calc.removeCharacter();
			break;
		}
	}

	private HashMap<Integer,EnumButton> getHotkeyMap(){
		return hotkeyMap.get(isShiftDown ? 1 : 0);
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {

		if(evt.isShiftDown() && !isShiftDown){
			isShiftDown = true;
			calc.onShiftPressed();
		}
		
		HashMap<Integer,EnumButton> map = getHotkeyMap();
		EnumButton button = map.get(evt.getKeyCode());
		if(button != null) executeCommand(button.getActionCommand(isShiftDown));
		

		//EnumButton button = hotkeyMap.get(evt.getKeyCode());
		//if(button != null) executeCommand(button.getActionCommand(isShiftDown));

		/*if(evt.getKeyCode() >= 96 && evt.getKeyCode() <= 105){
			calc.addNumber(evt.getKeyCode() - 96);
		}

		if(evt.getKeyCode() >= 48 && evt.getKeyCode() <= 57){
			calc.addNumber(evt.getKeyCode() - 48);
		}

		switch(evt.getKeyCode()){
		case 110:
		case 46:
		case 44:
			calc.addDecimal();
			break;
		case 111:
			calc.addOperator("/");
			break;
		case 106:
			calc.addOperator("*");
			break;
		case 109:
			calc.addOperator("-");
			break;
		case 107:
			calc.addOperator("+");
			break;
		case 91:
			calc.addOperator("(");
			break;
		case 93:
			calc.addOperator(")"); 
			break;
		case 61:
		case 10:
			calc.equals();
			break;
		case 8:
			calc.removeCharacter();
			break;
		}*/
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!e.isShiftDown() && isShiftDown){
			isShiftDown = false;
			calc.onShiftReleased();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
