package visualGraph;

public class Utils {

	public static final String toStringAndCutDouble(double d, int numbers){
		d = d * Math.pow(10, numbers);
		d = Math.round(d);
		d = d / Math.pow(10, numbers);
		return Double.toString(d);
	}
}
