/**Computes the definite integral of a polynomial with randomization.*/
public class Random {
	public static void main(String[] args) {
		if (args.length < 3) {
			throw new IllegalArgumentException("Insufficient args");
		}
		double[] coefficients = new double[args.length - 2];
		double lowerBound;
		double upperBound;
		try {
			for (int i = 0; i < args.length - 2; i++) {
				coefficients[i] = Double.parseDouble(args[i]);
			}
			lowerBound = Double.parseDouble(args[args.length-2]);
			upperBound = Double.parseDouble(args[args.length-1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("All args must be nunmbers.");
		}
		if (lowerBound >= upperBound) {
			throw new IllegalArgumentException("Lower bound must be less than upper bound");
		}
		//Get approximate absolute min and max of polynomial
		double min = evaluateFn(coefficients, lowerBound);
		double max = evaluateFn(coefficients, lowerBound);
		double width = (upperBound - lowerBound)/1000000.0;
		double x = lowerBound;
		double y = min;
		while (x <= upperBound) {
			y = evaluateFn(coefficients, x);
			if (y < min) {
				min = y;
			}
			if (y > max) {
				max = y;
			}
			x += width;
		}
		double integral = 0;
		if (max >= 0 && min >= 0) {
			integral = area(coefficients, lowerBound, upperBound, max);
		}
		if (max >= 0 && min < 0) {
			integral = area(coefficients, lowerBound, upperBound, max) + area(coefficients, lowerBound, upperBound, min);
		}
		if (max <= 0 && min < 0) {
			integral = area(coefficients, lowerBound, upperBound, min);
		}
		System.out.println(integral);
	}
	public static double area(double[] coefficients, double lowerBound, double upperBound, double rectHeight) {
		double dartsThrown = 10000000;
		double dartsIn = 0;
		double x; 
		double y;
		for (int i = 1; i <= dartsThrown; i++) {
			x = (Math.random() * (upperBound - lowerBound)) + lowerBound;
			y = Math.random() * rectHeight;


			if (y > 0 && evaluateFn(coefficients, x) > 0 && y <= evaluateFn(coefficients, x)) { 
				dartsIn++;
			}
			if (y < 0 && evaluateFn(coefficients, x) < 0 && y >= evaluateFn(coefficients, x)) { 
				dartsIn++;
			}
		}
		return (dartsIn/dartsThrown) * rectHeight * (upperBound - lowerBound);
	}
	public static double evaluateFn(double[] coefficients, double x) {
		double result = 0.0;
		for (int i = 0; i < coefficients.length; i++) {
			result = result + (coefficients[i] * Math.pow(x, i));
		}
		return result;
	}
}