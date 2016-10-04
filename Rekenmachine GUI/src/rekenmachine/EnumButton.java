package rekenmachine;

public enum EnumButton {

	CLEAR(3, 0, "AC", "clear", "C", "back", new int[]{8}, new boolean[]{true}),
	NMB9(2, 1, "9", "nmb9", new int[]{105, 57}),
	NMB8(1, 1, "8", "nmb8", new int[]{104, 56}),
	NBM7(0, 1, "7", "nmb7", new int[]{103, 55}),
	NMB6(2, 2, "6", "nmb6", new int[]{102, 54}),
	NMB5(1, 2, "5", "nmb5", new int[]{101, 53}),
	NMB4(0, 2, "4", "nmb4", new int[]{100, 52}),
	NMB3(2, 3, "3", "nmb3", new int[]{99, 51}),
	NMB2(1, 3, "2", "nmb2", new int[]{98, 50}),
	NMB1(0, 3, "1", "nmb1", new int[]{97, 49}),
	NMB0(0, 4, "0", "nmb0", new int[]{96, 48}),
	DECIMAL(1, 4, ".", "dec", new int[]{110, 46, 44}),
	EQUALS(2, 4, "=", "equals", null),
	DEVIDE(3, 1, "/", "ope/", "sin", "opesin", null, null),
	MULTIPLY(3, 2, "x", "ope*", "cos", "opecos", null, null),
	SUBTRACT(3, 3, "-", "ope-", "tan", "opetan", null, null),
	ADD(3, 4, "+", "ope+", "\u03c0", "opePI", null, null),
	BRACKET_OPEN(1, 0, "(", "ope(", "^", "ope^", new int[]{102}, new boolean[]{true}),
	BRACKET_CLOSE(2, 0, ")", "ope)", "SQR", "opeSQR", null, null),
	LAST_AWNSER(0, 0, "ANS", "ans", null);
	
	private final int row;
	private final int collum;
	private final String text;
	private final String actionCommand;
	
	private final boolean hasSecondaryAction;
	private final String secondaryText;
	private final String secondaryCommand;
	
	private final int[] hotkeys;
	private final boolean[] needShift;
	
	private EnumButton(int r, int c, String t, String ac, int[] hk){
		this(r, c, t, ac, null, null, hk, null);
	}
	
	private EnumButton(int r, int c, String t, String ac, String t2, String ac2, int[] hk, boolean[] ns){
		row = r;
		collum = c;
		text = t;
		actionCommand = ac;
		secondaryText = t2;
		secondaryCommand = ac2;
		hasSecondaryAction = (t2 != null && ac2 != null);
		hotkeys = hk;
		needShift = ns;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCollum(){
		return collum;
	}
	
	public String getText(boolean shiftPressed){
		return shiftPressed && hasSecondaryAction ? secondaryText : text;
	}
	
	public String getActionCommand(boolean shiftPressed){
		return shiftPressed && hasSecondaryAction ? secondaryCommand : actionCommand;
	}
	
	public boolean hasSecondaryAction(){
		return hasSecondaryAction;
	}
	
	public boolean hasHotkeys(){
		return hotkeys != null && hotkeys.length > 0;
	}
	
	public int[] getHotkeys(){
		return hotkeys;
	}
	
	public boolean isShiftHotkey(int index){
		return needShift == null ? false : needShift[index];
	}
	
	public static int getSize(){
		return values().length;
	}
	
	public static EnumButton getButton(int index){
		return (index >= 0 && index < getSize()) ? values()[index] : null;
	}
}
