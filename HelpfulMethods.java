public class HelpfulMethods {
	public static long gcd(long x, long y) {
		if (x % y == 0) {
			return y;
		} else {
			return gcd(y, x % y); 
		}
	}
	static long digitSum(long x) {
		String numString = Long.toString(x);
		long sum = 0;
		for (int i = 0; i < numString.length(); i++) {
			sum = sum + Character.getNumericValue(numString.charAt(i));
		}
		return sum;
	}
	static long lcm(long x, long y) {
		long gcd = gcd(x,y);
		return x * y / gcd;
		//recall from Math 248 that LCM(a,b) * GCD(a,b) = a * b
	}
}