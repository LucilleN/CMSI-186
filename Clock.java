//LUCILLE NJOO, CMSI 186, DORIN
public class Clock{
	private int hours;
	private int minutes;
	private double seconds;
	private double timeSlice;
	public Clock() {
		this.hours = 0;
		this.minutes = 0;
		this.seconds = 0.0;
		this.timeSlice = 60.0;
	}
	public Clock(int hours, int minutes, double seconds, double timeSlice) {
		if (hours < 0 || minutes < 0 || seconds < 0.0 || timeSlice <= 0.0 || hours >= 24 || minutes >= 60 || seconds >= 60.0 || timeSlice >= 43200) {
			throw new IllegalArgumentException();
		}
		else {
			this.hours = hours;
			this.minutes = minutes;
			this.seconds = seconds;
			this.timeSlice = timeSlice;
		}
	}
	public void advanceTime() {
		this.seconds += this.timeSlice;
		while (this.seconds >= 60) {
			this.seconds -= 60;
			this.minutes++;
		}
		while (this.minutes >= 60) {
			this.minutes -= 60;
			this.hours++;
		}
		while (this.hours >= 24) {
			this.hours -= 24;
		}
	}
	public int hours() {
		return this.hours;
	}
	public int minutes() {
		return this.minutes;
	}
	public double seconds() {
		return this.seconds;
	}
	public String toString() {
		return ((this.hours <= 9) ? "0" : "") + String.valueOf(this.hours) + ":" + ((this.minutes <= 9) ? "0" : "") + String.valueOf(this.minutes) + ":" + ((this.seconds <= 9) ? "0" : "") + String.valueOf(this.seconds);
	}
	public static boolean testThrowsExceptions(int hours, int minutes, double seconds, double timeSlice) {
		try {
			Clock test = new Clock(hours, minutes, seconds, timeSlice);
		} 
		catch (IllegalArgumentException e) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		Clock c1 = new Clock(1, 30, 22, 3.45);
		System.out.println(c1.toString().equals("01:30:22.0"));
		c1.advanceTime();
		System.out.println(c1.hours() == 1);
		System.out.println(c1.minutes() == 30);
		System.out.println(c1.seconds() == 25.45);
		System.out.println(c1.toString().equals("01:30:25.45"));
		Clock c2 = new Clock();
		System.out.println(c2.toString().equals("00:00:00.0"));
		c2.advanceTime();
		System.out.println(c2.hours() == 0);
		System.out.println(c2.minutes() == 1);
		System.out.println(c2.seconds() == 0.0);
		System.out.println(c2.toString().equals("00:01:00.0"));
		Clock c3 = new Clock(0, 0, 59, 1.5);
		System.out.println(c3.toString().equals("00:00:59.0"));
		c3.advanceTime();
		System.out.println(c3.hours() == 0);
		System.out.println(c3.minutes() == 1);
		System.out.println(c3.seconds() == 0.5);
		System.out.println(c3.toString().equals("00:01:00.5"));
		Clock c4 = new Clock(0, 59, 59, 1.5);
		System.out.println(c4.toString().equals("00:59:59.0"));
		c4.advanceTime();
		System.out.println(c4.hours() == 1);
		System.out.println(c4.minutes() == 0);
		System.out.println(c4.seconds() == 0.5);
		System.out.println(c4.toString().equals("01:00:00.5"));
		Clock c5 = new Clock (23, 59, 59, 1.5);
		System.out.println(c5.toString().equals("23:59:59.0"));
		c5.advanceTime();
		System.out.println(c5.hours() == 0);
		System.out.println(c5.minutes() == 0);
		System.out.println(c5.seconds() == 0.5);
		System.out.println(c5.toString().equals("00:00:00.5"));
		System.out.println(testThrowsExceptions(-1, 1, 1.0, 20.0));
		System.out.println(testThrowsExceptions(25, 1, 1.0, 20.0));
		System.out.println(testThrowsExceptions(1, -1, 1.0, 20.0));
		System.out.println(testThrowsExceptions(1, 65, 1.0, 20.0));
		System.out.println(testThrowsExceptions(1, 1, -1.0, 20.0));
		System.out.println(testThrowsExceptions(1, 1, 60.0, 20.0));
		System.out.println(testThrowsExceptions(1, 1, 1.0, 0.0));
		System.out.println(testThrowsExceptions(1, 1, 1.0, -20.0));
		System.out.println(testThrowsExceptions(1, 1, 1.0, 43201.0));
	}
}