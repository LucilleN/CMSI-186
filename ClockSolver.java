//LUCILLE NJOO, CMSI 186, DORIN
public class ClockSolver {
	public static void main(String[] args) {
		if (args.length > 1) {
			throw new IllegalArgumentException();
		}
		double timeSlice = 60.0;
		if (args.length == 1) {
			try {
				timeSlice = Double.parseDouble(args[0]);
			}
			catch (NumberFormatException nfe) {
				System.out.println("Error: Argument must be a real number.");
				System.exit(1);
			}
		}
		if ((timeSlice <= 0.0) || (timeSlice > 1800.0)) {
			throw new IllegalArgumentException();
		}
		Clock clock = new Clock(0, 0, 0.0, timeSlice);
		double hrHandAngleChange = 30.0 * timeSlice / 3600.0;
		double minHandAngleChange = 360.0 * timeSlice / 3600.0;
		double deltaAngle = minHandAngleChange - hrHandAngleChange;
		double angleBetween = 0.0;
		while (clock.hours() < 12) {
			if (inSweetSpot(angleBetween, deltaAngle)) {
				System.out.println(clock.toString());
			}
			angleBetween += deltaAngle;
			if (angleBetween > 360.0) {
				angleBetween = angleBetween - 360.0;
			}
			clock.advanceTime();
		}
	}
	public static boolean inSweetSpot(double angleBetween, double deltaAngle) {
		double sweetSpotRange = deltaAngle/2.0;
		if 	((angleBetween >= 180.0 - sweetSpotRange) && (angleBetween < 180.0 + sweetSpotRange) 
			|| (angleBetween >= 90.0 - sweetSpotRange) && (angleBetween < 90.0 + sweetSpotRange) 
			|| (angleBetween >= 270.0 - sweetSpotRange) && (angleBetween < 270.0 + sweetSpotRange)) {
				return true;
		}
		else {
				return false;
		}
	}
}