/**This class models a tuple of specified integer length.*/
public class Tuple {	
	private int[] tuple;
	/**Constructs a tuple of length i, with all elements set to a default value of 0.*/
	public Tuple(int k) {
		this.tuple = new int[k];
		for (int i = 0; i < k; i++) {
			this.tuple[i] = 0;
		}
	}
	/**Constructs a tuple from an array of data.*/
	public Tuple(int[] data) {
		this.tuple = data;
	}
	/**Constructs a tuple from an array of data.*/
	public static Tuple makeTupleFromData(int[] data) {
		return new Tuple(data);
	}
	/**Returns a new tuple whose elements are the sums of the respective elements of this tuple and tuple t.*/
	public Tuple add(Tuple t) {
		if (this.length() != t.length()) {
			throw new IllegalArgumentException();
		}
		Tuple result = new Tuple(t.length());
		for (int i = 0; i < t.length(); i++) {
			result.setElement(i, (t.getElement(i) + this.tuple[i]));
		}
		return result;
	} 
	/**Returns a new tuple that is an exact copy of this tuple.*/
	public Tuple clone() {
		return new Tuple(this.tuple);
	}
	/**Returns true iff object obj is a tuple with the same length and elements as this tuple.*/
	public boolean equals(Object obj) {
		if (!(obj instanceof Tuple)) {
			return false;
		}
		if (((Tuple)obj).length() != this.length()) {
			return false;
		}
		for (int i = 0; i < ((Tuple)obj).length(); i++) {
			if (this.getElement(i) != ((Tuple)obj).getElement(i)) {
				return false;
			}
		}
		return true;
	}
	/**Returns the value of the ith element of this tuple, with zero-based indexing.*/
	public int getElement(int i) {
		if (i < 0 || i > this.length()) {
			throw new IllegalArgumentException();
		}
		return this.tuple[i];
	}
	/**Sets the value of the ith element of this tuple to the specified integer value, with zero-based indexing.*/
	public void setElement(int i, int value) {
		if (i < 0 || i > this.length()) {
			throw new IllegalArgumentException();
		}
		this.tuple[i] = value;
	}
	/**Returns the number of elements in this tuple.*/
	public int length() {
		return this.tuple.length;
	}
	/**Returns the sum of the elements in this tuple.*/
	public int sum() {
		int sum = 0;
		for (int i : this.tuple) {
			sum += i;
		}
		return sum;
	}
	/**Returns the string that denotes this tuple.*/
	public String toString() {
		String string = "[";
		if (this.length() > 0) {
			string += this.tuple[0];
			for (int i = 1; i < this.length(); i++) {
				string += ("," + this.tuple[i]);
			}
		}
		string += "]";
		return string;
	}
	/**Tests all the methods in this class.*/
	public static void main(String[] args) {
		testToString();
		testMakeTupleFromData();
		testAdd();
		testClone();
		testEquals();
		testGetElement();
		testSetElement();
		testLength();
		testSum();
	}
	public static void testMakeTupleFromData() {
		System.out.println("Testing makeTupleFromData:");
		int[] data1 = new int[] {2, 4, 6};
		Tuple t1 = new Tuple(data1);
		Tuple t2 = makeTupleFromData(data1);
		int[] data2 = new int[] {Integer.MIN_VALUE, 4, 6, 0, Integer.MAX_VALUE};
		Tuple t3 = new Tuple(data2);
		Tuple t4 = makeTupleFromData(data2);
		int[] data3 = new int[] {0, 72, 209, 23};
		Tuple t5 = new Tuple(data3);
		Tuple t6 = makeTupleFromData(data3);
		System.out.println(t1.equals(t2));
		System.out.println(t2.equals(t1));
		System.out.println(t3.equals(t4));
		System.out.println(t4.equals(t3));
		System.out.println(t5.equals(t6));
		System.out.println(t6.equals(t5));
	}
	public static void testAdd() {
		System.out.println("Testing add:");
		Tuple t1 = new Tuple(new int[] {1, 2, 3, 4});
		Tuple t2 = new Tuple(new int[] {-1, 60, 55, 3});
		Tuple t3 = new Tuple(3);
		Tuple t4 = new Tuple(new int[] {2, 4, 6});
		Tuple t5 = new Tuple(new int[] {Integer.MAX_VALUE});
		Tuple t6 = new Tuple(new int[] {Integer.MIN_VALUE});
		System.out.println(t1.add(t2).toString().equals("[0,62,58,7]"));
		System.out.println(t2.add(t1).toString().equals("[0,62,58,7]"));
		System.out.println(t3.add(t4).toString().equals("[2,4,6]"));
		System.out.println(t4.add(t3).toString().equals("[2,4,6]"));
		System.out.println(t5.add(t6).toString().equals("[-1]"));
		System.out.println(t6.add(t5).toString().equals("[-1]"));
		System.out.println(t1.add(t1).toString().equals("[2,4,6,8]"));
		System.out.println(t2.add(t2).toString().equals("[-2,120,110,6]"));
		System.out.println(t3.add(t3).toString().equals("[0,0,0]"));
		System.out.println(t4.add(t4).toString().equals("[4,8,12]"));
		try {
			Tuple fail1 = t1.add(t3);
		} 
		catch (IllegalArgumentException iae) {
			System.out.println(true);
		}
		try {
			Tuple fail2 = t2.add(t4);
		} 
		catch (IllegalArgumentException iae) {
			System.out.println(true);
		}		
	}
	public static void testClone() {
		System.out.println("Testing clone:");
		Tuple t1 = new Tuple(2);
		Tuple t2 = new Tuple(new int[] {-108, 9023, 0, 64, -25, -25});
		Tuple t3 = new Tuple(new int[] {1, 2, -6, 198493, Integer.MAX_VALUE});
		Tuple t4 = new Tuple(new int[] {23940, 0, 0, Integer.MIN_VALUE});
		System.out.println(t1.clone().toString().equals("[0,0]"));
		System.out.println(t2.clone().toString().equals("[-108,9023,0,64,-25,-25]"));
		System.out.println(t3.clone().toString().equals("[1,2,-6,198493,2147483647]"));
		System.out.println(t4.clone().toString().equals("[23940,0,0,-2147483648]"));
	}
	public static void testEquals() {
		System.out.println("Testing equals");		
		Tuple t1 = new Tuple(6);
		Tuple t2 = new Tuple(6);
		Tuple t3 = new Tuple(new int[] {-9845,24,0,1,5});
		Tuple t4 = new Tuple(new int[] {-9845,24,0,1,5});
		Tuple t5 = new Tuple(new int[] {0,Integer.MAX_VALUE,1});
		Tuple t6 = new Tuple(new int[] {0,2147483647,1});
		Tuple t7 = new Tuple (3);
        Tuple t8 = new Tuple (2);
		System.out.println(t1.equals(t2));
		System.out.println(t2.equals(t1));
		System.out.println(t3.equals(t4));
		System.out.println(t4.equals(t3));
		System.out.println(t5.equals(t6));
		System.out.println(t6.equals(t5));
		System.out.println(t1.clone().equals(t1));
		System.out.println(t3.clone().equals(t3));
		System.out.println(t5.clone().equals(t5));
        System.out.println (!t7.equals(t8));		
	}
	public static void testGetElement() {
		System.out.println("Testing getElement:");
		Tuple t1 = new Tuple(3);
		Tuple t2 = new Tuple(new int[] {3, 0, 1, 100});
		Tuple t3 = new Tuple(new int[] {Integer.MAX_VALUE});
		Tuple t4 = new Tuple(new int[] {-100, -409, -293086, 34, 690856});
		Tuple t5 = new Tuple(new int[] {0, Integer.MIN_VALUE});
		System.out.println(t1.getElement(0) == 0);
		System.out.println(t1.getElement(1) == 0);
		System.out.println(t1.getElement(2) == 0);
		System.out.println(t2.getElement(0) == 3);
		System.out.println(t2.getElement(1) == 0);
		System.out.println(t2.getElement(2) == 1);
		System.out.println(t2.getElement(3) == 100);
		System.out.println(t3.getElement(0) == 2147483647);
		System.out.println(t4.getElement(0) == -100);
		System.out.println(t4.getElement(1) == -409);
		System.out.println(t4.getElement(2) == -293086);
		System.out.println(t4.getElement(3) == 34);
		System.out.println(t4.getElement(4) == 690856);
		System.out.println(t5.getElement(0) == 0);
		System.out.println(t5.getElement(1) == -2147483648);
		try {
			t1.getElement(-1);
		}
		catch (IllegalArgumentException iae) {
			System.out.println(true);
		}
		try {
			t1.getElement(3);
		}
		catch (IllegalArgumentException iae) {
			System.out.println(true);
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println(true);
		}
	}
	public static void testSetElement() {
		System.out.println("Testing setElement:");
		Tuple t1 = new Tuple(3);
		Tuple t2 = new Tuple(6);
		Tuple t3 = new Tuple(new int[] {2, 4, 6, 8});
		Tuple t4 = new Tuple(new int[] {-1, -65});
		t1.setElement(0, 4);
		t1.setElement(2,30);
		t2.setElement(3, Integer.MAX_VALUE);
		t3.setElement(1, Integer.MIN_VALUE);
		t3.setElement(3, 0);
		t4.setElement(1, -1600);
		System.out.println(t1.getElement(0) == 4);
		System.out.println(t1.getElement(2) == 30);
		System.out.println(t2.getElement(3) == 2147483647);
		System.out.println(t3.getElement(1) == -2147483648);
		System.out.println(t3.getElement(3) == 0);
		System.out.println(t4.getElement(1) == -1600);
		System.out.println(t1.toString().equals("[4,0,30]"));
		System.out.println(t2.toString().equals("[0,0,0,2147483647,0,0]"));
		System.out.println(t3.toString().equals("[2,-2147483648,6,0]"));
		System.out.println(t4.toString().equals("[-1,-1600]"));		
		try {
			t1.setElement(3, 27);
		}
		catch (IllegalArgumentException iae) {
			System.out.println(true);
		}
		catch (ArrayIndexOutOfBoundsException aioobe) {
			System.out.println(true);
		}
		try {
			t2.setElement(-1, 27);
		}
		catch (IllegalArgumentException iae) {
			System.out.println(true);
		}
	}
	public static void testLength() {
		System.out.println("Testing length:");
		Tuple t1 = new Tuple(1);
		Tuple t2 = new Tuple(new int[] {2, -49, 45});
		Tuple t3 = new Tuple(new int[] {-12, -589, 0, 123, 14});
		Tuple t4 = new Tuple(15);
		Tuple t5 = new Tuple(1000000);
		System.out.println(t1.length() == 1);
		System.out.println(t2.length() == 3);
		System.out.println(t3.length() == 5);
		System.out.println(t4.length() == 15);
		System.out.println(t5.length() == 1000000);
	}
	public static void testSum() {
		System.out.println("Testing sum:");
		Tuple t1 = new Tuple(12);
		Tuple t2 = new Tuple(new int[] {1, 36, 209, -14});
		Tuple t3 = new Tuple(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE});
		Tuple t4 = new Tuple(new int[] {-2, -4, 0, -190, 35});
		System.out.println(t1.sum() == 0);
		System.out.println(t2.sum() == 232);
		System.out.println(t3.sum() == -1);
		System.out.println(t4.sum() == -161);
	}
	public static void testToString() {
		System.out.println("Testing toString:");
		Tuple t1 = new Tuple(3);
		Tuple t2 = new Tuple(new int[] {3, 0, 1, 100});
		Tuple t3 = new Tuple(new int[] {Integer.MAX_VALUE});
		Tuple t4 = new Tuple(new int[] {-100, -409, -293086, 34, 690856});
		Tuple t5 = new Tuple(new int[] {0, Integer.MIN_VALUE});
		System.out.println(t1.toString().equals("[0,0,0]"));
		System.out.println(t2.toString().equals("[3,0,1,100]"));
		System.out.println(t3.toString().equals("[2147483647]"));
		System.out.println(t4.toString().equals("[-100,-409,-293086,34,690856]"));
		System.out.println(t5.toString().equals("[0,-2147483648]"));
	}
}