package visualGraph;

public enum EnumAction {

	IDLE,NEW,SAVE,OPEN,EXIT,OPTIONS;
	
	public String getActionCommand(){
		return name().toLowerCase();
	}
	
	public static EnumAction getActionFromCommand(String command){
		for(EnumAction a : EnumAction.values()){
			if(a.getActionCommand().equals(command))
				return a;
		}
		return IDLE;
	}
}
