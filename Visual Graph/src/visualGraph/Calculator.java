package visualGraph;

public class Calculator {

	private static final double evaluateFormattedExpression(final String str){
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
	
	private static final String formatExpression(VariableManager vm, final String str){
		String s = str;

		s = s.replaceAll("SQR", "r");
		s = s.replaceAll("sin", "s");
		s = s.replaceAll("cos", "c");
		s = s.replaceAll("tan", "t");
		
		s = vm.replaceVariables(s);
		return s;
	}
	
	public static final double evaluateExpression(VariableManager vm, final String str) throws RuntimeException{
		String s = formatExpression(vm, str);
		return evaluateFormattedExpression(s);
	}
}
