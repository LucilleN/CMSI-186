/**Computes the definite integral of a polynomial with midpoint Riemann sums.*/
public class Riemann {
	public static void main(String[] args) {
		if (args.length < 4) {
			throw new IllegalArgumentException("Insufficient args");
		}
		double[] coefficients = new double[args.length - 3];
		double lowerBound;
		double upperBound;
		double pct;
		try {
			for (int i = 0; i < args.length - 3; i++) {
				coefficients[i] = Double.parseDouble(args[i]);
			}
			lowerBound = Double.parseDouble(args[args.length-3]);
			upperBound = Double.parseDouble(args[args.length-2]);
			pct = Double.parseDouble(args[args.length-1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("All args must be nunmbers.");
		}
		if (pct <= 0.0) {
			throw new IllegalArgumentException("Percent must be greater than 0.");
		}
		if (lowerBound >= upperBound) {
			throw new IllegalArgumentException("Lower bound must be less than upper bound");
		}
		double width = upperBound - lowerBound;
		double area1 = 0;
		double area2 = 0;
		double x;
		double height;
		double rectArea;
		do {
			x = lowerBound;
			area1 = area2;
			area2 = 0;
			while (x < upperBound) {
				height = evaluateFn(coefficients, x + width/2.0);
				rectArea = height*width;
				area2 += rectArea;
				x += width;
			}
			width = width/2;
		} while (Math.min(Math.abs(area1), Math.abs(area2))/Math.max(Math.abs(area1), Math.abs(area2)) < (1.0 - pct/100.0));
		System.out.println(area2);
	}
	public static double evaluateFn(double[] coefficients, double x) {
		double result = 0.0;
		for (int i = 0; i < coefficients.length; i++) {
			result = result + (coefficients[i] * Math.pow(x, i));
		}
		return result;
	}
}