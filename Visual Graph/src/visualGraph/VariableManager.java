package visualGraph;

import java.util.HashMap;
import java.util.Set;

public class VariableManager {
	
	private HashMap<String,Double> variableMap;
	
	public VariableManager(){
		variableMap = new HashMap<String,Double>();
		variableMap.put("PI", Math.PI);
	}
	
	public void addVariable(String key, double value){
		variableMap.put(key, value);
	}
	
	public double getValue(String key){
		return variableMap.get(key);
	}
	
	public String replaceVariables(String string){
		Set<String> keySet = variableMap.keySet();
		String finalString = string;
		for(String s : keySet){
			double value = variableMap.get(s);
			finalString = finalString.replace(s, Double.toString(value));
		}
		return finalString;
	}
}
