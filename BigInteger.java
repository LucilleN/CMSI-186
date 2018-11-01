/**This class represents big integers with as many as Integer.MAX_VALUE digits.*/
public class BigInteger {
	private int[] digits;
	private int sign;
	/**BigInteger that represents the integer 0.*/
	public static final BigInteger ZERO = new BigInteger("0");
	/**BigInteger that represents the integer 1.*/
	public static final BigInteger ONE = new BigInteger("1");
	/**Constructs a BigInteger from a string of digits. A leading + or - to designate sign is optional.*/
	public BigInteger(String val) {
		if (val.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.sign = 1;
		if (val.charAt(0) == '+') {
			val = val.substring(1);
		}
		if (val.charAt(0) == '-') {
			this.sign = -1;
			val = val.substring(1);
		}
		while (val.charAt(0) == '0' && val.length() > 1) {
			val = val.substring(1);
		}
		if (val.length() == 1 && val.charAt(0) == '0') {
			this.digits = new int[] {0};
			this.sign = 1;
		}
		this.digits = new int[val.length()];
		String[] characters = val.split("");
		try {
			for (int i = 0; i < this.digits.length; i++) {
				this.digits[this.digits.length - 1 - i] = Integer.parseInt(characters[i]);
			}
		}
		catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}	
	}
	/**This constructor creates a BigInteger from an existing array of digits, where each element of the array corresponds to the power of 10. By default, the sign is set to +1.*/
	public BigInteger(int[] digits) {
		for (int i = 0; i < digits.length; i++) {
			if (digits[i] < 0 || digits[i] > 9) {
				throw new IllegalArgumentException();
			}
		}
		this.digits = digits;
		this.sign = 1;
	}
	/**Returns a new BigInteger that is the same value and sign as this one.*/
	public BigInteger clone() {
		BigInteger copy = new BigInteger(this.digits);
		copy.sign = this.sign;
		return copy;
	}
	/**Returns a string that represents this BigInteger.*/
	public String toString() {
		String str = this.sign == 1 ? "+" : "-";
		for (int i = 0; i < this.digits.length; i++) {
			str = str + String.valueOf(digits[this.digits.length - 1 - i]);
		}
		return str;
	}
	/**Returns true if and only if Object x is a BigInteger with the same value and sign as this BigInteger.*/
	public boolean equals(Object x){
		if (!(x instanceof BigInteger)) {
			return false;
		}
		if ((this.sign != ((BigInteger)x).sign) || this.digits.length != ((BigInteger)x).digits.length) {			
			return false;
		}
		for (int i = this.digits.length - 1; i >= 0; i--) {
			if (this.digits[i] != ((BigInteger)x).digits[i]) {
				// System.out.println(i);
				return false;
			}
		}
		return true;
	}
	/**Returns a new BigInteger that is the sum of this BigInteger and BigInteger val.*/
	public BigInteger add(BigInteger val) {
		int[] sumArray = new int[(Math.max(this.digits.length, val.digits.length))+1];
		for (int i = 0; i < sumArray.length; i++) {
			sumArray[i] = 0;
		}
		boolean switchSign = false;
		int thisSign = this.sign;
		int valSign = val.sign;
		if (this.sign == -1 && val.sign == -1) {
			thisSign = -this.sign;
			valSign = -val.sign;
			switchSign = true;
		}
		else if ((this.sign == -1 && val.sign == 1 && this.greaterMagnitudeThan(val)) || 
				 (this.sign == 1 && val.sign == -1 && val.greaterMagnitudeThan(this))) {
			thisSign = -this.sign;
			valSign = -val.sign;
			switchSign = true;
		}
		for (int i = 0; i < Math.min(this.digits.length, val.digits.length); i++) {
			sumArray[i] = (thisSign * this.digits[i]) + (valSign * val.digits[i]);
		}
		for (int i = Math.min(this.digits.length, val.digits.length); i < Math.max(this.digits.length, val.digits.length); i++) {
			sumArray[i] = this.digits.length >= val.digits.length ? (thisSign * this.digits[i]) : (valSign * val.digits[i]);
		}
		for (int i = 0; i < sumArray.length; i++) {
			// System.out.println("digit at index " + i + " is " + sumArray[i]);
			if (sumArray[i] >= 10 && i < sumArray.length-1) {
				sumArray[i] = sumArray[i] - 10;
				sumArray[i+1]++;
			}
			else if (sumArray[i] < 0 && i < sumArray.length-1) {
				sumArray[i] = sumArray[i] + 10;
				sumArray[i+1]--;
				// System.out.println("i: " + i + ", index i: " + sumArray[i] + ", index i+1: " + sumArray[i+1]);
			}
			// System.out.println("now, digit at index " + i + " is " + sumArray[i]);
		}
		if (sumArray[sumArray.length-1] < 0) {
			int[] sumArrayCopy = sumArray.clone();
			sumArray = new int[sumArrayCopy.length+1];
			for (int i = 0; i < sumArrayCopy.length; i++) {
				sumArray[i] = sumArrayCopy[i];
			}
			sumArray[sumArray.length-1] = 0;
		}
		if (sumArray[sumArray.length-1] >= 10) {
			int[] sumArrayCopy = sumArray.clone();
			sumArray = new int[sumArrayCopy.length+1];
			for (int i = 0; i < sumArrayCopy.length; i++) {
				sumArray[i] = sumArrayCopy[i];
			}
			sumArray[sumArray.length-1] = 0;
		}
		if (sumArray[sumArray.length - 2] >= 10) {
			sumArray[sumArray.length - 2] -= 10;
			sumArray[sumArray.length - 1]++;
		}
		if (sumArray[sumArray.length - 2] < 0) {
			sumArray[sumArray.length - 2] += 10;
			sumArray[sumArray.length - 1]--;
		}
		String sumString = (switchSign) ? "-" : "+";
		for (int i = 0; i < sumArray.length; i++) {
			sumString = sumString + Integer.toString(sumArray[sumArray.length - 1 - i]);
		}
		// System.out.println(sumString);
		BigInteger sum = new BigInteger(sumString);
		return sum;
	}
	/**Returns a new BigInteger that is the result of this BigInteger minus BigInteger val.*/
	public BigInteger subtract(BigInteger val) {
		BigInteger valCopy = new BigInteger(val.digits.clone());
		valCopy.sign = -val.sign;
		return this.add(valCopy);
	}
	/**Returns a new BigInteger that is the product of this BigInteger and BigInteger val.*/
	public BigInteger multiply(BigInteger val) {
		int[] productArray = new int[(this.digits.length - 1) + (val.digits.length - 1) + 1]; //add the highest powers of ten of both and add one
		for (int i = 0; i < productArray.length; i++) { //set all digits to 0 to begin
			productArray[i] = 0;
		} 
		int productTemp;
		for (int i = 0; i < val.digits.length; i++) {
			for (int j = 0; j < this.digits.length; j++) {
				productTemp = this.digits[j] * val.digits[i];
				productArray[i+j] += productTemp;
			}
		}
		for (int i = 0; i < productArray.length-1; i++) {
			while (productArray[i] >= 10) {
				productArray[i] -= 10;
				productArray[i+1]++;
			}
		}
		String productStr = (this.sign == val.sign) ? "+" : "-";
		for (int i = productArray.length - 1; i >= 0; i--) {
			productStr += String.valueOf(productArray[i]);
		}
		return new BigInteger(productStr);
	}
	/**Returns a new BigInteger that is equal to this BigInteger raised to the power of int exponent.*/
	public BigInteger power(int exponent) {
		if (exponent < 0) {
			throw new IllegalArgumentException();
		}
		if (exponent == 0) {
			return new BigInteger("1");
		}
		if (this.equals(ZERO)) {
			return this.clone();
		}
		BigInteger result = this.clone();
		for (int i = 1; i < exponent; i++) {
			result = result.multiply(this);
		}
		return result;
	}
	/**Returns a new BigInteger that is the quotient of this BigInteger divided by BigInteger val.*/
	public BigInteger divide(BigInteger val) {
		//This is a recursive method that uses the divide-and-conquer algorithm. 
		if (!(this.greaterMagnitudeThan(val))) {
			return new BigInteger("0");
		}
		if (this.equals(val)) {
			return new BigInteger("1");
		}
		BigInteger quotient = new BigInteger("0");
		BigInteger bigger = this.clone();
		bigger.sign = 1;
		BigInteger ten = new BigInteger("10");
		BigInteger smaller = val.clone();
		smaller.sign = 1;
		while (bigger.greaterMagnitudeThan(val)) {
			smaller = val.clone();
			smaller.sign = 1;
			int i = 0;
			while (!(smaller.greaterMagnitudeThan(bigger))) {
				smaller = smaller.multiply(ten);
				i++;
			}
			BigInteger result = ten.power(i-1);
			quotient = quotient.add(result);
			bigger = bigger.subtract(val.multiply(result).abs());
		}
		quotient.sign = (this.sign == val.sign) ? 1 : -1;
		return quotient;
	}
	/**Returns a new BigInteger that is the remainder of this BigInteger divided by BigInteger val.*/
	public BigInteger remainder(BigInteger val) {
		BigInteger quotient = this.divide(val);
		// System.out.println(quotient);
		BigInteger remainder = (this.abs()).subtract(quotient.multiply(val).abs());
		// System.out.println(this.abs());
		// System.out.println(quotient.multiply(val).abs());
		// System.out.println(remainder);
		remainder.sign = this.sign;
		return remainder;
	}
	/**Returns a new BigInteger that is the absolute value of this BigInteger.*/
	public BigInteger abs() {
		BigInteger result = this.clone();
		result.sign = 1;
		return result;
	}
	/**Returns true if this BigInteger is greater than BigInteger val.*/
	public boolean greaterThan(BigInteger val) {
		if (this.sign == 1 && val.sign == 1) {
			if (this.greaterMagnitudeThan(val)) {
				return true;	
			}
		}
		else if (this.sign == 1 && val.sign == -1) {
			return true;
		}
		else if (this.sign == -1 && val.sign == 1) {
			return false;
		}
		else if (this.sign == -1 && val.sign == -1) {
			if (!(this.greaterMagnitudeThan(val))) {			
				return true;	
			}
			else {
				return true;
			}
		}
		return false;
	}
	/**Returns true if this BigInteger has a greater magnitude than BigInteger val.*/
	public boolean greaterMagnitudeThan(BigInteger val) {
		if (this.digits.length > val.digits.length) {
				return true;
		}
		else if (this.digits.length == val.digits.length) {
			for (int i = this.digits.length - 1; i >= 0; i--) {
				if (this.digits[i] > val.digits[i]) {
					return true;
				}
			}
		}
		return false;
	}
	/**Tests constructors and methods.*/
	public static void main(String[] args) {
		testConstructor();
		testEquals();
		testAdd();
		testSubtract();
		testMultiply();
		testDivide();
		testAbs();
		testPower();
		testGreaterThan();
		testGreaterMagnitudeThan();
		testRemainder();
	}
	/**Tests the constructor.*/
	public static void testConstructor() {
		System.out.println("testConstructor:");
		String[] badInputStrings = new String[] {"", "0.908", "0+090", "-+109832", "908732xyz91"};
		for (int i = 0; i < badInputStrings.length; i++) {
			try {
				BigInteger n1 = new BigInteger(badInputStrings[i]);
			}
			catch (Exception e) {
				System.out.println(true);
			}
		}
		try { //These should work fine. If they throw an exception, will print out false. 
			BigInteger n2 = new BigInteger("001190825");
			BigInteger n3 = new BigInteger("+910840000");
			BigInteger n4 = new BigInteger("-000");
			BigInteger n5 = new BigInteger("1");
		}
		catch (IllegalArgumentException e) {
			System.out.println(false);
		}
	}
	/**Tests the equals method.*/
	public static void testEquals() {
		System.out.println("testEquals:");
		BigInteger n1 = new BigInteger("005230984");
		BigInteger n2 = new BigInteger("+5230984");
		BigInteger n3 = new BigInteger("-00005230984");	
		BigInteger n4 = new BigInteger("-0003289");
		BigInteger n5 = new BigInteger("-000003289");
		BigInteger n6 = new BigInteger("+00");
		BigInteger n7 = new BigInteger("-000000");		
		System.out.println(n1.equals(n2));
		System.out.println(!(n1.equals(n3)));
		System.out.println(n4.equals(n5));
		System.out.println(n6.equals(n7));
	}
	/**Tests the add method.*/
	public static void testAdd() {
		System.out.println("testAdd:");
		BigInteger n1 = new BigInteger("923490");
		BigInteger n2 = new BigInteger("2309");
		BigInteger n3 = new BigInteger("925799");

		BigInteger n4 = new BigInteger("-19300");
		BigInteger n5 = new BigInteger("2309");
		BigInteger n6 = new BigInteger("-16991");

		BigInteger n7 = new BigInteger("-193");
		BigInteger n8 = new BigInteger("2309");
		BigInteger n9 = new BigInteger("2116");

		BigInteger n10 = new BigInteger("18");
		BigInteger n11 = new BigInteger("-139855");
		BigInteger n12 = new BigInteger("-139837");

		BigInteger n13 = new BigInteger("-923490");
		BigInteger n14 = new BigInteger("-2309");
		BigInteger n15 = new BigInteger("-925799");

		System.out.println(n1.add(n2).equals(n3));
		System.out.println((n4.add(n5)).equals(n6));
		System.out.println(n7.add(n8).equals(n9));
		System.out.println(n10.add(n11).equals(n12));
		System.out.println(n13.add(n14).equals(n15));

		System.out.println(n2.add(n1).equals(n3));
		System.out.println(n5.add(n4).equals(n6));
		System.out.println(n8.add(n7).equals(n9));
		System.out.println(n11.add(n10).equals(n12));
		System.out.println(n14.add(n13).equals(n15));
	}
	/**Tests the subtract method.*/
	public static void testSubtract() {
		System.out.println("testSubtract:");
		BigInteger n1 = new BigInteger("923490");
		BigInteger n2 = new BigInteger("2309");
		BigInteger n3 = new BigInteger("921181");

		BigInteger n4 = new BigInteger("-19300");
		BigInteger n5 = new BigInteger("2309");
		BigInteger n6 = new BigInteger("-21609");

		BigInteger n7 = new BigInteger("193");
		BigInteger n8 = new BigInteger("2309");
		BigInteger n9 = new BigInteger("-2116");

		BigInteger n10 = new BigInteger("18");
		BigInteger n11 = new BigInteger("-139855");
		BigInteger n12 = new BigInteger("139873");

		BigInteger n13 = new BigInteger("-923490");
		BigInteger n14 = new BigInteger("-2309");
		BigInteger n15 = new BigInteger("-921181");

		System.out.println(n1.subtract(n2).equals(n3));
		System.out.println(n4.subtract(n5).equals(n6));
		System.out.println(n7.subtract(n8).equals(n9));
		System.out.println(n10.subtract(n11).equals(n12));
		System.out.println(n13.subtract(n14).equals(n15));
	}
	/**Tests the multiply method.*/
	public static void testMultiply() {
		System.out.println("testMultiply:");

		BigInteger n1 = new BigInteger("312");
		BigInteger n2 = new BigInteger("68");
		BigInteger n3 = new BigInteger("21216");

		BigInteger n4 = new BigInteger("-908724");
		BigInteger n5 = new BigInteger("2108");
		BigInteger n6 = new BigInteger("-1915590192");

		BigInteger n7 = new BigInteger("-770009");
		BigInteger n8 = new BigInteger("-5981");
		BigInteger n9 = new BigInteger("4605423829");

		System.out.println(n1.multiply(n2).equals(n3));
		System.out.println(n2.multiply(n1).equals(n3));
		System.out.println(n4.multiply(n5).equals(n6));
		System.out.println(n5.multiply(n4).equals(n6));
		System.out.println(n7.multiply(n8).equals(n9));
		System.out.println(n8.multiply(n7).equals(n9));
	}
	/**Tests the divide method.*/
	public static void testDivide() {
		System.out.println("testDivide:");

		BigInteger n1 = new BigInteger("312");
		BigInteger n2 = new BigInteger("68");
		BigInteger n3 = new BigInteger("4");

		BigInteger n4 = new BigInteger("198428");
		BigInteger n5 = new BigInteger("1039582");
		BigInteger n6 = new BigInteger("0");

		BigInteger n7 = new BigInteger("-770009");
		BigInteger n8 = new BigInteger("-5981");
		BigInteger n9 = new BigInteger("128");

		BigInteger n10 = new BigInteger("-770009");
		BigInteger n11 = new BigInteger("5981");
		BigInteger n12 = new BigInteger("-128");

		BigInteger n13 = new BigInteger("770009");
		BigInteger n14 = new BigInteger("-5981");
		BigInteger n15 = new BigInteger("-128");

		System.out.println(n1.divide(n2).equals(n3));
		System.out.println(n4.divide(n5).equals(n6));
		System.out.println(n7.divide(n8).equals(n9));
		System.out.println(n10.divide(n11).equals(n12));
		System.out.println(n13.divide(n14).equals(n15));	
	}
	/**Tests the remainder method.*/
	public static void testRemainder() {
		System.out.println("testRemainder:");

		BigInteger n1 = new BigInteger("312");
		BigInteger n2 = new BigInteger("68");
		BigInteger n3 = new BigInteger("40");

		BigInteger n4 = new BigInteger("198428");
		BigInteger n5 = new BigInteger("1039582");
		BigInteger n6 = new BigInteger("198428");

		//Answer should take the sign of the first number
		BigInteger n7 = new BigInteger("-770009");
		BigInteger n8 = new BigInteger("-5981");
		BigInteger n9 = new BigInteger("-4441");

		BigInteger n10 = new BigInteger("-770009");
		BigInteger n11 = new BigInteger("5981");
		BigInteger n12 = new BigInteger("-4441");

		BigInteger n13 = new BigInteger("770009");
		BigInteger n14 = new BigInteger("-5981");
		BigInteger n15 = new BigInteger("4441");

		System.out.println(n1.remainder(n2).equals(n3));		
		System.out.println(n4.remainder(n5).equals(n6));
		System.out.println(n7.remainder(n8).equals(n9));
		System.out.println(n10.remainder(n11).equals(n12));
		System.out.println(n13.remainder(n14).equals(n15));
	}
	/**Tests the abs method.*/
	public static void testAbs() {
		System.out.println("testAbs:");

		BigInteger n1 = new BigInteger("312");
		BigInteger n2 = new BigInteger("-68000");
		BigInteger n3 = new BigInteger("68000");
		BigInteger n4 = new BigInteger("0");

		System.out.println(n1.abs().equals(n1));
		System.out.println(n2.abs().equals(n3));
		System.out.println(n4.abs().equals(n4));
	}
	/**Tests the power method.*/
	public static void testPower() {
		System.out.println("testPower:");

		BigInteger n1 = new BigInteger("10");
		BigInteger n2 = new BigInteger("10000000");
		System.out.println(n1.power(7).equals(n2));

		BigInteger n3 = new BigInteger("3242");
		BigInteger n4 = new BigInteger("34075248488");
		System.out.println(n3.power(3).equals(n4));
		System.out.println(n3.power(0).equals(ONE));
		try {
			n3.power(-1);
		} catch (IllegalArgumentException e) {
			System.out.println(true);
		}

		BigInteger n5 = new BigInteger("0");
		System.out.println(n5.power(6).equals(n5));	
	}
	/**Tests the greaterThan method.*/
	public static void testGreaterThan() {
		System.out.println("testGreaterThan:");

		BigInteger n1 = new BigInteger("198428");
		BigInteger n2 = n1.clone();
		BigInteger n3 = new BigInteger("198429");
		BigInteger n4 = new BigInteger("1389");
		BigInteger n5 = new BigInteger("-1389");
		BigInteger n6 = new BigInteger("-1390");

		System.out.println(!(n1.greaterThan(n2)));
		System.out.println(n3.greaterThan(n1));
		System.out.println(n3.greaterThan(n4));
		System.out.println(n4.greaterThan(n5));
		System.out.println(n5.greaterThan(n6));
	}
	/**Tests the greaterMagnitudeThan method.*/
	public static void testGreaterMagnitudeThan() {
		System.out.println("testGreaterMagnitudeThan:");
		
		BigInteger n1 = new BigInteger("198428");
		BigInteger n2 = n1.clone();
		BigInteger n3 = new BigInteger("198429");
		BigInteger n4 = new BigInteger("1389");
		BigInteger n5 = new BigInteger("-1389");
		BigInteger n6 = new BigInteger("-1390");

		System.out.println(!(n1.greaterMagnitudeThan(n2)));
		System.out.println(n3.greaterMagnitudeThan(n1));
		System.out.println(n3.greaterMagnitudeThan(n4));
		System.out.println(!(n4.greaterMagnitudeThan(n5)));
		System.out.println(n6.greaterMagnitudeThan(n5));
	}
}