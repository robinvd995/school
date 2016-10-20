package visualGraph;

public enum EnumFunction {
	
	VALUE("Value", FunctionPanelValue.class),
	DELTA("Delta", FunctionPanelDelta.class),
	INTERSECT("Intersect", FunctionPanelIntersect.class);
	
	private final String functionName;
	private final Class<? extends BaseFunctionPanel> panelClass;
	
	private EnumFunction(String name, Class<? extends BaseFunctionPanel> c){
		functionName = name;
		panelClass = c;
	}
	
	public String toString(){
		return functionName;
	}
	
	public Class<? extends BaseFunctionPanel> getPanelClass(){
		return panelClass;
	}
	
	public static int size(){
		return values().length;
	}
}
