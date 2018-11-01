/**This class estimates the value of pi.*/
public class PiSolver {
	/**Estimates the value of pi using randomization.*/
	public static void main(String[] args) {
		int darts = 1000000;
		if (args.length > 1) {
			throw new IllegalArgumentException();
		}
		if (args.length == 1) {
			try {
				darts = Integer.parseInt(args[0]);	
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException();
			}
			if (Integer.parseInt(args[0]) < 1) {
				throw new IllegalArgumentException();
			}
		}
		double x;
		double y;
		int dartsInCircle = 0;
		for (int i = 0; i < darts; i++) {
			x = Math.random() * 2;
			y = Math.random() * 2; 
			if (Math.sqrt(Math.pow((x - 1.0), 2.0) + Math.pow((y - 1.0), 2)) <= 1) {
				dartsInCircle++;
			}
		}
		double pi = (double) dartsInCircle / (double) darts * 4.0;
		System.out.println(dartsInCircle + " darts fell on or in the circle");
		System.out.println(darts + " darts were thrown");
		System.out.println("pi is approximately " + pi);
	}
}