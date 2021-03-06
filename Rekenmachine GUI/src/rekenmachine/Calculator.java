package rekenmachine;

import java.awt.Color;

public class Calculator {

	private static final String LAST_AWNSER_KEY = "ANS";

	private Main main;

	private boolean hasLastAwnser = false;
	private double lastAwnser = 0.0D;

	private String input = "";
	private boolean calculated = false;

	public Calculator(Main m){
		main = m;
	}

	public void addNumber(int number){
		checkCalculated();
		input = input + number;
		updateLabel();
	}

	public void addOperator(String operator){
		calculated = false;
		String addition = (isLastCharSpace() ? "" : "") + operator + "";
		input = input + addition;
		updateLabel();
	}

	public void addDecimal(){
		checkCalculated();
		input = input + ".";
		updateLabel();
	}

	private void checkCalculated(){
		if(calculated)
			clear();
	}

	public void addAwnser(){
		checkCalculated();
		if(hasLastAwnser){
			input = input + LAST_AWNSER_KEY;
			updateLabel();
		}
	}

	public void clear(){
		if(input.isEmpty())
			resetLastAwnser();
		calculated = false;
		input = "";
		updateLabel();
	}

	private void updateLabel(){
		main.setLabelText(input);
	}

	public void equals(){

		if(input.isEmpty())
			return;

		String s = "";

		try {
			double awnser = eval(getFormattedInput());
			System.out.println(awnser);
			if(awnser < 0.00000000000001D && awnser > -0.00000000000001D)
				awnser = 0.0D;
			setLastAwnser(awnser);
			s = Double.toString(awnser);
			input = s;
		} catch (RuntimeException e) {
			s = "ERROR";
			e.printStackTrace();
		}

		calculated = true;
		main.setLabelText(s);

	}

	private String getFormattedInput(){
		String s = input;
		s = s.replaceAll("SQR", "r");
		s = s.replaceAll(LAST_AWNSER_KEY, Double.toString(lastAwnser));
		s = s.replaceAll("PI", Double.toString(Math.PI));
		s = s.replaceAll("sin", "s");
		s = s.replaceAll("cos", "c");
		s = s.replaceAll("tan", "t");
		return s;
	}

	private void resetLastAwnser(){
		lastAwnser = 0.0d;
		hasLastAwnser = false;
		main.setAnsButtonTextColor(Color.red);
	}

	private void setLastAwnser(double d){
		lastAwnser = d;
		hasLastAwnser = true;
		main.setAnsButtonTextColor(Color.black);
	}

	public void removeCharacter(){
		
		if(input.isEmpty())
			return;

		boolean removed = false;
		
		if(input.length() >= 3){
			String s = input.substring(input.length() - 3, input.length());
			if(s.equals("sin") || s.equals("cos") || s.equals("tan") || s.equals("SQR") || s.equals(LAST_AWNSER_KEY)){
				input = input.substring(0, input.length() - 3);
				removed = true;
			}
		}
		
		if(!removed && input.length() >= 2){
			String s = input.substring(input.length() - 2, input.length());
			if(s.equals("PI")){
				input = input.substring(0, input.length() - 2);
				removed = true;
			}
		}
		
		if(!removed){
			input = input.substring(0, input.length() - 1);
		}
		
		updateLabel();
		
		/*boolean removedCharacter = false;
		boolean first = true;
		int curChar = input.length() - 1;
		while(true){
			char c = input.charAt(curChar);
			if(c != ' '){
				if(!removedCharacter){
					removedCharacter = true;
				}
				else{
					break;
				}
			}
			curChar--;
			if(curChar < 0)
				break;
			if(first && removedCharacter) break;
			first = false;
		}
		input = input.substring(0, curChar + 1);
		updateLabel();*/
		
		

	}

	public void onShiftPressed(){
		main.changeButtons(true);
	}

	public void onShiftReleased(){
		main.changeButtons(false);
	}

	private boolean isLastCharSpace(){
		return input.isEmpty() ? false : getLastChar() == ' ';
	}
	
	private char getLastChar(){
		return input.charAt(input.length() - 1);
	}
	
	public double eval(final String str) throws RuntimeException{
		return new Object() {
			int pos = -1, ch;

			void nextChar() {
				ch = (++pos < str.length()) ? str.charAt(pos) : -1;
			}

			boolean eat(int charToEat) {
				while (ch == ' ') nextChar();
				if (ch == charToEat) {
					nextChar();
					return true;
				}
				return false;
			}

			double parse() {
				nextChar();
				double x = parseExpression();
				if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
				return x;
			}

			double parseExpression() {
				double x = parseTerm();
				while(true) {
					if      (eat('+')) x += parseTerm();
					else if (eat('-')) x -= parseTerm();
					else return x;
				}
			}

			double parseTerm() {
				double x = parseFactor();
				while(true) {
					if      (eat('*')) x *= parseFactor();
					else if (eat('/')) x /= parseFactor();
					else return x;
				}
			}

			double parseFactor() {
				if (eat('s')){
					double s = Math.sin(parseFactor());
					//System.out.println("Parsed sin");
					return s;
				}
				if (eat('c')){
					double s = Math.cos(parseFactor());
					//System.out.println("Parsed cos");
					return s;
				}
				if (eat('t')){
					//System.out.println("Parsed tan");
					return Math.tan(parseFactor());
				}
				if (eat('r')){
					//System.out.println("Parsed sqr");
					return Math.sqrt(parseFactor());
				}
				if (eat('+')){
					//System.out.println("Parsed +");
					return parseFactor();
				}
				if (eat('-')){
					//System.out.println("Parsed -");
					return -parseFactor();
				}

				double x = 0;
				int startPos = this.pos;
				if (eat('(')) {
					x = parseExpression();
					eat(')');
				} else if ((ch >= '0' && ch <= '9') || ch == '.') {
					while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
					x = Double.parseDouble(str.substring(startPos, this.pos));
				} else {
					throw new RuntimeException("Unexpected: " + (char)ch);
				}

				if (eat('^')) x = Math.pow(x, parseFactor());
				return x;
			}
		}.parse();
	}
}
