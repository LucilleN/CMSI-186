public class Fibonacci {
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException();
		}
		int n;
		try {
			n = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
		BigInteger x1 = new BigInteger("0");
		BigInteger x2 = new BigInteger("1");
		BigInteger x3 = new BigInteger("0");
		if (n == 0) {
			System.out.println(x1);
		}
		if (n == 1) {
			System.out.println(x2);
		}
		if (n >= 2) {
			while (n >= 2) {
				x3 = x1.add(x2);
				//update the i-1 term and i-2 term. 
				x1 = x2.clone(); 
				x2 = x3.clone();
				n--;
			}	
		}
		System.out.println(x3);
	}
}